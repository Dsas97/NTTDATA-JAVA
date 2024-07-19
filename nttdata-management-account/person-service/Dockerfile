FROM openjdk:21
VOLUME /tmp
ENV IMG_PATH=/img
EXPOSE 8081
RUN mkdir -p /img
ADD ./build/libs/person-service-0.0.1-SNAPSHOT.jar person-service.jar
ENTRYPOINT ["java","-jar","/persona-service.jar"]
