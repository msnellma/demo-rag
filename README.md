# Configuring the Vector Store
Now we add some dependencies for setting up the vector database (vectore store).
We'll use one from Postgres. Additionally, we add some Docker Compose support.
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-pgvector</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-spring-boot-docker-compose</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

We create this `compose.yaml` file for the vector store
```yaml
services:
  pgvector:
    image: 'pgvector/pgvector:pg16'
    environment:
      - 'POSTGRES_DB=mydatabase'
      - 'POSTGRES_PASSWORD=secret'
      - 'POSTGRES_USER=myuser'
    labels:
      - "org.springframework.boot.service-connection=postgres"
    ports:
      - '5432:5432'
```

And in `application.properties` we tell Spring to set up the database schema. 
Because we'll use Ollama's nomic-embed-text embedding model, we also set the embedding dimension to 768.
(This figure depends on the embedding model we're using. If it's wrong, we'll get exception thrown telling us what it should be)
```properties
spring.ai.ollama.embedding.model=nomic-embed-text

spring.ai.vectorstore.pgvector.initialize-schema=true
spring.ai.vectorstore.pgvector.dimensions=768
```

Now we can start the Postgres database with Docker Compose (or just by starting the Spring app since we have Docker Compose support).
```bash
docker compose up -d
```

You can connect to it with DBeaver or some other service to check that it's running and its schema.
