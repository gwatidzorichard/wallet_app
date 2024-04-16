FROM openjdk:17-jdk-alpine
COPY /target/wallet-app-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
EXPOSE 8989
ENTRYPOINT ["java","-jar","wallet-app-0.0.1-SNAPSHOT.jar"]
