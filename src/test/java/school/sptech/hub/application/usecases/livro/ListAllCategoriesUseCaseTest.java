
package school.sptech.hub.application.usecases.livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.domain.dto.categoria.CategoriaDto;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaEntity;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListAllCategoriesUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ListAllCategoriesUseCase useCase;

    private List<CategoriaEntity> categoriaEntities;

    @BeforeEach
    void setUp() {
        categoriaEntities = Arrays.asList(
                new CategoriaEntity(1, "Culinária"),
                new CategoriaEntity(2, "Distopia"),
                new CategoriaEntity(3, "Ficção Cientifica"),
                new CategoriaEntity(4, "Filosofia")
        );
    }

    @Test
    @DisplayName("Deve retornar lista de CategoriaDto com id e nome")
    void deveRetornarListaDeCategoriaDto() {
        when(categoriaRepository.findAll()).thenReturn(categoriaEntities);

        List<CategoriaDto> result = useCase.execute();

        assertNotNull(result);
        assertEquals(4, result.size());
        
        assertEquals(1, result.get(0).getId());
        assertEquals("Culinária", result.get(0).getNome());
        
        assertEquals(2, result.get(1).getId());
        assertEquals("Distopia", result.get(1).getNome());
        
        assertEquals(3, result.get(2).getId());
        assertEquals("Ficção Cientifica", result.get(2).getNome());
        
        assertEquals(4, result.get(3).getId());
        assertEquals("Filosofia", result.get(3).getNome());

        verify(categoriaRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há categorias")
    void deveRetornarListaVaziaQuandoNaoHaCategorias() {
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList());

        List<CategoriaDto> result = useCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoriaRepository).findAll();
    }

    @Test
    @DisplayName("Deve mapear corretamente múltiplas categorias")
    void deveMappearCorretamenteMultiplasCategorias() {
        when(categoriaRepository.findAll()).thenReturn(categoriaEntities);

        List<CategoriaDto> result = useCase.execute();

        result.forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getNome());
            assertTrue(dto.getId() > 0);
            assertFalse(dto.getNome().isEmpty());
        });
    }
}

