package school.sptech.hub.infraestructure.search;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;

@Service
public class LivroIndexInitializerService {

    private final LivroSearchService livroSearchService;
    private final LivroGateway livroGateway;

    public LivroIndexInitializerService(LivroSearchService livroSearchService, LivroGateway livroGateway) {
        this.livroSearchService = livroSearchService;
        this.livroGateway = livroGateway;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeIndex() {
        try {
            List<Livro> todosLivros = livroGateway.findAll();

            if (!todosLivros.isEmpty()) {
                livroSearchService.indexAllLivros(todosLivros);
                System.out.println("Índice Lucene inicializado com " + todosLivros.size() + " livros");
            } else {
                // Inicializa índice vazio para garantir que existe
                livroSearchService.initializeEmptyIndex();
                System.out.println("Índice Lucene inicializado vazio - nenhum livro encontrado no banco de dados");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar índice Lucene: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reindexAllLivros() {
        try {
            List<Livro> todosLivros = livroGateway.findAll();
            livroSearchService.indexAllLivros(todosLivros);
            System.out.println("Reindexação completa - " + todosLivros.size() + " livros reindexados");
        } catch (Exception e) {
            throw new RuntimeException("Erro durante reindexação: " + e.getMessage(), e);
        }
    }
}
