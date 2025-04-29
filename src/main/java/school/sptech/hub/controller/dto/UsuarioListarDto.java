package school.sptech.hub.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioListarDto {

    @Schema(description = "Id do Usuário", example = "1")
    private Long id;

    @Schema(description = "Nome do Usuário", example = "Joao Silva")
    private String nome;

    @Schema(description = "E-mail do Usuário", example = "xxxx@xxx.com")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
