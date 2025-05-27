package school.sptech.hub.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "DTO utilizado para criação de um novo usuário")
public class UsuarioCreateDto {

    @Schema(description = "Nome completo do usuário", example = "Ana Lúcia")
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 45, message = "O nome deve ter no máximo 45 caracteres")
    private String nome;

    @Schema(description = "Email válido do usuário", example = "analu.pereira@email.com")
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @Schema(description = "Telefone com DDD", example = "11912345678")
    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 14, message = "O telefone deve ter no máximo 11 dígitos")
    private String telefone;

    @Schema(description = "Tipo de usuário (ex: administrador, cliente)", example = "admin")
    @NotBlank(message = "O tipo de usuário é obrigatório")
    private String tipo_usuario;

    @Schema(description = "CPF do usuário", example = "12345678900")
    @NotBlank(message = "O CPF é obrigatório")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
    private String cpf;

    @Schema(description = "Senha de acesso (mínimo 8 caracteres)", example = "senhaOkAEJ!")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    @Schema(description = "Data de nascimento", example = "2000-04-16")
    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dtNascimento;

    // Getters and Setters
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }
}
