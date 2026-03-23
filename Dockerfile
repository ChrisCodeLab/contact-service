#------------Stage 1: Build------------
FROM maven:3.9.9-eclipse-temurin-21 AS build

#set working directory for stage 1
WORKDIR /app

#layered caching improves performance
COPY pom.xml .

#maven downloads all required dependencies
RUN mvn dependency:go-offline

#copies source code
COPY src ./src

#maven creates .jar file
RUN mvn clean package -DskipTests


#------------Stage 2: Run------------
	
#Use lightweight JDK image as base image (the project needs to run java)
FROM eclipse-temurin:21-jre-jammy

#Set working directory for stage 2
WORKDIR /app

#copies from stage 1 (build) and stores into stage 2
COPY --from=build /app/target/*.jar app.jar

#Expose port
EXPOSE 8080

#Run application by adding the entry point
ENTRYPOINT ["java","-jar","app.jar"]