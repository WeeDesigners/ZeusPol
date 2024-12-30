docker-build:
	eval $(minikube docker-env)
	./scripts/docker-build.sh

docker-build-local-windows:
#  TODO this may work, but I did not test it
	powershell -Command "& minikube -p minikube docker-env | Invoke-Expression; mvn clean install; docker build --no-cache -t weedesigners/zeuspol:latest ."

docker-build-and-push: docker-build
	sudo docker push weedesigners/zeuspol:latest
