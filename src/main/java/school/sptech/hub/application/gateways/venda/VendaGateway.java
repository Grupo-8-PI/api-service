package school.sptech.hub.application.gateways.venda;

import school.sptech.hub.domain.entity.Venda;

import java.util.List;
import java.util.Optional;

public interface VendaGateway {
    Optional<Venda> createVenda(Venda venda);
    Optional<Venda> findById(Integer id);
    Optional<Venda> updateVenda(Venda venda);
    void deleteVenda(Venda venda);
    boolean reservaPertenceAoUsuario(Integer idReserva, String emailUsuario);
    List<Venda> findVendasByClienteId(Integer clienteId);
}
