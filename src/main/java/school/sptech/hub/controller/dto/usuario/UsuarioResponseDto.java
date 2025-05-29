package school.sptech.hub.controller.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "DTO de resposta com os dados públicos do usuário")
public class UsuarioResponseDto {

    @Schema(description = "ID único do usuário no sistema", example = "1")
    private Integer id;

    @Schema(description = "Nome completo do usuário", example = "José da Silva")
    private String nome;

    @Schema(description = "Endereço de email do usuário", example = "jose.silva@email.com")
    private String email;

    @Schema(description = "Número de telefone com DDD", example = "11912345678")
    private String telefone;

    @Schema(description = "Tipo de perfil do usuário (ex: cliente, admin)", example = "cliente")
    private String tipo_usuario;

    @Schema(description = "CPF do usuário (formato 000.000.000-00)", example = "12345678900")
    private String cpf;

    @Schema(description = "Data de nascimento do usuário no formato YYYY-MM-DD", example = "1990-05-15")
    private LocalDate dtNascimento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }
}