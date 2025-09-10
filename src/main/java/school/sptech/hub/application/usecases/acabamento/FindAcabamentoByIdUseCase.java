package school.sptech.hub.application.usecases.acabamento;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.domain.entity.Acabamento;

@Component
public class FindAcabamentoByIdUseCase {

    private final AcabamentoGateway acabamentoGateway;

    public FindAcabamentoByIdUseCase(AcabamentoGateway acabamentoGateway) {
        this.acabamentoGateway = acabamentoGateway;
    }

    public Acabamento execute(Integer id) {
        return acabamentoGateway.findById(id)
                .orElseThrow(() -> new AcabamentoNaoEncontradoException("Acabamento não encontrado com ID: " + id));
    }
}
