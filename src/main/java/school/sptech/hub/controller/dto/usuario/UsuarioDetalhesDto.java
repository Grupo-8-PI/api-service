package school.sptech.hub.controller.dto.usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.hub.entity.Usuario;

import java.util.Collection;
import java.util.List;

public class UsuarioDetalhesDto implements UserDetails {
    private final String nome;
    private final String email;
    private final String tipoUsuario;
    private final String senha;

    public UsuarioDetalhesDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.tipoUsuario = usuario.getTipo_usuario() != null ? usuario.getTipo_usuario().toUpperCase() : "USER";
    }

    public String getNome() {
        return nome;
    }

    public String getTipoUsuario() {return tipoUsuario;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
