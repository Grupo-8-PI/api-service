package school.sptech.hub.utils.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheInvalidationHelper {
    private static final Logger logger = LoggerFactory.getLogger(CacheInvalidationHelper.class);

    private final CacheManager cacheManager;

    public CacheInvalidationHelper(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void invalidarCacheHasReserva(Integer livroId) {
        if (livroId == null) {
            return;
        }

        try {
            Cache cache = cacheManager.getCache("hasReservaCache");
            if (cache != null) {
                cache.evict(livroId);
                logger.debug("Cache hasReserva invalidado para livro ID: {}", livroId);
            } else {
                logger.warn("Cache 'hasReservaCache' não encontrado para invalidar");
            }
        } catch (Exception e) {
            logger.error("Erro ao invalidar cache hasReserva para livro ID: {}", livroId, e);
        }
    }

    public void invalidarCacheLivros() {
        try {
            Cache cache = cacheManager.getCache("livros");
            if (cache != null) {
                cache.clear();
                logger.info("Cache de livros invalidado");
            } else {
                logger.warn("Cache 'livros' não encontrado para invalidar");
            }
        } catch (Exception e) {
            logger.error("Erro ao invalidar cache de livros", e);
        }
    }

    public void invalidarTodosCachesLivro(Integer livroId) {
        invalidarCacheHasReserva(livroId);
        invalidarCacheLivros();
    }
}

