package school.sptech.hub.infraestructure.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import school.sptech.hub.infraestructure.persistance.livroPersistance.LivroEntity;

@Component
public class HibernateSearchIndexBuilder implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(HibernateSearchIndexBuilder.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            logger.info("Iniciando indexação do Hibernate Search...");
            
            SearchSession searchSession = Search.session(entityManager);
            MassIndexer massIndexer = searchSession.massIndexer(LivroEntity.class)
                    .threadsToLoadObjects(5);
            
            massIndexer.startAndWait();
            
            logger.info("Indexação do Hibernate Search concluída com sucesso!");
        } catch (InterruptedException e) {
            logger.error("Erro durante a indexação do Hibernate Search", e);
            Thread.currentThread().interrupt();
        }
    }
}

