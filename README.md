# fake-bank-backend
Fake Bank - Java/Spring Boot Backend

This is a demo project, vaguely to offer a banking-api, but more just to try out new ideas and continue on topics I encounter in my day-to-day software engineering.

# Requirements
## Software
* Java 11

## Environment
* Docker
* Minikube

## Tooling
* Maven
* Helm (https://helm.sh)
* Task (https://taskfile.dev)
* Skaffold (https://skaffold.dev)

Note about installing: Helm 3 supports Kubernetes 1.17, however skaffold only supports Helm 2.
AUR has a helpful `kubernetes-helm2` package to downgrade easily.

https://github.com/GoogleContainerTools/skaffold/issues/2142 

# Start
`skaffold dev --port-forward`

Manual deploy (requires Docker images)  
`helm upgrade --install fake-bank-backend .`

Update the child charts:  
`helm dependency update`

Manual delete
`helm delete --purge fake-bank-backend`

# Logs
`kubectl logs -f deploy/fake-bank-backend`

# Debug
Connect to a container:  
`kubectl exec -it fake-bank-backend-database-0 bash`

# API
A rendered API specification of the endpoints used in the
service can be found under http://localhost:8080/api-specifications 