package com.presentation.demo_rag.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


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
