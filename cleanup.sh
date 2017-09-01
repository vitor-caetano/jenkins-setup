#!/usr/bin/env bash

docker stack rm jenkins

docker secret rm jenkins-user

docker secret rm jenkins-pass