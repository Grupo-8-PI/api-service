package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.venda.VendaGateway;

@Component
public class CheckVendaOwnershipUseCase {

    private final VendaGateway gateway;

    public CheckVendaOwnershipUseCase(VendaGateway gateway) {
        this.gateway = gateway;
    }

    public boolean execute(Integer idReserva, String emailUsuario) {
        return gateway.reservaPertenceAoUsuario(idReserva, emailUsuario);
    }
}
