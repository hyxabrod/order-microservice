# order-microservice

Spring Boot service that exposes orders through a REST API.

## Development
```
./gradlew bootRun
```

## Docker
```
docker build -t order-service:latest .
```

## Kubernetes
Apply `k8s-order.yaml` once you update the image reference.
