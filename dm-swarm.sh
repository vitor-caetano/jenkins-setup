#!/usr/bin/env bash

if [[ "$(uname -s )" == "Linux" ]]; then
  export VIRTUALBOX_SHARE_FOLDER="$PWD:$PWD"
fi

for i in 1 2 3; do
    docker-machine create \
        -d virtualbox \
        local-swarm-$i
done

eval $(docker-machine env local-swarm-1)

docker swarm init \
  --advertise-addr $(docker-machine ip local-swarm-1)

TOKEN=$(docker swarm join-token -q manager)

for i in 2 3; do
    eval $(docker-machine env local-swarm-$i)

    docker swarm join \
        --token $TOKEN \
        --advertise-addr $(docker-machine ip local-swarm-$i) \
        $(docker-machine ip local-swarm-1):2377
done

echo ">> The swarm cluster is up and running"
≠