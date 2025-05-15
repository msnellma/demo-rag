FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S app && adduser -S app -G app

USER app
COPY target/*.jar app.jar
EXPOSE 8082

# Set Ollama url to the host url
ENV SPRING_AI_OLLAMA_BASE-URL=http://host.docker.internal:11434
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://pgvector:5432/mydatabase
ENV SPRING_DATASOURCE_USERNAME=myuser
ENV SPRING_DATASOURCE_PASSWORD=secret

ENTRYPOINT ["java", "-jar", "/app.jar"]