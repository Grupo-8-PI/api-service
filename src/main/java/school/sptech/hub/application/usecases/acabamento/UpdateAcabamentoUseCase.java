package school.sptech.hub.application.usecases.acabamento;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.domain.entity.Acabamento;

@Component
public class UpdateAcabamentoUseCase {

    private final AcabamentoGateway acabamentoGateway;

    public UpdateAcabamentoUseCase(AcabamentoGateway acabamentoGateway) {
        this.acabamentoGateway = acabamentoGateway;
    }

    public Acabamento execute(Integer id, Acabamento acabamentoToUpdate) {
        Acabamento existingAcabamento = acabamentoGateway.findById(id)
                .orElseThrow(() -> new AcabamentoNaoEncontradoException("Acabamento não encontrado com ID: " + id));

        // Validação usando regras de negócio da entidade de domínio
        acabamentoToUpdate.validateUpdateRules();

        // Atualizar campos se fornecidos
        if (acabamentoToUpdate.getTipoAcabamento() != null) {
            existingAcabamento.setTipoAcabamento(acabamentoToUpdate.getTipoAcabamento());
        }

        return acabamentoGateway.updateAcabamento(existingAcabamento)
                .orElseThrow(() -> new AcabamentoNaoEncontradoException("Erro ao atualizar acabamento"));
    }
}
