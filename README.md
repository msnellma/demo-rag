# Logging LLM requests and responses
As we're developing Gen AI applications, it is useful to log requests and responses to/from the LLMs for debugging, auditing and understanding the models.
Spring AI comes with a `SimpleLoggerAdvisor` that we can use to log the requests and responses. We can add a `SimpleLoggerAdvisor` to the `ChatClient` configuration.
```java
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore) {
        return builder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                        new QuestionAnswerAdvisor(vectorStore),
                        new SimpleLoggerAdvisor() 
                ).build();
    }

}
```
Additionally, we need to set the Spring AI logging level in the `application.properties`.
```properties
logging.level.org.springframework.ai.chat.client.advisor=DEBUG
```
When you run the application, you will see a lot of info about the request and responses in the console. 
For instance, we can see how many tokens the model uses, which is really important for understanding costs.
Specifically when working with RAGs, knowing how many tokens go into the models is also important for understanding model performance.
When you ask a question about a topic the model has info about in the vector store, you'll see that the model gets a lot of tokens, depending on how many matches are found in the vector store and how big the text chunks are.
If the tokens exceed the model context window, this information can completely overwhelm the model and it will not be able to answer the question.
In fact, it will likely not see the question at all, because it is pushed out of the context window. 
This is one reason why we need to be careful about the size of the text chunks we store in the vector store.

In a subsequent branch, we'll look at how we can configure the `QuestionAnswerAdvisor` to limit the number of matches returned from the vector store through parameters like top K.
We'll also look at how we can configure the `SimpleLoggerAdvisor` to extract more specific information about the results fetched from the vector store.