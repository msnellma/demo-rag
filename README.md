# Implementing Chat Memory
By default, the LLM will not recall previous interactions. 
To implement chat memory, we configure the `ChatClient` to use advisors. 
Specifically, we use a `MessageChatMemoryAdvisor` with an `InMemoryChatMemory` instance.
```java
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                ).build();
    }

}
```
Now the LLM can remember previous interactions. (Up to a certain limit, determined by the context window)