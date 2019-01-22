1. Go to root app folder
cd <HelloWorldServer>

2. run project in IDE or via docker:

-Publish local docker container:

sudo sbt docker:publishLocal

-Run local container:

sudo docker run --rm -p9000:9000 hello-world-server:0.1-SNAPSHOT
