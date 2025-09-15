package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.exceptions.ConservacaoExceptions.ConservacaoNaoEncontradaException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;

@Component
@Transactional
public class UpdateLivroUseCase {

    private final LivroGateway livroGateway;
    private final CategoriaGateway categoriaGateway;

    public UpdateLivroUseCase(LivroGateway livroGateway, CategoriaGateway categoriaGateway) {
        this.livroGateway = livroGateway;
        this.categoriaGateway = categoriaGateway;
    }

    public LivroResponseDto execute(Integer id, LivroUpdateDto livroUpdateDto) {
        Livro existingLivro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        // Buscar entidades relacionadas pelos IDs apenas se fornecidos
        Acabamento acabamento = null;
        if (livroUpdateDto.getAcabamentoId() != null) {
            // Para acabamento, criamos diretamente pois os IDs são fixos (1-CAPA DURA, 2-BROCHURA)
            try {
                acabamento = new Acabamento(livroUpdateDto.getAcabamentoId());
            } catch (IllegalArgumentException e) {
                throw new AcabamentoNaoEncontradoException("ID de acabamento inválido: " + livroUpdateDto.getAcabamentoId() + ". IDs válidos: 1-CAPA DURA, 2-BROCHURA");
            }
        }

        Categoria categoria = null;
        if (livroUpdateDto.getCategoriaId() != null) {
            categoria = categoriaGateway.findById(livroUpdateDto.getCategoriaId())
                    .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + livroUpdateDto.getCategoriaId()));
        }

        Conservacao conservacao = null;
        if (livroUpdateDto.getConservacaoId() != null) {
            // Para conservação, criamos diretamente pois os IDs são fixos (1-4)
            try {
                conservacao = new Conservacao(livroUpdateDto.getConservacaoId());
            } catch (IllegalArgumentException e) {
                throw new ConservacaoNaoEncontradaException("ID de conservação inválido: " + livroUpdateDto.getConservacaoId() + ". IDs válidos: 1-EXCELENTE, 2-BOM, 3-RAZOÁVEL, 4-DEGRADADO");
            }
        }

        // Converter DTO para entidade usando o mapper atualizado
        Livro livroToUpdate = LivroMapper.fromUpdateDto(livroUpdateDto, acabamento, conservacao, categoria);

        // Validação usando regras de negócio da entidade de domínio
        livroToUpdate.validateUpdateRules();

        // Aplicar atualizações ao livro existente
        Livro updatedLivro = LivroMapper.updateLivroFields(existingLivro, livroToUpdate);

        // Salvar no repositório
        Livro savedLivro = livroGateway.updateLivro(updatedLivro)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao atualizar livro"));

        return LivroMapper.toResponseDto(savedLivro);
    }
}
