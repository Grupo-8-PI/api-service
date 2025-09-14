package school.sptech.hub.application.usecases.acabamento;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoJaExisteException;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.domain.entity.Acabamento;

import java.util.Optional;

@Component
public class CreateAcabamentoUseCase {

    private final AcabamentoGateway acabamentoGateway;

    public CreateAcabamentoUseCase(AcabamentoGateway acabamentoGateway) {
        this.acabamentoGateway = acabamentoGateway;
    }

    public Acabamento execute(Acabamento acabamento) {
        // Validação usando regras de negócio da entidade de domínio
        acabamento.validateBusinessRules();

        // Verificar se acabamento com mesmo tipo já existe
        Optional<Acabamento> existingAcabamento = acabamentoGateway.findByTipoAcabamento(acabamento.getTipoAcabamento());
        if (existingAcabamento.isPresent()) {
            throw new AcabamentoJaExisteException("Já existe um acabamento com este tipo: " + acabamento.getTipoAcabamento());
        }

        return acabamentoGateway.createAcabamento(acabamento)
                .orElseThrow(() -> new AcabamentoNaoEncontradoException("Erro ao criar acabamento"));
    }
}
