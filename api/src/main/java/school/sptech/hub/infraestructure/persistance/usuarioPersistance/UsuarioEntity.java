package school.sptech.hub.infraestructure.persistance.usuarioPersistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotNull
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    @NotNull
    @Email
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, length = 20)
    @NotNull
    private String tipo_usuario;

    @Column(nullable = false, unique = true, length = 14)
    @NotNull
    private String cpf;

    @Column(nullable = false, length = 60)
    @NotNull
    private String senha;

    @Column(nullable = false)
    @NotNull
    private LocalDate dtNascimento;

    public UsuarioEntity() {}

    public UsuarioEntity(Integer id, String nome, String email, String telefone, String tipo_usuario, String cpf, String senha, LocalDate dtNascimento) {
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
        UsuarioEntity that = (UsuarioEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(tipo_usuario, that.tipo_usuario) &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(senha, that.senha) &&
                Objects.equals(dtNascimento, that.dtNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, telefone, tipo_usuario, cpf, senha, dtNascimento);
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
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
