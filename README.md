# Loading documents to the Vector Store
We now have a Vector Store that we can load documents into. 
To load documents, we can create a `docs` directory under `resources` and place the documents we want to upload there.
I have added a test document `testdocument.pdf` to the `docs` directory. It contains information about a fictional product called "QuantumMesh Network Optimizer".

We can create a configuration class that makes these resources available in the Spring context:
```java
@Configuration
public class ResourceConfig {

    private final ResourcePatternResolver resourcePatternResolver;

    public ResourceConfig(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @Bean
    public Resource[] resources() {
        try {
            return resourcePatternResolver.getResources("classpath:/docs/*");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load resources from classpath:/docs/*", e);
        }
    }

}
```

This configuration class uses the `ResourcePatternResolver` to load all resources from the `docs` directory and makes them available as a Spring bean.
Next, we need a way to read the documents, and we'll use Tika Document Reader that can read both pdfs and Word docs (and more).
```xml
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-tika-document-reader</artifactId>
</dependency>
```
Then we create a service class to load the documents into the Vector Store:
```java
@Service
public class DocumentIngestionService {

    private final VectorStore vectorStore;
    private final Resource[] resources;
    private static final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);
    private final TextSplitter textSplitter = new TokenTextSplitter(400, 100, 5, 100, true);

    public DocumentIngestionService(VectorStore vectorStore, Resource[] resources) {
        this.vectorStore = vectorStore;
        this.resources = resources;
    }

    @PostConstruct
    public void init() {
        for (Resource resource : resources) {
            var reader = new TikaDocumentReader(resource);
            vectorStore.accept(textSplitter.apply(reader.get()));

            log.info("Vector store loaded with Document: {}", resource.getFilename());
        }
    }

}
```
This service has the vector store and the loaded documents (`resources`) autowired. 
It uses the `TikaDocumentReader` to read the documents and the `TokenTextSplitter` to split the text into chunks.
Note that the first argument of `TokenTextSplitter`, `chunkSize`, can be really important. If it is too small, the text will be split into too many chunks and the RAG may later have issues retaining the context between the chunks.
If it's too large, the context window of the LLM may be exceeded.

The embedding model that we have specified in `application.properties` is used to create the embeddings for the document chunks that go in to the vector store.

When we run the application, the `DocumentIngestionService` will be initialized and the documents will be loaded into the Vector Store if everything goes well.
We can verify this by connecting to the vector store database and check the data in the content and embedding columns.