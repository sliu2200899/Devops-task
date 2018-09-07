# Devops-task
a small devops task which includes developing simple api and deploying the task on gcp using kubernetes

# Instructions
This is a basic Spring boot project. In order to launch the api, you should take the following steps:

1. perform the gradle command and package java program: <br/>
      ./gradlew build 

2. build docker image: <br/>
      docker build -f Dockerfile -t test-docker .

3. modify docker tag: <br/>
      docker tag test-docker gcr.io/java-dev-kubernetes/test-docker

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
      kubectl apply -f test-autoscaler.yaml <br/>

8. after waiting a couple of minutes, we can get the external ip by performing the command: <br/>
      kubectl get ing   <br/>
   so we know the external ip is 35.241.58.191

9. finally, we can query the api:  <br/>
   
   9.1 query api:  http://35.241.58.191/ <br/>    
       I will inform you of querying the api ./nxchallenge/astarisborn <br/>    
       
         
   9.2 query api:  http://35.241.58.191/nxchallenge/astarisborn/ <br/>    
       I will show the general response of Hello world. <br/>    
   
   
   9.3 query api:  http://35.241.58.191/about <br/>    
       I will show the signal saying that you are lost, please query ./nxchallenge/astarisborn. <br/>    
       
       
   9.4 regarding identity management, we would assign specific token to user. So next time when uses access the api as well 
   as his token, I will show Hello world as well as the map coordinates of where the pod ip is hosted.   <br/>  
   
   for example, the secret for the use is "newtonx-challenge", then the processed token would be "bmV3dG9ueC1jaGFsbGVuZ2VuZXd0b254LXBhZGRpbmc=" <br/> 
       
   so when user access the api:  http://35.241.58.191/nxchallenge/astarisborn/bmV3dG9ueC1jaGFsbGVuZ2VuZXd0b254LXBhZGRpbmc= <br/>    
   
   I will show the information like follows:  <br/>     
       
       "response":"Hello World"
       
       "myHostIP":"104.199.129.66"
       "latitude":"39.0438"
       "longitude":"-77.4874"


# Some points that need to explain:
# Deployment:
1. scalability <br/>
leverage HorizontalPodAutoscaler in kubernetes to adjust the number of pods dynamically.
use cpu and memory as metrics to determine whether we need to scale out.

2. health check <br/>
develop the api /healthz to perform the health check <br/>

3. pod antiaffinity <br/>
prevent two pods from scattering within the same node, which increase the performance and efficiency.


# Development:
1. podIp and nodeIp <br/>
If you want to get the private address of pod ip or node ip, we can expose pod information to containers via fieldRef through environment variables.
In this case, because we want the map coordinates of host, so it is easy to get current external ip of node, and call the api to get the coordinates.


2. identity management <br/>
Frankly speaking, I'm not familiar with IDM or related professional IDM framework or service from like okta or mulesoft.
Basically, I know it would be straightforward and easy to calculating some token the first time user access the api, and storing the token in the database. 
But in this case, to make it simple, I just calculate the token and compare the token everytime user access the api. Specifically, I will use our confidential 
algorithm to calculate the token which would be assigned to users. So the next time, the customer can use this token to identify himself and append to the 
original api to get some extra information. 

