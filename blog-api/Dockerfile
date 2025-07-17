FROM  maven:3.9.10-amazoncorretto-21-alpine AS build
WORKDIR /build
COPY pom.xml ./ 
COPY src ./src
RUN mvn -DskipTests=true  package

FROM openjdk:21-jdk-slim 
WORKDIR /app
COPY --from=build /build/target/*.jar ./app.jar
ENTRYPOINT [ "java" ,"-jar", "app.jar"]