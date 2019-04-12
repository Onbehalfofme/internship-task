FROM maven:alpine

# image layer
WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
RUN cd target
RUN ls target
RUN chmod 7777 ./target/app.jar
CMD ["java","-jar","./target/app.jar"]