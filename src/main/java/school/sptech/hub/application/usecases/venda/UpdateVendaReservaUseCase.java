package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.dto.venda.VendaUpdateDto;
import school.sptech.hub.domain.dto.venda.VendaMapper;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;

@Component
public class UpdateVendaReservaUseCase {
    private final VendaGateway gateway;

    public UpdateVendaReservaUseCase(VendaGateway gateway) {
        this.gateway = gateway;
    }

    public Venda execute(Integer id, VendaUpdateDto vendaUpdateDto) {
        Venda existingVenda = gateway.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada com ID: " + id));

        VendaMapper.updateFromDto(existingVenda, vendaUpdateDto);

        return gateway.updateVenda(existingVenda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao atualizar reserva"));
    }
}
