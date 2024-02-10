FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
# ./gradlew build
# docker build  -t hicoder-backend .