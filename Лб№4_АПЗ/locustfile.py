from locust import HttpUser, task, between

class BackendUser(HttpUser):
    wait_time = between(0.1, 0.5)
    
    @task(3)
    def test_cpu_load(self):
        self.client.get("/api/test/cpu")
    
    @task(1)
    def test_ping(self):
        self.client.get("/api/test/ping")