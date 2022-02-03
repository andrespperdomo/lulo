FROM openjdk:8-jdk-alpine
ADD target/lulobank.jar lulobank.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","lulobank.jar"]