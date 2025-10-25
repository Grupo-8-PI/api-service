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

        if (dto.getAcabamentoId() != null) {
            livro.setAcabamento(new Acabamento(dto.getAcabamentoId()));
        }

        if (dto.getConservacaoId() != null) {
            livro.setEstadoConservacao(new Conservacao(dto.getConservacaoId()));
        }

        livro.setCapa(null);
        livro.setPreco(dto.getPreco());

        if (dto.getNomeCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setNome(dto.getNomeCategoria());
            livro.setCategoria(categoria);
        }

        livro.setDataAdicao(dto.getDataAdicao() != null ? dto.getDataAdicao() : LocalDateTime.now());

        return livro;
    }

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

        if (livro.getAcabamento() != null) {
            dto.setAcabamentoId(livro.getAcabamento().getId());
            dto.setTipoAcabamento(livro.getAcabamento().getTipoAcabamento() != null
                ? livro.getAcabamento().getTipoAcabamento().name() : null);
        }

        if (livro.getEstadoConservacao() != null) {
            dto.setConservacaoId(livro.getEstadoConservacao().getId());
            dto.setEstadoConservacao(livro.getEstadoConservacao().getTipoConservacao() != null
                ? livro.getEstadoConservacao().getTipoConservacao().name() : null);
        }

        if (livro.getCategoria() != null) {
            dto.setCategoriaId(livro.getCategoria().getId());
            dto.setNomeCategoria(livro.getCategoria().getNome());
        }

        dto.setDescricao(livro.getDescricao());
        dto.setDataAdicao(livro.getDataAdicao());

        return dto;
    }

    public static LivroComSinopseResponseDto toResponseDtoWithSinopse(Livro livro, String sinopse) {
        if (livro == null) return null;

        LivroComSinopseResponseDto dto = new LivroComSinopseResponseDto();

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

        dto.setSinopse(sinopse);

        return dto;
    }

    public static Livro fromUpdateDto(LivroUpdateDto dto, Acabamento acabamento, Conservacao conservacao, Categoria categoria) {
        if (dto == null) return null;

        Livro livro = new Livro();
        if (dto.getIsbn() != null) livro.setIsbn(dto.getIsbn());
        if (dto.getTitulo() != null) livro.setTitulo(dto.getTitulo());
        if (dto.getAutor() != null) livro.setAutor(dto.getAutor());
        if (dto.getEditora() != null) livro.setEditora(dto.getEditora());
        if (dto.getAnoPublicacao() != null) livro.setAnoPublicacao(dto.getAnoPublicacao());
        if (dto.getPaginas() != null) livro.setPaginas(dto.getPaginas());
        if (dto.getCapa() != null) livro.setCapa(dto.getCapa());
        if (dto.getPreco() != null) livro.setPreco(dto.getPreco());
        if (dto.getDescricao() != null) livro.setDescricao(dto.getDescricao());

        if (acabamento != null) livro.setAcabamento(acabamento);
        if (conservacao != null) livro.setEstadoConservacao(conservacao);
        if (categoria != null) livro.setCategoria(categoria);

        return livro;
    }

    public static void updateEntityFromDto(Livro existingLivro, LivroUpdateDto dto) {
        if (existingLivro == null || dto == null) return;

        if (dto.getIsbn() != null) {
            existingLivro.setIsbn(dto.getIsbn());
        }
        if (dto.getTitulo() != null) {
            existingLivro.setTitulo(dto.getTitulo());
        }
        if (dto.getAutor() != null) {
            existingLivro.setAutor(dto.getAutor());
        }
        if (dto.getEditora() != null) {
            existingLivro.setEditora(dto.getEditora());
        }
        if (dto.getAnoPublicacao() != null) {
            existingLivro.setAnoPublicacao(dto.getAnoPublicacao());
        }
        if (dto.getPaginas() != null) {
            existingLivro.setPaginas(dto.getPaginas());
        }
        if (dto.getCapa() != null) {
            existingLivro.setCapa(dto.getCapa());
        }
        if (dto.getPreco() != null) {
            existingLivro.setPreco(dto.getPreco());
        }
        if (dto.getDescricao() != null) {
            existingLivro.setDescricao(dto.getDescricao());
        }

        if (dto.getAcabamentoId() != null) {
            existingLivro.setAcabamento(new Acabamento(dto.getAcabamentoId()));
        }

        if (dto.getConservacaoId() != null) {
            existingLivro.setEstadoConservacao(new Conservacao(dto.getConservacaoId()));
        }

        if (dto.getNomeCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setNome(dto.getNomeCategoria());
            existingLivro.setCategoria(categoria);
        }
    }
}

