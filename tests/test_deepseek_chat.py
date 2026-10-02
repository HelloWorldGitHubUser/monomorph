import os
import unittest
from unittest import mock

from langchain_core.messages import AIMessage, HumanMessage, ToolMessage
from langchain_core.outputs import ChatGeneration, ChatResult
from langchain_core.tools import tool
from pydantic import BaseModel

from monomorph.llm.custom_chat import DeepSeekChat
from monomorph.llm.factory import get_chat_class, init_model


@tool
def get_class_code(class_name: str) -> str:
    """Return the source code of a class."""
    return ""


class Decision(BaseModel):
    decision: str


def response_with_tool_call(reasoning: str) -> dict:
    return {
        "id": "r1",
        "model": "deepseek-v4-pro",
        "choices": [{
            "index": 0,
            "finish_reason": "tool_calls",
            "message": {
                "role": "assistant",
                "content": "",
                "reasoning_content": reasoning,
                "tool_calls": [{"id": "call_1", "type": "function",
                                "function": {"name": "get_class_code", "arguments": "{\"class_name\": \"A\"}"}}],
            },
        }],
        "usage": {"prompt_tokens": 10, "completion_tokens": 5, "total_tokens": 15},
    }


@mock.patch.dict(os.environ, {"DEEPSEEK_API_KEY": "test-key"})
class TestDeepSeekChat(unittest.TestCase):

    def test_factory_routes_prefix_and_effort(self):
        chat_class, name, kwargs = get_chat_class("mm_deepseek/deepseek-v4-pro::high")
        self.assertIs(chat_class, DeepSeekChat)
        self.assertEqual(name, "deepseek-v4-pro")
        self.assertEqual(kwargs, {"reasoning_effort": "high"})

    def test_payload_enables_thinking_without_temperature(self):
        model = DeepSeekChat("deepseek-v4-pro", temperature=0.0, reasoning_effort="high")
        payload = model._get_request_payload([HumanMessage(content="hi")])
        self.assertEqual(payload["model"], "deepseek-v4-pro")
        self.assertNotIn("temperature", payload)
        self.assertEqual(model.extra_body, {"thinking": {"type": "enabled"}, "reasoning_effort": "high"})
        self.assertEqual(str(model.openai_api_base), "https://api.deepseek.com")

    def test_reasoning_content_is_kept_and_sent_back(self):
        model = DeepSeekChat("deepseek-v4-pro")
        result = model._create_chat_result(response_with_tool_call("I should read class A."))
        ai_message = result.generations[0].message
        self.assertEqual(ai_message.additional_kwargs["reasoning_content"], "I should read class A.")
        self.assertEqual(len(ai_message.tool_calls), 1)
        history = [HumanMessage(content="decide"), ai_message,
                   ToolMessage(content="class A {}", tool_call_id="call_1"),
                   AIMessage(content="no stored reasoning")]
        messages = model._get_request_payload(history)["messages"]
        self.assertEqual(messages[1]["reasoning_content"], "I should read class A.")
        self.assertEqual(messages[1]["tool_calls"][0]["id"], "call_1")
        self.assertEqual(messages[3]["reasoning_content"], "")
        self.assertNotIn("reasoning_content", messages[0])
        self.assertNotIn("reasoning_content", messages[2])

    def test_structured_output_defaults_to_function_calling(self):
        model = DeepSeekChat("deepseek-v4-pro")
        structured = model.with_structured_output(Decision, include_raw=True)
        bound = structured.first.steps__["raw"]
        self.assertIn("tools", bound.kwargs)
        self.assertNotIn("response_format", bound.kwargs)

    def test_forced_tool_choice_is_relaxed_to_auto(self):
        # Thinking mode answers 400 "Thinking mode does not support this tool_choice" to any forced choice
        model = DeepSeekChat("deepseek-v4-pro")
        structured = model.with_structured_output(Decision, include_raw=True)
        self.assertEqual(structured.first.steps__["raw"].kwargs["tool_choice"], "auto")
        for forced in ["required", "any", True, "get_class_code"]:
            bound = model.bind_tools([get_class_code], tool_choice=forced)
            self.assertEqual(bound.kwargs["tool_choice"], "auto", forced)
        self.assertNotIn("tool_choice", model.bind_tools([get_class_code]).kwargs)

    def _invoke_structured(self, message: AIMessage, include_raw: bool = True):
        model = DeepSeekChat("deepseek-v4-pro")
        result = ChatResult(generations=[ChatGeneration(message=message)])
        with mock.patch.object(DeepSeekChat, "_generate", return_value=result):
            return model.with_structured_output(Decision, include_raw=include_raw).invoke("parse this")

    def test_structured_output_uses_tool_call(self):
        message = AIMessage(content="", tool_calls=[{"name": "Decision", "args": {"decision": "ID-Based"},
                                                     "id": "call_1"}])
        self.assertEqual(self._invoke_structured(message)["parsed"], Decision(decision="ID-Based"))

    def test_structured_output_falls_back_to_json_in_content(self):
        # With tool_choice relaxed to "auto", the model sometimes answers with the JSON object as plain content
        message = AIMessage(content='```json\n{\n  "decision": "DTO-Based"\n}\n```')
        output = self._invoke_structured(message)
        self.assertEqual(output["parsed"], Decision(decision="DTO-Based"))
        self.assertIsNone(output["parsing_error"])
        self.assertIs(output["raw"].content, message.content)
        self.assertEqual(self._invoke_structured(message, include_raw=False), Decision(decision="DTO-Based"))

    def test_structured_output_without_json_stays_unparsed(self):
        for content in ["I choose DTO-Based.", '{"unexpected": 1}']:
            self.assertIsNone(self._invoke_structured(AIMessage(content=content))["parsed"], content)

    def test_init_model_builds_tool_and_structured_models(self):
        tooling = init_model("mm_deepseek/deepseek-v4-pro::high", mode="tooling", tools=[get_class_code])
        self.assertEqual(tooling.kwargs["tools"][0]["function"]["name"], "get_class_code")
        self.assertIsInstance(tooling.bound, DeepSeekChat)
        structured = init_model("mm_deepseek/deepseek-v4-pro::high", mode="structured", output_type=Decision)
        self.assertIsNotNone(structured)


if __name__ == "__main__":
    unittest.main()
