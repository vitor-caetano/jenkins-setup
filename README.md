This project is based on the [Running Fault Tolerant Jenkins Inside A Docker Swarm Cluster](https://technologyconversations.com/2016/10/10/running-fault-tolerant-jenkins-inside-a-docker-swarm-cluster/) article.

Base image from the [Offical lts jenkins image](https://hub.docker.com/r/jenkins/jenkins/tags/).

[Jenkins docker image](https://github.com/jenkinsci/docker)

Deploy and test

```bash
eval $(docker-machine env local-swarm-1)
open http://$(docker-machine ip local-swarm-1):8082/jenkins
```
