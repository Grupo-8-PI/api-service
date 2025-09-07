package school.sptech.hub.application.usecases.venda;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.entity.Usuario;

import static school.sptech.hub.application.validators.VendaValidator.isValidVenda;

@Component
public class CreateVendaUseCase {

    private final VendaGateway vendaGateway;
    private final UsuarioGateway usuarioGateway;

    public CreateVendaUseCase(VendaGateway vendaGateway, UsuarioGateway usuarioGateway) {
        this.vendaGateway = vendaGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public Venda execute(Venda venda) {
        // Obter o usuário autenticado do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName();

        // Buscar o usuário no banco de dados
        Usuario usuario = usuarioGateway.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + emailUsuario));

        // Associar o usuário à venda
        venda.setUsuarios(usuario);

        // Validar a venda
        boolean isReservaValid = isValidVenda(venda);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        
        return vendaGateway.createVenda(venda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao criar reserva"));
    }
}
