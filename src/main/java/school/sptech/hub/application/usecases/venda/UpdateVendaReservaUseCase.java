package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.dto.venda.VendaUpdateDto;
import school.sptech.hub.domain.dto.venda.VendaMapper;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.utils.cache.CacheInvalidationHelper;

@Component
public class UpdateVendaReservaUseCase {
    private final VendaGateway gateway;
    private final CacheInvalidationHelper cacheInvalidationHelper;

    public UpdateVendaReservaUseCase(VendaGateway gateway, CacheInvalidationHelper cacheInvalidationHelper) {
        this.gateway = gateway;
        this.cacheInvalidationHelper = cacheInvalidationHelper;
    }

    public Venda execute(Integer id, VendaUpdateDto vendaUpdateDto) {
        Venda existingVenda = gateway.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada com ID: " + id));

        VendaMapper.updateFromDto(existingVenda, vendaUpdateDto);

        Venda updatedVenda = gateway.updateVenda(existingVenda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao atualizar reserva"));

        if (updatedVenda.getLivro() != null) {
            cacheInvalidationHelper.invalidarTodosCachesLivro(updatedVenda.getLivro().getId());
        }

        return updatedVenda;
    }
}
