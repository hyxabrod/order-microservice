./gradlew clean build
eval $(minikube docker-env)  
docker build -t order-service:1.0 .
kubectl delete pod -l app=order-service
kubectl apply -f k8s-order.yaml
kubectl get pods -l app=order-service