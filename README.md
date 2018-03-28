This project is based on the [Automating Jenkins Docker Setup](https://technologyconversations.com/2017/06/16/automating-jenkins-docker-setup/) article.

Base image from the [Offical lts jenkins image](https://hub.docker.com/r/jenkins/jenkins/tags/).

[Jenkins docker image](https://github.com/jenkinsci/docker) on GitHub.

Deploy and test

```bash
./dm-swarm.sh
eval $(docker-machine env local-swarm-1)
./deploy.sh
open http://$(docker-machine ip local-swarm-1):8080/
```
