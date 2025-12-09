package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.utils.cache.CacheInvalidationHelper;

@Component
public class DeleteVendaUseCase {

    private final VendaGateway gateway;
    private final CacheInvalidationHelper cacheInvalidationHelper;

    public DeleteVendaUseCase(VendaGateway gateway, CacheInvalidationHelper cacheInvalidationHelper) {
        this.gateway = gateway;
        this.cacheInvalidationHelper = cacheInvalidationHelper;
    }

    public Venda execute(Integer id) {
        Venda vendaFinded = gateway.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));
        
        gateway.deleteVenda(vendaFinded);

        if (vendaFinded.getLivro() != null) {
            cacheInvalidationHelper.invalidarTodosCachesLivro(vendaFinded.getLivro().getId());
        }

        return vendaFinded;
    }
}
