# bank-backend
Fake Bank - Java/Spring Boot Backend

# Requirements
## Software
* Java 11

## Environment
* Docker
* Minikube

This is set up using minikube.
Note about installing: latest helm (v2.14.3) is not compatible with Kubernetes 1.16.0.

As workaround, configure minikube with an older version.

`minikube config set kubernetes-version 1.15.4`

https://github.com/helm/helm/issues/6374

## Tooling
* Maven
* Helm (https://helm.sh)
* Task (https://taskfile.dev)
* Skaffold (https://skaffold.dev)

# Start
`skaffold dev --port-forward`

Manual deploy (requires Docker images)  
`helm upgrade --install --name fake-bank-backend .`

Update the child charts:  
`helm dependency update`

Manual delete
`helm delete --purge fake-bank-backend`

# Logs
`kubectl logs -f deploy/fake-bank-backend`

# Debug
Connect to a container:  
`kubectl exec -it fake-bank-backend-database-0 bash`
