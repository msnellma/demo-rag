package com.presentation.demo_rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
