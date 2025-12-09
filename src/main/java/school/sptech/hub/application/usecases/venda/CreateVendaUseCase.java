package school.sptech.hub.application.usecases.venda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import school.sptech.hub.utils.cache.CacheInvalidationHelper;

import java.time.LocalDateTime;

import static school.sptech.hub.application.validators.VendaValidator.isValidVenda;

@Component
public class CreateVendaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateVendaUseCase.class);

    private final VendaGateway vendaGateway;
    private final UsuarioGateway usuarioGateway;
    private final LivroGateway livroGateway;
    private final CacheInvalidationHelper cacheInvalidationHelper;

    public CreateVendaUseCase(VendaGateway vendaGateway, UsuarioGateway usuarioGateway, LivroGateway livroGateway, CacheInvalidationHelper cacheInvalidationHelper) {
        this.vendaGateway = vendaGateway;
        this.usuarioGateway = usuarioGateway;
        this.livroGateway = livroGateway;
        this.cacheInvalidationHelper = cacheInvalidationHelper;
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
        
        Venda vendaCriada = vendaGateway.createVenda(venda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao criar reserva"));

        if (venda.getLivro() != null) {
            cacheInvalidationHelper.invalidarTodosCachesLivro(venda.getLivro().getId());
        }

        return vendaCriada;
    }
}
