package school.sptech.hub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.controller.dto.UsuarioErroResponseSwgDto;
import school.sptech.hub.controller.dto.UsuarioCreateDto;
import school.sptech.hub.controller.dto.UsuarioResponseDto;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.service.UsuarioService;

@Tag(name = "usuarios", description = "Operações relacionadas a usuários")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;


    @Operation(
            summary = "Criar novo usuário",
            description = "Cria um novo usuário com os dados enviados no corpo da requisição. Os dados são validados automaticamente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos - erro de validação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioErroResponseSwgDto.class))
            )
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioCreateDto usuario) {
        UsuarioResponseDto createdUser = service.createUser(usuario);


        return ResponseEntity.status(201).body(createdUser);
    }


    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados do usuário correspondente ao ID informado, caso exista."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioErroResponseSwgDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Integer id) {
        UsuarioResponseDto usuario = service.getUserById(id);

        return ResponseEntity.status(200).body(usuario);
    }


    @Operation(
            summary = "Atualizar usuário por ID",
            description = "Atualiza os dados do usuário com base no ID informado. Todos os campos são validados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioErroResponseSwgDto.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUserById(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        Usuario updateUser = service.updateUserById(id, usuario);

        return ResponseEntity.status(200).body(updateUser);
    }


    @Operation(
            summary = "Deletar usuário por ID",
            description = "Remove o usuário correspondente ao ID informado do sistema, caso exista."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário deletado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioErroResponseSwgDto.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUserById(@PathVariable Integer id){
        Usuario deletedUser = service.deleteUserById(id);

        return ResponseEntity.status(200).body(deletedUser);
    }
}
