FROM openjdk:18

COPY ./target/server.jar .

CMD ["java","-jar","/server.jar"]