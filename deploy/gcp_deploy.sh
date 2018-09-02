gcloud container clusters create test-demo --zone=asia-east1-b --cluster-version=1.10.5-gke.0
# (for the cluster version, can check: 'gcloud container get-server-config' in advance)



gcloud container node-pools create pool-1 --cluster=test-demo --machine-type=n1-standard-1 --num-nodes=2 --service-account='java-dev-kubernetes@appspot.gserviceaccount.com' --node-version=1.10.5-gke.0 --node-labels='type=first' --tags=shuliu
# (note: node-label 对应的是 kubernetes labels 然后可以用于node selector )
