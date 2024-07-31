
docker-build-local:
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
	kubectl apply -f deployment/hephaestus

deploy-microservices-demo:
	kubectl apply -f deployment/microservices-demo/manifests
	kubectl apply -f deployment/microservices-demo/manifests-monitoring

deploy-local:
	make deploy-zeuspol-local
	make deploy-themis
	make deploy-hephaestus
	make deploy-microservices-demo

deploy-zeuspol-local:
	kubectl apply -f deployment/zeuspol/00-zeuspol-ns.yaml
	kubectl apply -f deployment/zeuspol/01-zeuspol-dep-local.yaml
	kubectl apply -f deployment/zeuspol/02-zeuspol-svc.yaml
