# imagem default
FROM jenkinsci/jenkins:lts
MAINTAINER Evandro Couto <vandocouto@gmail.com>

# copy
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy
COPY role-authz.groovy /usr/share/jenkins/ref/init.groovy.d/role-authz.groovy

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

# env
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# run
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt


