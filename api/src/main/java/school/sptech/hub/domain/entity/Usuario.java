package school.sptech.hub.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String tipo_usuario;
    private String cpf;
    private String senha;
    private LocalDate dtNascimento;

    public Usuario() {
    }

    public Usuario(String nome, String email, String telefone, String tipo_usuario, String cpf, String senha, LocalDate dtNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipo_usuario = tipo_usuario;
        this.cpf = cpf;
        this.senha = senha;
        this.dtNascimento = dtNascimento;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTipo_usuario() { return tipo_usuario; }
    public void setTipo_usuario(String tipo_usuario) { this.tipo_usuario = tipo_usuario; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public LocalDate getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(LocalDate dtNascimento) { this.dtNascimento = dtNascimento; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(nome, usuario.nome) &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(telefone, usuario.telefone) &&
                Objects.equals(tipo_usuario, usuario.tipo_usuario) &&
                Objects.equals(cpf, usuario.cpf) &&
                Objects.equals(senha, usuario.senha) &&
                Objects.equals(dtNascimento, usuario.dtNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, telefone, tipo_usuario, cpf, senha, dtNascimento);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipo_usuario='" + tipo_usuario + '\'' +
                ", cpf='" + cpf + '\'' +
                ", senha='" + senha + '\'' +
                ", dtNascimento=" + dtNascimento +
                '}';
    }
}
