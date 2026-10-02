import os
import unittest
from unittest import mock

from langchain_core.messages import AIMessage, HumanMessage, ToolMessage
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

    def test_init_model_builds_tool_and_structured_models(self):
        tooling = init_model("mm_deepseek/deepseek-v4-pro::high", mode="tooling", tools=[get_class_code])
        self.assertEqual(tooling.kwargs["tools"][0]["function"]["name"], "get_class_code")
        self.assertIsInstance(tooling.bound, DeepSeekChat)
        structured = init_model("mm_deepseek/deepseek-v4-pro::high", mode="structured", output_type=Decision)
        self.assertIsNotNone(structured)


if __name__ == "__main__":
    unittest.main()
