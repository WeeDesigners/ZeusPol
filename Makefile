
docker-build-local:
	eval $(minikube docker-env)
	mvn clean install
	docker build --no-cache -t zeuspol .

docker-build-local-windows:
	powershell -Command "minikube -p minikube docker-env --shell powershell | Invoke-Expression"
	mvn clean install
	docker build --no-cache -t zeuspol .

docker-build-and-push:
	mvn clean install
	docker build --no-cache -t socz3qqq/zeuspol .
	docker push socz3qqq/zeuspol:latest

deploy:
	make deploy-zeuspol
	make deploy-themis
	make deploy-hephaestus
	make deploy-microservices-demo

deploy-zeuspol:
	kubectl apply -f deployment/zeuspol/00-zeuspol-ns.yaml
	kubectl apply -f deployment/zeuspol/01-zeuspol-dep.yaml
	kubectl apply -f deployment/zeuspol/02-zeuspol-svc.yaml

deploy-themis:
	kubectl apply -f deployment/themis

deploy-hephaestus:
	kubectl apply -f deployment/hephaestus/manifests
	kubectl apply -f deployment/hephaestus/volume-creation

deploy-microservices-demo:
	make deploy-monitoring
	make deploy-sock-shop

deploy-sock-shop:
	kubectl apply -f deployment/microservices-demo/manifests

deploy-monitoring:
	kubectl apply -f deployment/microservices-demo/manifests-monitoring

deploy-local:
	make deploy-zeuspol-local
	make deploy-themis
	make deploy-hephaestus
	make deploy-microservices-demo

deploy-zeuspol-local:
	#idk here?
	minikube image load zeuspol:latest
	kubectl apply -f deployment/zeuspol/00-zeuspol-ns.yaml
	kubectl apply -f deployment/zeuspol/01-zeuspol-dep-local.yaml
	kubectl apply -f deployment/zeuspol/02-zeuspol-svc.yaml

undeploy:
	make undeploy-zeuspol
	make undeploy-themis
	make undeploy-hephaestus
	make undeploy-microservices-demo

undeploy-zeuspol:
	kubectl delete -f deployment/zeuspol/00-zeuspol-ns.yaml --ignore-not-found=true

undeploy-themis:
	kubectl delete namespaces themis-executor --ignore-not-found=true

undeploy-hephaestus:
	kubectl delete namespaces hephaestus --ignore-not-found=true
	kubectl delete -f deployment/hephaestus/volume-creation

undeploy-microservices-demo:
	kubectl delete namespaces sock-shop --ignore-not-found=true
	kubectl delete namespaces monitoring --ignore-not-found=true

undeploy-local:
	make undeploy-zeuspol-local
	make undeploy-themis
	make undeploy-hephaestus
	make undeploy-microservices-demo

undeploy-zeuspol-local:
	kubectl delete -f deployment/zeuspol/00-zeuspol-ns.yaml --ignore-not-found=true

reset-minikube:
	minikube stop
	minikube delete
	minikube start


get-minikube-info:
	minikube service list