package school.sptech.demo.crud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Doador {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 45)
    private String nome;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String chavePix;

    public String getChavePix() {
        return chavePix;
    }
    // This set maybe will be put as private due to safety
    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
