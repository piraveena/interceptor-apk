
FROM eclipse-temurin:17.0.10_7-jre as builder

# Add Maintainer Info
LABEL maintainer="piraveena18@gmailcom"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/interceptor-apk-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} interceptor-apk-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/interceptor-apk-0.0.1-SNAPSHOT.jar"]