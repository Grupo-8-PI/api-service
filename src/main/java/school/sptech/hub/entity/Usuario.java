package school.sptech.hub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, unique = true)
    private String nome;

    @Column(length = 45, unique = true)
    private String email;

    @Column(length = 45, unique = true)
    private String telefone;


    @Column(length = 45)
    private String tipo_usuario;

    @Size(max = 14, message = "Invalid CPF provided")
    @Column(length = 45, unique = true)
    private String cpf;


    @Column(length = 60)
    private String senha;

    @NotNull
    private LocalDate dtNascimento;



    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
