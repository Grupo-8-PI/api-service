package school.sptech.hub.application.usecases.acabamento;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.domain.entity.Acabamento;

import java.util.List;

@Component
public class ListAllAcabamentosUseCase {

    private final AcabamentoGateway acabamentoGateway;

    public ListAllAcabamentosUseCase(AcabamentoGateway acabamentoGateway) {
        this.acabamentoGateway = acabamentoGateway;
    }

    public List<Acabamento> execute() {
        return acabamentoGateway.findAll();
    }
}
