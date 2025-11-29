package school.sptech.hub.domain.dto.livro;

import java.util.List;

public class LivroPaginatedResponseDto {
    private List<LivroResponseDto> livros;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    public LivroPaginatedResponseDto(List<LivroResponseDto> livros, int totalPages, long totalElements, int currentPage) {
        this.livros = livros;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }
    public LivroPaginatedResponseDto() {
    }

    public List<LivroResponseDto> getLivros() {
        return livros;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setLivros(List<LivroResponseDto> livros) {
        this.livros = livros;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

