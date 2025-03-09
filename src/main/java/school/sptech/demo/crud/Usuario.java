package school.sptech.demo.crud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Usuarios tipoUsuario;

    @Column(nullable = false, unique = true)
    private String cpf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuarios getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Usuarios tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Usuario(Integer id, String username, String password, Usuarios tipoUsuario, String cpf) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.cpf = cpf;
    }

    public Usuario() {
    }
}
