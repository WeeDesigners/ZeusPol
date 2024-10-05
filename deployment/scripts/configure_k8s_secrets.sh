#!/bin/bash
# !IMPORTANT this script contains relative paths, it should be run only within the makefile
echo "
  ______              _____      _
 |___  /             |  __ \    | |
    / / ___ _   _ ___| |__) |__ | |
   / / / _ \ | | / __|  ___/ _ \| |
  / /_|  __/ |_| \__ \ |  | (_) | |
 /_____\___|\__,_|___/_|   \___/|_|


"
trap "exit" INT
echo "Welcome to the Zeuspol Application deployment :)"
echo "Zeus communicates with themis, thus you need to provide some configuration values for themis to work properly on your cluster"
echo "Please run the command: kubectl config view --flatten"
    #echo -n "Kubernetes master ip ( e.g 192.168.49.2): "
    #read -r KUBERNETES_MASTER
    #echo
echo -n "Kubernetes certificate-authority-data in base64 (it already is you read it from the output of kubectl config view --flatten): "
read -r KUBERNETES_CA_CERT_DATA
echo
echo -n "Kubernetes client-certificate-data in base64: "
read -r KUBERNETES_CLIENT_CERT_DATA
echo
echo -n "Kubernetes client-key-data in base64: "
read -r KUBERNETES_CLIENT_KEY_DATA

kubectl apply -f deployment/themis/00-ns.yaml
kubectl create secret generic --namespace=themis-executor themis-secrets-k8s \
--from-literal=KUBERNETES_CA_CERT_DATA="$KUBERNETES_CA_CERT_DATA" \
--from-literal=KUBERNETES_CLIENT_CERT_DATA="$KUBERNETES_CLIENT_CERT_DATA" \
--from-literal=KUBERNETES_CLIENT_KEY_DATA="$KUBERNETES_CLIENT_KEY_DATA"