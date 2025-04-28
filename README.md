# Interacting with the LLM
In this branch, we're only using two dependencies: one for Spring Web and one Spring AI for interacting with the LLM of our choice (here: Ollama):
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-starter-model-ollama</artifactId>
</dependency>
```

We need to tell Spring which Ollama model we're using in `application.properties`:
```
spring.ai.ollama.chat.model=llama3.1
```

And we can create the simplest `ChatClient` configuration
```java
@Configuration  
public class ChatClientConfig {  
  
    @Bean  
    public ChatClient chatClient(ChatClient.Builder builder) {  
        return builder.build();  
    }  
  
}
```

That we make use of in the rest controller class:
```java
@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

     @PostMapping("/chat")
     public ResponseEntity<String> chat(@RequestBody String message) {
        String body = chatClient.prompt()
                .user(message)
                .call()
                .content();

         return ResponseEntity.ok(body);
     }

}
```

The rest controller exposes one POST endpoint (`/chat`) on port 8080 that we can call to interact with the LLM.
