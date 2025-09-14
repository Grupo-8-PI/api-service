package school.sptech.hub.domain.dto.livro;

import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;

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

        livro.setCapa(dto.getCapa());
        livro.setPreco(dto.getPreco());
        livro.setCategoria(dto.getCategoria());
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
        // Tratamento seguro para Year
        dto.setAnoPublicacao(livro.getAnoPublicacao() != null ? livro.getAnoPublicacao().getValue() : null);
        dto.setPaginas(livro.getPaginas());
        dto.setCapa(livro.getCapa());
        dto.setPreco(livro.getPreco());

        // Mapear dados do acabamento
        if (livro.getAcabamento() != null) {
            dto.setAcabamentoId(livro.getAcabamento().getId());
            dto.setTipoAcabamento(livro.getAcabamento().getTipoAcabamentoStr());
        }

        // Mapear dados da conservação
        if (livro.getEstadoConservacao() != null) {
            dto.setConservacaoId(livro.getEstadoConservacao().getId());
            dto.setEstadoConservacao(livro.getEstadoConservacao().getEstadoConservacao());
        }

        // Mapear dados da categoria
        if (livro.getCategoria() != null) {
            dto.setCategoriaId(livro.getCategoria().getId());
            dto.setNomeCategoria(livro.getCategoria().getNome());
        }

        return dto;
    }

    public static LivroComSinopseResponseDto toComSinopseResponseDto(Livro livro, String sinopse) {
        if (livro == null) return null;

        LivroComSinopseResponseDto dto = new LivroComSinopseResponseDto();

        // Usar o método base para preencher todos os campos herdados
        LivroResponseDto baseDto = toResponseDto(livro);

        // Copiar todos os campos do DTO base
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

        // Adicionar o campo específico
        dto.setSinopse(sinopse);

        return dto;
    }

    // Método melhorado para update com DTO específico
    public static Livro fromUpdateDto(LivroUpdateDto dto, Acabamento acabamento, Conservacao conservacao, Categoria categoria) {
        if (dto == null) return null;

        Livro livro = new Livro();
        livro.setIsbn(dto.getIsbn());
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setEditora(dto.getEditora());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPaginas(dto.getPaginas());
        livro.setAcabamento(acabamento);

        // Criar conservação a partir do ID se fornecido
        if (dto.getConservacaoId() != null) {
            livro.setEstadoConservacao(new Conservacao(dto.getConservacaoId()));
        } else {
            livro.setEstadoConservacao(conservacao);
        }

        livro.setCapa(dto.getCapa());
        livro.setPreco(dto.getPreco());
        livro.setCategoria(categoria);
        return livro;
    }

    public static Livro updateLivroFields(Livro existingLivro, Livro updatedLivro) {
        if (existingLivro == null || updatedLivro == null) return existingLivro;

        // Update apenas campos não nulos (partial update)
        if (updatedLivro.getIsbn() != null) {
            existingLivro.setIsbn(updatedLivro.getIsbn());
        }
        if (updatedLivro.getTitulo() != null) {
            existingLivro.setTitulo(updatedLivro.getTitulo());
        }
        if (updatedLivro.getAutor() != null) {
            existingLivro.setAutor(updatedLivro.getAutor());
        }
        if (updatedLivro.getEditora() != null) {
            existingLivro.setEditora(updatedLivro.getEditora());
        }
        if (updatedLivro.getAnoPublicacao() != null) {
            existingLivro.setAnoPublicacao(updatedLivro.getAnoPublicacao());
        }
        if (updatedLivro.getPaginas() != null) {
            existingLivro.setPaginas(updatedLivro.getPaginas());
        }
        if (updatedLivro.getAcabamento() != null) {
            existingLivro.setAcabamento(updatedLivro.getAcabamento());
        }
        if (updatedLivro.getEstadoConservacao() != null) {
            existingLivro.setEstadoConservacao(updatedLivro.getEstadoConservacao());
        }
        if (updatedLivro.getCapa() != null) {
            existingLivro.setCapa(updatedLivro.getCapa());
        }
        if (updatedLivro.getPreco() != null) {
            existingLivro.setPreco(updatedLivro.getPreco());
        }
        if (updatedLivro.getCategoria() != null) {
            existingLivro.setCategoria(updatedLivro.getCategoria());
        }
        return existingLivro;
    }
}
