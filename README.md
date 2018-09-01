# Devops-task
a small devops task which includes developing simple api and deploying the task on gcp using kubernetes

# Instructions
This is a basic Spring boot project. In order to launch the api, you should take the following steps:

1. perform the gradle command and package java program: <br/>
      ./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar

2. build docker image: <br/>
      docker build -f Dockerfile -t test-docker .

3. modify docker tag: <br/>
      docker tag test-docker-5 gcr.io/java-dev-kubernetes/test-docker

4. push the image to the gcr. (note that we should login to gcr in advance, gcloud auth configure-docker) <br/>
      docker push gcr.io/java-dev-kubernetes/test-docker

5. create the kubernetes cluster on gcp using <br/>
      gcloud container clusters create test-demo --zone=asia-east1-b --cluster-version=1.10.5-gke.0

6. create the node-pool on clusters <br/>
      gcloud container node-pools create pool-1 --cluster=test-demo --machine-type=n1-standard-1 --num-nodes=2 <br/> --service-account='java-dev-kubernetes@appspot.gserviceaccount.com' --node-version=1.10.5-gke.0 --node-labels='type=first' --tags=shuliu

7. deploy all the files inside the k8s folder <br/>
      kubectl apply -f rolebinding.yaml <br/>
      kubectl apply -f test-service.yaml <br/>
      kubectl apply -f test-ingress.yaml <br/>
      kubectl apply -f test-deployment.yaml <br/>

8. after waiting a couple of minutes, we can get the external ip by performing the command: <br/>
      kubectl get ing

9. finally, we can query the api like external_ip/nxchallenge/astarisborn/ <br/>

# some points that need to explain:
Deployment:
1. scalability


2. health check


3. node affinity


Development:
1. Jersey client api


2. podIp and nodeIp
If you want to get the private address of pod ip or node ip, we can expose pod information to containers via fieldRef through environment variables.
In this case, because you want the map coordinates of host, so it is easy to get current external ip of node, and call the api to get the coordinates.

3. identity management
Frankly speaking, I'm not familiar with IDM or related professional IDM framework or service from like okta or mulesoft. The approach that our team use to deal with identity problem is calculating and storing token as well as RSA public/private key. Specifically, when the customer accesses our api at the first time, we will decrypt the message, check the timestamp and secret before creating one token for this user. After that, we would save the token in the database. So the next time, the customer can use this token to identify himself.
