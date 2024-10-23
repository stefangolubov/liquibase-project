#!/bin/bash

# Namespace to restart deployments in
NAMESPACE=default

# Get all deployments in the namespace
DEPLOYMENTS=$(kubectl get deployments -n $NAMESPACE -o jsonpath='{.items[*].metadata.name}')

# Get all statefulsets in the namespace
STATEFULSETS=$(kubectl get statefulsets -n $NAMESPACE -o jsonpath='{.items[*].metadata.name}')

# Restart each deployment
for DEPLOYMENT in $DEPLOYMENTS; do
echo "Restarting deployment: $DEPLOYMENT"
kubectl rollout restart deployment $DEPLOYMENT -n $NAMESPACE
done

# Restart each statefulset
for STATEFULSET in $STATEFULSETS; do
echo "Restarting statefulset: $STATEFULSET"
kubectl rollout restart statefulset $STATEFULSET -n $NAMESPACE
done