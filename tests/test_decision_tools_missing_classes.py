import unittest
from unittest import mock

from monomorph.decision.tools import AnalysisTools


class TestAnalysisToolsMissingClasses(unittest.TestCase):

    def test_classes_missing_from_analysis_are_skipped(self):
        # The analyzer ignores Java records, but the decomposition still lists them as relevant classes
        app_model = mock.Mock()
        app_model.get_class_names.return_value = ["a.A", "a.B"]
        app_model.get_method_names.return_value = ["a.A::m()", "a.Rec::x()"]
        with mock.patch.object(AnalysisTools, "_build_interaction_dict"):
            tools = AnalysisTools(app_model, decomposition=None, relevant_classes=["a.A", "a.Rec", "a.B"])
        self.assertEqual(tools.names, ["a.A", "a.B"])
        self.assertEqual(tools.method_names, ["a.A::m()"])


if __name__ == "__main__":
    unittest.main()
