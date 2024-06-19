FROM openjdk:17
EXPOSE 9090
ADD target/iam-service-v1.jar iam-service-v1.jar
ENTRYPOINT ["java","-jar","/iam-service-v1.jar"]