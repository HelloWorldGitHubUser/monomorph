import unittest
from types import SimpleNamespace

from monomorph.monomorph import MonoMorph


def api_class(name: str, microservice: str):
    return SimpleNamespace(name=name, microservice=microservice, client_microservices=None)


class FakeMonoMorph:
    """Stands in for MonoMorph: invoking classes per API class, as {client microservice: [invoking classes]}."""
    _assign_client_microservice = MonoMorph._assign_client_microservice

    def __init__(self, invoking: dict[str, dict[str, list[str]]]):
        self.invoking = invoking

    def _get_invoking_classes(self, api_class, api_classes, include_same=True):
        return self.invoking.get(api_class.name, {})


class TestClientMicroserviceCycles(unittest.TestCase):

    def test_mutually_invoking_api_classes_do_not_recurse_forever(self):
        # Pet (customers) and Visit (visits) reference each other, like a bidirectional JPA association
        classes = {"Pet": api_class("Pet", "customers"), "Visit": api_class("Visit", "visits")}
        mono = FakeMonoMorph({"Pet": {"customers": ["Visit"], "visits": ["Visit"]},
                              "Visit": {"visits": ["Pet"], "customers": ["Pet"], "vets": ["Clinic"]}})
        for c in classes.values():
            mono._assign_client_microservice(c, classes)
        self.assertEqual(classes["Pet"].client_microservices, {"visits", "vets"})
        self.assertEqual(classes["Visit"].client_microservices, {"customers", "vets"})

    def test_acyclic_clients_are_propagated(self):
        classes = {"A": api_class("A", "s1"), "B": api_class("B", "s2")}
        mono = FakeMonoMorph({"A": {"s1": ["B"]}, "B": {"s3": ["X"]}})
        mono._assign_client_microservice(classes["A"], classes)
        self.assertEqual(classes["A"].client_microservices, {"s3"})
        self.assertEqual(classes["B"].client_microservices, {"s3"})


if __name__ == "__main__":
    unittest.main()
