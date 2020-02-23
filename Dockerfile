FROM gcr.io/spark-operator/spark:v2.4.5
MAINTAINER jim@cloudphysics.com

# Need procps, coreutils, and create /tmp/spark-events
# https://issues.apache.org/jira/browse/SPARK-25516
# In there, it points out that you only need procps and coreutils
# if you are going to run non-k8s processes and adding this in
# our image makes it slightly more universal for testing.
RUN set -ex && \
    apt-get update && \
    apt install -y procps coreutils curl && \
    rm -rf /var/cache/apt/*

# COPY target/universal/edison-1.0-SNAPSHOT.zip .
WORKDIR /opt/spark/work-dir
COPY \
  target/scala-2.11/*.jar \
  log4j.properties \
  ./

# Spark history server if running
EXPOSE 18080

# CMD env
CMD /bin/bash
