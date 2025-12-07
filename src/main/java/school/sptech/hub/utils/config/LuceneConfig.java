package school.sptech.hub.utils.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class LuceneConfig {

    @Value("${lucene.index.path:./lucene-index}")
    private String indexPath;

    @Bean
    public Analyzer analyzer() {
        return new StandardAnalyzer();
    }

    @Bean
    public Directory indexDirectory() throws IOException {
        return FSDirectory.open(Paths.get(indexPath));
    }

    @Bean
    public IndexWriterConfig indexWriterConfig(Analyzer analyzer) {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        return config;
    }

    @Bean
    public IndexWriter indexWriter(Directory directory, IndexWriterConfig config) throws IOException {
        IndexWriter writer = new IndexWriter(directory, config);

        // Garante que o índice seja criado mesmo que vazio
        writer.commit();

        return writer;
    }

    @Bean
    @DependsOn("indexWriter")
    public SearcherManager searcherManager(Directory directory) throws IOException {
        // Remove dependência circular - só usa o Directory após IndexWriter estar pronto
        return new SearcherManager(directory, null);
    }
}
