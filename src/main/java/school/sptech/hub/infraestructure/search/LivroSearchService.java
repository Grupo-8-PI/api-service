package school.sptech.hub.infraestructure.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.springframework.stereotype.Service;
import school.sptech.hub.domain.entity.Livro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivroSearchService {

    private final IndexWriter indexWriter;
    private final SearcherManager searcherManager;
    private final Analyzer analyzer;

    public LivroSearchService(IndexWriter indexWriter, SearcherManager searcherManager, Analyzer analyzer) {
        this.indexWriter = indexWriter;
        this.searcherManager = searcherManager;
        this.analyzer = analyzer;
    }

    public void indexLivro(Livro livro) {
        try {
            Document document = createDocument(livro);

            // Remove documento existente se houver (por ISBN)
            indexWriter.deleteDocuments(new Term("isbn", livro.getIsbn()));

            // Adiciona novo documento
            indexWriter.addDocument(document);
            indexWriter.commit();
            searcherManager.maybeRefresh();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao indexar livro: " + livro.getIsbn(), e);
        }
    }

    public void indexAllLivros(List<Livro> livros) {
        try {
            // Limpa índice existente
            indexWriter.deleteAll();

            for (Livro livro : livros) {
                Document document = createDocument(livro);
                indexWriter.addDocument(document);
            }

            indexWriter.commit();
            searcherManager.maybeRefresh();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao indexar livros em lote", e);
        }
    }

    public void initializeEmptyIndex() {
        try {
            // Inicializa o índice vazio se não existir
            indexWriter.deleteAll();
            indexWriter.commit();
            searcherManager.maybeRefresh();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar índice vazio", e);
        }
    }

    public SearchResult searchLivros(String query, int page, int size) {
        try {
            searcherManager.maybeRefresh();
            IndexSearcher searcher = searcherManager.acquire();

            try {
                // Campos para busca
                String[] fields = {"titulo", "autor", "editora", "categoria", "descricao"};
                MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
                parser.setDefaultOperator(QueryParser.Operator.OR);

                Query luceneQuery = parser.parse(QueryParser.escape(query));

                // Calcula offset para paginação
                int offset = page * size;
                int totalToRetrieve = offset + size;

                TopDocs topDocs = searcher.search(luceneQuery, totalToRetrieve);

                List<Integer> livroIds = new ArrayList<>();

                // Pega apenas os resultados da página atual
                int startIndex = offset;
                int endIndex = Math.min(offset + size, topDocs.scoreDocs.length);

                for (int i = startIndex; i < endIndex; i++) {
                    Document doc = searcher.doc(topDocs.scoreDocs[i].doc);
                    String idStr = doc.get("id");
                    if (idStr != null) {
                        livroIds.add(Integer.parseInt(idStr));
                    }
                }

                return new SearchResult(livroIds, topDocs.totalHits.value, page, size);

            } finally {
                searcherManager.release(searcher);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Erro durante a busca: " + query, e);
        }
    }

    public void deleteLivroFromIndex(String isbn) {
        try {
            indexWriter.deleteDocuments(new Term("isbn", isbn));
            indexWriter.commit();
            searcherManager.maybeRefresh();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover livro do índice: " + isbn, e);
        }
    }

    private Document createDocument(Livro livro) {
        Document document = new Document();

        // Campos indexáveis e pesquisáveis
        document.add(new StringField("id", livro.getId().toString(), Field.Store.YES));
        document.add(new StringField("isbn", livro.getIsbn(), Field.Store.YES));
        document.add(new TextField("titulo", livro.getTitulo() != null ? livro.getTitulo() : "", Field.Store.YES));
        document.add(new TextField("autor", livro.getAutor() != null ? livro.getAutor() : "", Field.Store.YES));
        document.add(new TextField("editora", livro.getEditora() != null ? livro.getEditora() : "", Field.Store.YES));
        document.add(new TextField("categoria", livro.getCategoria() != null ? livro.getCategoria().getNome() : "", Field.Store.YES));
        document.add(new TextField("descricao", livro.getDescricao() != null ? livro.getDescricao() : "", Field.Store.YES));

        // Campos adicionais para filtros
        if (livro.getAnoPublicacao() != null) {
            document.add(new StringField("anoPublicacao", livro.getAnoPublicacao().toString(), Field.Store.YES));
        }
        if (livro.getPreco() != null) {
            document.add(new StringField("preco", livro.getPreco().toString(), Field.Store.YES));
        }

        return document;
    }

    public static class SearchResult {
        private final List<Integer> livroIds;
        private final long totalElements;
        private final int currentPage;
        private final int pageSize;

        public SearchResult(List<Integer> livroIds, long totalElements, int currentPage, int pageSize) {
            this.livroIds = livroIds;
            this.totalElements = totalElements;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }

        public List<Integer> getLivroIds() { return livroIds; }
        public long getTotalElements() { return totalElements; }
        public int getCurrentPage() { return currentPage; }
        public int getPageSize() { return pageSize; }
        public int getTotalPages() { return (int) Math.ceil((double) totalElements / pageSize); }
        public boolean hasNext() { return currentPage < getTotalPages() - 1; }
        public boolean hasPrevious() { return currentPage > 0; }
    }
}
