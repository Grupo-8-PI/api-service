package school.sptech.hub.application.usecases.livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.infraestructure.persistance.livroPersistance.LivroEntity;
import school.sptech.hub.infraestructure.persistance.livroPersistance.LivroRepository;

@Component
public class ListPaginatedLivrosUseCase {

    private final LivroRepository livroRepository;

    public ListPaginatedLivrosUseCase(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Page<LivroResponseDto> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LivroEntity> livrosPage = livroRepository.findAllWithAssociations(pageable);

        // Converte as entidades paginadas em DTOs paginados
        return livrosPage.map(this::toResponseDto);
    }

    private LivroResponseDto toResponseDto(LivroEntity entity) {
        LivroResponseDto dto = new LivroResponseDto();

        dto.setId(entity.getId());
        dto.setIsbn(entity.getIsbn() != null ? entity.getIsbn() : "");
        dto.setTitulo(entity.getTitulo() != null ? entity.getTitulo() : "");
        dto.setAutor(entity.getAutor() != null ? entity.getAutor() : "");
        dto.setEditora(entity.getEditora() != null ? entity.getEditora() : "");
        dto.setAnoPublicacao(entity.getAnoPublicacao() != null ? entity.getAnoPublicacao().getValue() : null);
        dto.setPaginas(entity.getPaginas() != null ? entity.getPaginas() : 0);
        dto.setCapa(entity.getCapa() != null ? entity.getCapa() : "");
        dto.setPreco(entity.getPreco() != null ? entity.getPreco() : 0.0);

        // Categoria
        var categoria = entity.getCategoria();
        if (categoria != null) {
            dto.setCategoriaId(categoria.getId());
            dto.setNomeCategoria(categoria.getNomeCategoria() != null ? categoria.getNomeCategoria() : "");
        } else {
            dto.setCategoriaId(null);
            dto.setNomeCategoria("");
        }

        // Conservação
        var conservacao = entity.getEstadoConservacao();
        if (conservacao != null) {
            dto.setConservacaoId(conservacao.getId());
            dto.setEstadoConservacao(conservacao.getEstadoConservacao() != null ? conservacao.getEstadoConservacao() : "");
        } else {
            dto.setConservacaoId(null);
            dto.setEstadoConservacao("");
        }

        // Acabamento
        var acabamento = entity.getAcabamento();
        if (acabamento != null) {
            dto.setAcabamentoId(acabamento.getId());
            dto.setTipoAcabamento(acabamento.getTipoAcabamentoStr() != null ? acabamento.getTipoAcabamentoStr() : "");
        } else {
            dto.setAcabamentoId(null);
            dto.setTipoAcabamento("");
        }

        return dto;
    }
}
