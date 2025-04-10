package school.sptech.hub.crud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Usuario {
@Column(length = 45)
private String nome;

@Column(length = 45)
private String email;

@Column(length = 45)
private String telefone;

@Column(length = 45)
private String tipo_usuario;

@Column(length = 45)
private String cpf;

@Column(length = 45)
private String senha;

private LocalDate dtNascimento;


}
