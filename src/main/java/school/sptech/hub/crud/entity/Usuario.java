package school.sptech.hub.crud.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
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
