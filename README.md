# Creating a RAG
Now that we have a vector store that we can load with data, we can configure the `ChatClient` with the Spring AI Advisors API.
We did this previously when we implemented a simple chat memory (see the `chat-memory` branch).
Since Spring AI M7, we need to import a new dependency to use the `QuestionAnswerAdvisor`.
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-advisors-vector-store</artifactId>
</dependency>
```

We can now create a `QuestionAnswerAdvisor` that will use the vector store to get access to the data we loaded in the vector store.
```java
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore) {
        return builder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                        new QuestionAnswerAdvisor(vectorStore)
                ).build();
    }

}
```

Now when we ask a question (send a POST request to `/chat`), the `QuestionAnswerAdvisor` will use the vector store to find the most relevant data and use it as part of the response.
To try it out, you can first run one of the previous branches and ask about the "QuantumMesh Network Optimizer". 
It will probably not know or make stuff up. Then compare it to running this branch! It will now be able to answer questions about the "QuantumMesh Network Optimizer" and other data we loaded into the vector store.