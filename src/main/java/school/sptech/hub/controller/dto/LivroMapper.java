package school.sptech.hub.controller.dto;

import school.sptech.hub.entity.Livro;

public class LivroMapper {
    public static Livro toEntity(LivroCreateDto dto) {
        Livro livro = new Livro();
        livro.setIsbn(dto.getIsbn());
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setEditora(dto.getEditora());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPaginas(dto.getPaginas());
        livro.setAcabamento(dto.getAcabamento());
        livro.setEstadoConservacao(dto.getEstadoConservacao());
        livro.setCapa(dto.getCapa());
        livro.setPreco(dto.getPreco());
        livro.setCategoria(dto.getCategoria());
        return livro;
    }

    public static LivroResponseDto toResponseDto(Livro livro) {
        LivroResponseDto dto = new LivroResponseDto();
        dto.setIsbn(livro.getIsbn());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setEditora(livro.getEditora());
        dto.setAnoPublicacao(livro.getAnoPublicacao().getValue());
        dto.setPaginas(livro.getPaginas());
        dto.setAcabamento(livro.getAcabamento());
        dto.setEstadoConservacao(livro.getEstadoConservacao());
        dto.setCapa(livro.getCapa());
        dto.setPreco(livro.getPreco());
        dto.setCategoria(livro.getCategoria());
        return dto;
    }

    public static Livro updateLivroFields(Livro existingLivro, Livro updatedLivro) {
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
