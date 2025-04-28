# Demo RAG
This is a demo RAG application with Spring AI. Each branch demonstrate how each part of RAG can be implemented:
- `main`: The main branch contains this readme file.
- `llm`: This branch contains the configuration of `ChatClient` to interact with the LLM
- `chat-memory`: This branch implements a simple chat memory solution to the `ChatClient`config
- `vectorstore-setup`: This branch contains the configuration of pgvector vector database
- `document-embedding`: This branch contains the implementation of document embedding into the vector store with a document reader
- `rag`: This branch contains the configuration of `ChatClient` to implement RAG capabilities
- `logging`: This branch contains the `ChatClient` configuration to log the LLM requests and responses
## Requirements
This project requires Java 21, and Ollama with LLama 3.1 and Nomic-embed-text as embedding model unless you have access to some other LLM API.
In that case, you need to change the `ChatClient` configuration in `application.properties` file, and provide the API key.
## Run the application
To run the application, open your terminal or IDE of choice, choose the branch you want to run, and type: 
```bash
mvn spring-boot:run
```
