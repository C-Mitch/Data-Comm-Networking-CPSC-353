FROM openjdk:jdk-alpine

COPY MtServer.class /deployments/
COPY ClientHandler.class /deployments/

CMD cd /deployments; java MtServer

