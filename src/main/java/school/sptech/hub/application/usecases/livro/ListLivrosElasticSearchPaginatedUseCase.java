package school.sptech.hub.application.usecases.livro;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroSearchGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroPaginatedResponseDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListLivrosElasticSearchPaginatedUseCase {
    private final LivroSearchGateway gateway;

    public ListLivrosElasticSearchPaginatedUseCase(LivroSearchGateway gateway) {
        this.gateway = gateway;
    }

    public LivroPaginatedResponseDto execute(int page, int size, String query){
        Page<Livro> livrosPage = gateway.search(query, page, size);
        List<LivroResponseDto> livros = livrosPage.getContent().stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
        return new LivroPaginatedResponseDto(
                livros,
                livrosPage.getTotalPages(),
                livrosPage.getTotalElements(),
                livrosPage.getNumber()
        );
    }
    }

