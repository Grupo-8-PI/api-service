package school.sptech.hub.application.service;

import org.springframework.stereotype.Service;
import school.sptech.hub.application.usecases.livro.*;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroPaginatedResponseDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;

import java.util.List;

@Service
public class LivroService {

    private final CreateLivroUseCase createLivroUseCase;
    private final FindLivroByIdUseCase findLivroByIdUseCase;
    private final ListLivrosPaginatedUseCase listLivrosPaginatedUseCase;
    private final FindLivroWithSinopseUseCase findLivroWithSinopseUseCase;
    private final UpdateLivroUseCase updateLivroUseCase;
    private final DeleteLivroUseCase deleteLivroUseCase;
    private final UploadImageUseCase uploadImageUseCase;
    private final FindLivrosByAcabamentoUseCase findLivrosByAcabamentoUseCase;
    private final FindLivrosByConservacaoUseCase findLivrosByConservacaoUseCase;
    private final FindLivrosByCategoriaUseCase findLivrosByCategoriaUseCase;
    private final ListAllCategoriesUseCase listAllCategoriesUseCase;
    private final ListRecommendedLivrosUseCase listRecommendedLivrosUseCase;
    private final ListRecentLivrosUseCase listRecentLivrosUseCase;

    public LivroService(CreateLivroUseCase createLivroUseCase,
                        FindLivroByIdUseCase findLivroByIdUseCase,
                        ListLivrosPaginatedUseCase listLivrosPaginatedUseCase,
                        FindLivroWithSinopseUseCase findLivroWithSinopseUseCase,
                        UpdateLivroUseCase updateLivroUseCase,
                        DeleteLivroUseCase deleteLivroUseCase,
                        UploadImageUseCase uploadImageUseCase,
                        FindLivrosByAcabamentoUseCase findLivrosByAcabamentoUseCase,
                        FindLivrosByConservacaoUseCase findLivrosByConservacaoUseCase,
                        FindLivrosByCategoriaUseCase findLivrosByCategoriaUseCase,
                        ListAllCategoriesUseCase listAllCategoriesUseCase,
                        ListRecommendedLivrosUseCase listRecommendedLivrosUseCase,
                        ListRecentLivrosUseCase listRecentLivrosUseCase) {
        this.createLivroUseCase = createLivroUseCase;
        this.findLivroByIdUseCase = findLivroByIdUseCase;
        this.listLivrosPaginatedUseCase = listLivrosPaginatedUseCase;
        this.findLivroWithSinopseUseCase = findLivroWithSinopseUseCase;
        this.updateLivroUseCase = updateLivroUseCase;
        this.deleteLivroUseCase = deleteLivroUseCase;
        this.uploadImageUseCase = uploadImageUseCase;
        this.findLivrosByAcabamentoUseCase = findLivrosByAcabamentoUseCase;
        this.findLivrosByConservacaoUseCase = findLivrosByConservacaoUseCase;
        this.findLivrosByCategoriaUseCase = findLivrosByCategoriaUseCase;
        this.listAllCategoriesUseCase = listAllCategoriesUseCase;
        this.listRecommendedLivrosUseCase = listRecommendedLivrosUseCase;
        this.listRecentLivrosUseCase = listRecentLivrosUseCase;
    }

    public LivroResponseDto createNewLivro(LivroCreateDto livro) {
        return createLivroUseCase.execute(livro);
    }

    public LivroComSinopseResponseDto buscarLivroPorIdComSinopse(Integer id) {
        return findLivroWithSinopseUseCase.execute(id);
    }

    public LivroResponseDto buscarLivroPorId(Integer id) {
        return findLivroByIdUseCase.execute(id);
    }

    public LivroResponseDto atualizarLivro(Integer id, LivroUpdateDto livroUpdateDto) {
        return updateLivroUseCase.execute(id, livroUpdateDto);
    }

    public LivroResponseDto deletarLivro(Integer id) {
        return deleteLivroUseCase.execute(id);
    }

    public LivroResponseDto atualizarImagemLivro(Integer id, byte[] conteudoArquivo, String nomeArquivo, String contentType) {
        return uploadImageUseCase.execute(id, conteudoArquivo, nomeArquivo, contentType);
    }

    public List<LivroResponseDto> buscarLivrosPorAcabamento(Integer acabamentoId) {
        return findLivrosByAcabamentoUseCase.execute(acabamentoId);
    }

    public List<LivroResponseDto> buscarLivrosPorConservacao(Integer conservacaoId) {
        return findLivrosByConservacaoUseCase.execute(conservacaoId);
    }

    public List<LivroResponseDto> buscarLivrosPorCategoria(Integer categoriaId) {
        return findLivrosByCategoriaUseCase.execute(categoriaId);
    }

    public List<String> listarCategorias() {
        return listAllCategoriesUseCase.execute();
    }

    public List<LivroResponseDto> listarLivrosRecomendados() {
        return listRecommendedLivrosUseCase.execute();
    }

    public List<LivroResponseDto> listarLivrosRecentes() {
        return listRecentLivrosUseCase.execute();
    }

    public LivroPaginatedResponseDto listarLivrosPaginado(int page, int size) {
        return listLivrosPaginatedUseCase.execute(page, size);
    }
}
