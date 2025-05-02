# Configuring the RAG
The `QuestionAnswerAdvisor` used to implement RAG capabilites have some default settings for deciding what to fetch from the vector store.
The prompt is converted to a vector with the embedding model and the top K results are fetched from the vector store based on some similarity metric in vector space.
We can configure the `QuestionAnswerAdvisor` to decide the "score" or threshold required for a match, and the maximum number of vectors that can be fetched (top K).
We do this with the `SearchRequest` object.
```java
@Configuration
public class QuestionAnswerAdvisorConfig {
    
    @Bean
    public QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
        return new QuestionAnswerAdvisor(vectorStore,
                SearchRequest.builder()
                        .similarityThreshold(0.6)
                        .topK(3)
                        .build());
    }
    
}
```
Then, we autowire this Spring bean into the `ChatClient` configuration.
```java
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, QuestionAnswerAdvisor questionAnswerAdvisor) {
        return builder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                        questionAnswerAdvisor,
                        new SimpleLoggerAdvisor()
                ).build();
    }

}
```
This configuration is useful to tune the RAG capabilities of the `ChatClient` to your needs.
Increasing top K will increase the number of vectors fetched from the vector store, and increasing the similarity threshold will increase the "strictness" of the match.
Limiting the number of vectors fetched will reduce the risk of overloading the LLM with too much information, but will also reduce the amount of information available to the LLM.