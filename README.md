
About
=====
A sample stateless service to run on kubernetes cluster.

Use case demonstration
----------------------
A simple Student CRUD operation exposed over REST

Dependency
----------
- Depends on Cassandra DB, and expects it to be available on Kubernets cluster via service name "cassandra"
- Leverages https://github.com/preetam-dutta/kubernetes-cassandra to provide underlying Cassandra DB

Tech stack
----------
- Spring Boot
- Spring Web Flux: Non blocking reactive approach
- TestContainers
- Lombok usage
- Cassandra client (datastax)
- Docker
- Kubernetes

Docker hub
----------
- Docker hub ref: https://cloud.docker.com/repository/docker/preetamdutta/student-rest

Usage
=====

Prerequisite
------------
- You have Docker & Kubernetes already setup

Resolve Dependency 
------------------
1. This project do not demonstrates deployments via helmcharts so manual dependency resoultion would be required for now.
2. Follow Cassandra setup as mentioned in https://github.com/preetam-dutta/kubernetes-cassandra/blob/master/README.md
3. Execute the below command to ensure Cassandra is up and running
   ```bash
   kubectl get pod,service,statefulset --selector="app=cassandra,env=dev"
   ```
   
Execute project
--------------- 
  ```bash
    git clone <this project>
    cd <this project root>/kubernetes
    kubectl apply -f replica-set.yaml
  ```

Dev notes
=========
- Please refer to notes/quick-notes.md for developer quick notes