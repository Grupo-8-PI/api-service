package school.sptech.hub.application.usecases.venda;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.domain.entity.Livro;

import java.time.LocalDateTime;

import static school.sptech.hub.application.validators.VendaValidator.isValidVenda;

@Component
public class CreateVendaUseCase {

    private final VendaGateway vendaGateway;
    private final UsuarioGateway usuarioGateway;
    private final LivroGateway livroGateway;

    public CreateVendaUseCase(VendaGateway vendaGateway, UsuarioGateway usuarioGateway, LivroGateway livroGateway) {
        this.vendaGateway = vendaGateway;
        this.usuarioGateway = usuarioGateway;
        this.livroGateway = livroGateway;
    }

    public Venda execute(Venda venda) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName();

        Usuario usuario = usuarioGateway.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + emailUsuario));

        if (venda.getLivro() != null && venda.getLivro().getId() != null) {
            Livro livroExistente = livroGateway.findById(venda.getLivro().getId())
                    .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + venda.getLivro().getId()));

            venda.setLivro(livroExistente);

            if (livroExistente.getPreco() != null) {
                venda.setTotalReserva(livroExistente.getPreco().intValue());
            }
        }

        venda.setUsuarios(usuario);

        LocalDateTime dtReserva = LocalDateTime.now();
        venda.setDtReserva(dtReserva);
        venda.setDtLimite(dtReserva.plusHours(48));

        boolean isReservaValid = isValidVenda(venda);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        
        return vendaGateway.createVenda(venda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao criar reserva"));
    }
}
