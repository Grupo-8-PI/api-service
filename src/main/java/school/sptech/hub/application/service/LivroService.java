package school.sptech.hub.application.service;

import org.springframework.stereotype.Service;
import school.sptech.hub.application.usecases.livro.*;
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;

import java.util.List;

@Service
public class LivroService {

    private final CreateLivroUseCase createLivroUseCase;
    private final FindLivroByIdUseCase findLivroByIdUseCase;
    private final ListAllLivrosUseCase listAllLivrosUseCase;
    private final FindLivroWithSinopseUseCase findLivroWithSinopseUseCase;
    private final UpdateLivroUseCase updateLivroUseCase;
    private final DeleteLivroUseCase deleteLivroUseCase;
    private final UploadImageUseCase uploadImageUseCase;
    private final FindLivrosByAcabamentoUseCase findLivrosByAcabamentoUseCase;
    private final FindLivrosByConservacaoUseCase findLivrosByConservacaoUseCase;
    private final FindLivrosByCategoriaUseCase findLivrosByCategoriaUseCase;

    public LivroService(CreateLivroUseCase createLivroUseCase,
                       FindLivroByIdUseCase findLivroByIdUseCase,
                       ListAllLivrosUseCase listAllLivrosUseCase,
                       FindLivroWithSinopseUseCase findLivroWithSinopseUseCase,
                       UpdateLivroUseCase updateLivroUseCase,
                       DeleteLivroUseCase deleteLivroUseCase,
                       UploadImageUseCase uploadImageUseCase,
                       FindLivrosByAcabamentoUseCase findLivrosByAcabamentoUseCase,
                       FindLivrosByConservacaoUseCase findLivrosByConservacaoUseCase,
                       FindLivrosByCategoriaUseCase findLivrosByCategoriaUseCase) {
        this.createLivroUseCase = createLivroUseCase;
        this.findLivroByIdUseCase = findLivroByIdUseCase;
        this.listAllLivrosUseCase = listAllLivrosUseCase;
        this.findLivroWithSinopseUseCase = findLivroWithSinopseUseCase;
        this.updateLivroUseCase = updateLivroUseCase;
        this.deleteLivroUseCase = deleteLivroUseCase;
        this.uploadImageUseCase = uploadImageUseCase;
        this.findLivrosByAcabamentoUseCase = findLivrosByAcabamentoUseCase;
        this.findLivrosByConservacaoUseCase = findLivrosByConservacaoUseCase;
        this.findLivrosByCategoriaUseCase = findLivrosByCategoriaUseCase;
    }

    public LivroResponseDto createNewLivro(LivroCreateDto livro) {
        return createLivroUseCase.execute(livro);
    }

    public List<LivroResponseDto> listarLivros() {
        return listAllLivrosUseCase.execute();
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
}
