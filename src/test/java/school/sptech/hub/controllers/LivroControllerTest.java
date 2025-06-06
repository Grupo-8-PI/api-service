package school.sptech.hub.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import school.sptech.hub.controller.LivroController;
import school.sptech.hub.controller.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.controller.dto.livro.LivroCreateDto;
import school.sptech.hub.controller.dto.livro.LivroResponseDto;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.service.LivroService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    private Livro livroMock;
    private LivroCreateDto livroCreateDto;
    private LivroResponseDto livroResponseDto;
    private LivroComSinopseResponseDto livroComSinopseResponseDto;

    @BeforeEach
    void setUp() {
        // Arrange - Setup comum para todos os testes
        livroMock = new Livro();
        livroMock.setId(1);
        livroMock.setTitulo("O Senhor dos Anéis");
        livroMock.setAutor("J.R.R. Tolkien");
        livroMock.setPreco(45.90);

        livroCreateDto = new LivroCreateDto();
        livroCreateDto.setTitulo("O Senhor dos Anéis");
        livroCreateDto.setAutor("J.R.R. Tolkien");
        livroCreateDto.setPreco(45.90);

        livroResponseDto = new LivroResponseDto();
        livroResponseDto.setId(1);
        livroResponseDto.setTitulo("O Senhor dos Anéis");
        livroResponseDto.setAutor("J.R.R. Tolkien");
        livroResponseDto.setPreco(45.90);

        livroComSinopseResponseDto = new LivroComSinopseResponseDto();
        livroComSinopseResponseDto.setId(1);
        livroComSinopseResponseDto.setTitulo("O Senhor dos Anéis");
        livroComSinopseResponseDto.setAutor("J.R.R. Tolkien");
        livroComSinopseResponseDto.setPreco(45.90);
        livroComSinopseResponseDto.setSinopse("Uma aventura épica na Terra Média");
    }

    @Test
    @DisplayName("Quando cadastrar livro com dados válidos deve retornar status 200 e livro criado")
    void when_cadastrar_livro_with_valid_data_should_return_200_and_created_book() {
        // Arrange
        when(livroService.createNewLivro(any(LivroCreateDto.class))).thenReturn(livroMock);

        // Act
        ResponseEntity<Livro> response = livroController.cadastrarLivro(livroCreateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("O Senhor dos Anéis", response.getBody().getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().getAutor());
        assertEquals(45.90, response.getBody().getPreco());
        verify(livroService, times(1)).createNewLivro(any(LivroCreateDto.class));
    }

    @Test
    @DisplayName("Quando listar livros deve retornar status 200 e lista de livros")
    void when_listar_livros_should_return_200_and_books_list() {
        // Arrange
        List<LivroResponseDto> livrosList = Arrays.asList(livroResponseDto);
        when(livroService.listarLivros()).thenReturn(livrosList);

        // Act
        ResponseEntity<List<LivroResponseDto>> response = livroController.listarLivros();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getId());
        assertEquals("O Senhor dos Anéis", response.getBody().get(0).getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().get(0).getAutor());
        assertEquals(45.90, response.getBody().get(0).getPreco());
        verify(livroService, times(1)).listarLivros();
    }

    @Test
    @DisplayName("Quando listar livros com lista vazia deve retornar status 200 e lista vazia")
    void when_listar_livros_with_empty_list_should_return_200_and_empty_list() {
        // Arrange
        List<LivroResponseDto> livrosVazia = Collections.emptyList();
        when(livroService.listarLivros()).thenReturn(livrosVazia);

        // Act
        ResponseEntity<List<LivroResponseDto>> response = livroController.listarLivros();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(livroService, times(1)).listarLivros();
    }

    @Test
    @DisplayName("Quando buscar livro por ID válido deve retornar status 200 e livro encontrado")
    void when_buscar_livro_por_id_with_valid_id_should_return_200_and_book() {
        // Arrange
        Integer idValido = 1;
        when(livroService.buscarLivroPorId(idValido)).thenReturn(livroResponseDto);

        // Act
        ResponseEntity<LivroResponseDto> response = livroController.buscarLivroPorId(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("O Senhor dos Anéis", response.getBody().getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().getAutor());
        assertEquals(45.90, response.getBody().getPreco());
        verify(livroService, times(1)).buscarLivroPorId(idValido);
    }

    @Test
    @DisplayName("Quando buscar livro por ID com sinopse deve retornar status 200 e livro com sinopse")
    void when_buscar_livro_por_id_com_sinopse_should_return_200_and_book_with_synopsis() {
        // Arrange
        Integer idValido = 1;
        when(livroService.buscarLivroPorIdComSinopse(idValido)).thenReturn(livroComSinopseResponseDto);

        // Act
        ResponseEntity<LivroComSinopseResponseDto> response = livroController.buscarLivroPorIdComSinopse(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("O Senhor dos Anéis", response.getBody().getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().getAutor());
        assertEquals(45.90, response.getBody().getPreco());
        assertEquals("Uma aventura épica na Terra Média", response.getBody().getSinopse());
        verify(livroService, times(1)).buscarLivroPorIdComSinopse(idValido);
    }

    @Test
    @DisplayName("Quando atualizar livro com dados válidos deve retornar status 200 e livro atualizado")
    void when_atualizar_livro_with_valid_data_should_return_200_and_updated_book() {
        // Arrange
        Integer idValido = 1;
        Livro livroParaAtualizar = new Livro();
        livroParaAtualizar.setTitulo("O Senhor dos Anéis - Edição Especial");
        livroParaAtualizar.setAutor("J.R.R. Tolkien");
        livroParaAtualizar.setPreco(55.90);

        Livro livroAtualizado = new Livro();
        livroAtualizado.setId(1);
        livroAtualizado.setTitulo("O Senhor dos Anéis - Edição Especial");
        livroAtualizado.setAutor("J.R.R. Tolkien");
        livroAtualizado.setPreco(55.90);

        when(livroService.atualizarLivro(eq(idValido), any(Livro.class))).thenReturn(livroAtualizado);

        // Act
        ResponseEntity<Livro> response = livroController.atualizarLivro(idValido, livroParaAtualizar);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("O Senhor dos Anéis - Edição Especial", response.getBody().getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().getAutor());
        assertEquals(55.90, response.getBody().getPreco());
        verify(livroService, times(1)).atualizarLivro(eq(idValido), any(Livro.class));
    }

    @Test
    @DisplayName("Quando deletar livro com ID válido deve retornar status 200")
    void when_deletar_livro_with_valid_id_should_return_200() {
        // Arrange
        Integer idValido = 1;
        when(livroService.deletarLivro(idValido)).thenReturn(livroMock);

        // Act
        ResponseEntity<Void> response = livroController.deletarLivro(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(livroService, times(1)).deletarLivro(idValido);
    }

    @Test
    @DisplayName("Quando buscar livro por ID zero deve chamar o serviço")
    void when_buscar_livro_por_id_with_zero_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(livroService.buscarLivroPorId(idZero)).thenReturn(livroResponseDto);

        // Act
        ResponseEntity<LivroResponseDto> response = livroController.buscarLivroPorId(idZero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(livroService, times(1)).buscarLivroPorId(idZero);
    }

    @Test
    @DisplayName("Quando buscar livro por ID negativo deve chamar o serviço")
    void when_buscar_livro_por_id_with_negative_id_should_call_service() {
        // Arrange
        Integer idNegativo = -1;
        when(livroService.buscarLivroPorId(idNegativo)).thenReturn(livroResponseDto);

        // Act
        ResponseEntity<LivroResponseDto> response = livroController.buscarLivroPorId(idNegativo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(livroService, times(1)).buscarLivroPorId(idNegativo);
    }

    @Test
    @DisplayName("Quando atualizar livro com ID zero deve chamar o serviço")
    void when_atualizar_livro_with_zero_id_should_call_service() {
        // Arrange
        Integer idZero = 0;
        Livro livroParaAtualizar = new Livro();
        when(livroService.atualizarLivro(eq(idZero), any(Livro.class))).thenReturn(livroMock);

        // Act
        ResponseEntity<Livro> response = livroController.atualizarLivro(idZero, livroParaAtualizar);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(livroService, times(1)).atualizarLivro(eq(idZero), any(Livro.class));
    }

    @Test
    @DisplayName("Quando deletar livro com ID zero deve chamar o serviço")
    void when_deletar_livro_with_zero_id_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(livroService.deletarLivro(idZero)).thenReturn(livroMock);

        // Act
        ResponseEntity<Void> response = livroController.deletarLivro(idZero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(livroService, times(1)).deletarLivro(idZero);
    }

    @Test
    @DisplayName("Quando cadastrar livro deve verificar se objeto correto foi passado para o serviço")
    void when_cadastrar_livro_should_verify_correct_object_passed_to_service() {
        // Arrange
        when(livroService.createNewLivro(any(LivroCreateDto.class))).thenReturn(livroMock);

        // Act
        livroController.cadastrarLivro(livroCreateDto);

        // Assert
        verify(livroService, times(1)).createNewLivro(livroCreateDto);
        verifyNoMoreInteractions(livroService);
    }

    @Test
    @DisplayName("Quando listar livros deve verificar se não há interações adicionais com o serviço")
    void when_listar_livros_should_verify_no_additional_service_interactions() {
        // Arrange
        List<LivroResponseDto> livrosList = Arrays.asList(livroResponseDto);
        when(livroService.listarLivros()).thenReturn(livrosList);

        // Act
        livroController.listarLivros();

        // Assert
        verify(livroService, times(1)).listarLivros();
        verifyNoMoreInteractions(livroService);
    }
}