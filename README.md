# Deployment

For ease of use we recommend running with [Minikube](https://minikube.sigs.k8s.io/docs/start)

After installing run:
``` minikube start ```

Then clone the [hephaestus](https://github.com/Hephaestus-Metrics/Deployment) repo:

then apply the deployment of [hephaestus](https://github.com/Hephaestus-Metrics/Deployment)
```kubectl apply -f Deployment/manifests/```

Next clone the [Zeuspol](https://github.com/WeeDesigners/ZeusPol) repo and deploy app:
``` kubectl apply -f ZeusPol/deployment```

For now is possible to inspect the output of the applications for example by using [k9s](https://k9scli.io/)

just run k9s and hit enter 2 times on the zeuspol pod.