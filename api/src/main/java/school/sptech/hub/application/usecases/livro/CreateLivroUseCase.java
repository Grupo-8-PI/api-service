package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroJaExisteException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

import java.util.Optional;

@Component
public class CreateLivroUseCase {

    private final LivroGateway livroGateway;

    public CreateLivroUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public LivroResponseDto execute(LivroCreateDto livroCreateDto) {
        // Converter DTO para entidade usando o mapper
        // As entidades (acabamento, conservacao, categoria) vêm completas no DTO
        Livro livroEntity = LivroMapper.toEntity(livroCreateDto);

        // Validação usando regras de negócio da entidade de domínio
        livroEntity.validateBusinessRules();

        // Verificar se livro com ISBN já existe
        Optional<Livro> existingLivro = livroGateway.findByIsbn(livroCreateDto.getIsbn());
        if (existingLivro.isPresent()) {
            throw new LivroJaExisteException("Já existe um livro cadastrado com este ISBN.");
        }

        // Criar o livro no banco de dados
        Livro createdLivro = livroGateway.createLivro(livroEntity)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao criar livro"));

        return LivroMapper.toResponseDto(createdLivro);
    }
}
