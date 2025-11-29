package school.sptech.hub.infraestructure.persistance.livroPersistance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import school.sptech.hub.application.gateways.livro.LivroSearchGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.infraestructure.gateways.livro.LivroEntityMapper;

import java.util.List;

@Repository
public class LivroSearchRepositoryHibernate implements LivroSearchGateway {

    private static final Logger logger = LoggerFactory.getLogger(LivroSearchRepositoryHibernate.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Livro> search(String query, int page, int size) {
        SearchSession searchSession = Search.session(em);
        int offset = page * size;

        logger.info("Buscando livros com query: '{}', page: {}, size: {}", query, page, size);

        // Se a query for vazia ou nula, retorna página vazia
        if (query == null || query.trim().isEmpty()) {
            logger.warn("Query vazia ou nula. Retornando página vazia.");
            return new PageImpl<>(List.of(), PageRequest.of(page, size), 0);
        }

        SearchResult<LivroEntity> result = searchSession.search(LivroEntity.class)
                .where(f -> f.match()
                        .fields("titulo", "autor", "editora", "descricao")
                        .matching(query)
                        .fuzzy(2)) // Aumentei para tolerar mais erros
                .fetch(offset, size);

        List<LivroEntity> entities = result.hits();
        long totalHitCount = result.total().hitCount();

        logger.info("Encontrados {} resultados para a query '{}'", totalHitCount, query);

        List<Livro> livros = entities.stream()
                .map(LivroEntityMapper::toDomain)
                .toList();

        return new PageImpl<>(livros, PageRequest.of(page, size), totalHitCount);
    }
}
