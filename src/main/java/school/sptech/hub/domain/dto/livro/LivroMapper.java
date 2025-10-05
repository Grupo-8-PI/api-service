package school.sptech.hub.domain.dto.livro;

import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;

import java.time.LocalDateTime;

public class LivroMapper {

    public static Livro toEntity(LivroCreateDto dto) {
        if (dto == null) return null;

        Livro livro = new Livro();
        livro.setIsbn(dto.getIsbn());
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setEditora(dto.getEditora());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPaginas(dto.getPaginas());

        // Criar acabamento a partir do ID
        if (dto.getAcabamentoId() != null) {
            livro.setAcabamento(new Acabamento(dto.getAcabamentoId()));
        }

        // Criar conservação a partir do ID
        if (dto.getConservacaoId() != null) {
            livro.setEstadoConservacao(new Conservacao(dto.getConservacaoId()));
        }

        // Capa será definida apenas via PATCH - não definir durante criação
        livro.setCapa(null);
        livro.setPreco(dto.getPreco());

        // Criar categoria temporária apenas com o nome para processamento posterior
        if (dto.getNomeCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setNome(dto.getNomeCategoria());
            livro.setCategoria(categoria);
        }

        // Definir dataAdicao: usar do DTO se fornecida, senão usar agora
        livro.setDataAdicao(dto.getDataAdicao() != null ? dto.getDataAdicao() : LocalDateTime.now());

        return livro;
    }

    // Método compatível com a assinatura anterior (para não quebrar outros códigos)
    public static Livro toEntity(LivroCreateDto dto, Acabamento acabamento, Conservacao conservacao, Categoria categoria) {
        return toEntity(dto);
    }

    public static LivroResponseDto toResponseDto(Livro livro) {
        if (livro == null) return null;

        LivroResponseDto dto = new LivroResponseDto();
        dto.setId(livro.getId());
        dto.setIsbn(livro.getIsbn());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setEditora(livro.getEditora());
        dto.setAnoPublicacao(livro.getAnoPublicacao() != null ? livro.getAnoPublicacao().getValue() : null);
        dto.setPaginas(livro.getPaginas());
        dto.setCapa(livro.getCapa());
        dto.setPreco(livro.getPreco());

        // Mapear acabamento
        if (livro.getAcabamento() != null) {
            dto.setAcabamentoId(livro.getAcabamento().getId());
            dto.setTipoAcabamento(livro.getAcabamento().getTipoAcabamento() != null
                ? livro.getAcabamento().getTipoAcabamento().name() : null);
        }

        // Mapear conservação
        if (livro.getEstadoConservacao() != null) {
            dto.setConservacaoId(livro.getEstadoConservacao().getId());
            dto.setEstadoConservacao(livro.getEstadoConservacao().getTipoConservacao() != null
                ? livro.getEstadoConservacao().getTipoConservacao().name() : null);
        }

        // Mapear categoria
        if (livro.getCategoria() != null) {
            dto.setCategoriaId(livro.getCategoria().getId());
            dto.setNomeCategoria(livro.getCategoria().getNome());
        }

        // Mapear dataAdicao
        dto.setDataAdicao(livro.getDataAdicao());

        return dto;
    }

    public static LivroComSinopseResponseDto toResponseDtoWithSinopse(Livro livro, String sinopse) {
        if (livro == null) return null;

        LivroComSinopseResponseDto dto = new LivroComSinopseResponseDto();

        // Copiar todos os campos do LivroResponseDto
        LivroResponseDto baseDto = toResponseDto(livro);
        dto.setId(baseDto.getId());
        dto.setIsbn(baseDto.getIsbn());
        dto.setTitulo(baseDto.getTitulo());
        dto.setAutor(baseDto.getAutor());
        dto.setEditora(baseDto.getEditora());
        dto.setAnoPublicacao(baseDto.getAnoPublicacao());
        dto.setPaginas(baseDto.getPaginas());
        dto.setAcabamentoId(baseDto.getAcabamentoId());
        dto.setTipoAcabamento(baseDto.getTipoAcabamento());
        dto.setConservacaoId(baseDto.getConservacaoId());
        dto.setEstadoConservacao(baseDto.getEstadoConservacao());
        dto.setCapa(baseDto.getCapa());
        dto.setPreco(baseDto.getPreco());
        dto.setCategoriaId(baseDto.getCategoriaId());
        dto.setNomeCategoria(baseDto.getNomeCategoria());
        dto.setDataAdicao(baseDto.getDataAdicao());

        // Adicionar sinopse
        dto.setSinopse(sinopse);

        return dto;
    }

    public static void updateEntityFromDto(Livro livro, LivroUpdateDto dto) {
        if (livro == null || dto == null) return;

        if (dto.getIsbn() != null) livro.setIsbn(dto.getIsbn());
        if (dto.getTitulo() != null) livro.setTitulo(dto.getTitulo());
        if (dto.getAutor() != null) livro.setAutor(dto.getAutor());
        if (dto.getEditora() != null) livro.setEditora(dto.getEditora());
        if (dto.getAnoPublicacao() != null) livro.setAnoPublicacao(dto.getAnoPublicacao());
        if (dto.getPaginas() != null) livro.setPaginas(dto.getPaginas());
        if (dto.getCapa() != null) livro.setCapa(dto.getCapa());
        if (dto.getPreco() != null) livro.setPreco(dto.getPreco());

        // Atualizar acabamento se fornecido
        if (dto.getAcabamentoId() != null) {
            livro.setAcabamento(new Acabamento(dto.getAcabamentoId()));
        }

        // Atualizar conservação se fornecida
        if (dto.getConservacaoId() != null) {
            livro.setEstadoConservacao(new Conservacao(dto.getConservacaoId()));
        }

        // Atualizar categoria se fornecida
        if (dto.getNomeCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setNome(dto.getNomeCategoria());
            livro.setCategoria(categoria);
        }

        // Atualizar dataAdicao se fornecida
        if (dto.getDataAdicao() != null) {
            livro.setDataAdicao(dto.getDataAdicao());
        }
    }
}
