package school.sptech.hub.application.usecases.venda;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.venda.VendaGateway;

@Component
public class CheckVendaOwnershipUseCase {

    private final VendaGateway gateway;

    public CheckVendaOwnershipUseCase(VendaGateway gateway) {
        this.gateway = gateway;
    }

    public boolean execute(Integer idReserva, String emailUsuario) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return true;
        }

        return gateway.reservaPertenceAoUsuario(idReserva, emailUsuario);
    }
}
