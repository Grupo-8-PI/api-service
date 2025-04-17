package school.sptech.hub.entity;

import jakarta.persistence.*;

@Entity
public class Acabamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String tipoAcabamento;
}
