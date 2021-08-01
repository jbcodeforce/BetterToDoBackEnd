#!/bin/bash

if [[ -z $(oc get secret postgresql-creds 2> /dev/null) ]]
then
  echo "#################"
  echo "Deploy postgresql"
  echo "#################"
  oc apply -k kustomize/env/postgres
fi 
