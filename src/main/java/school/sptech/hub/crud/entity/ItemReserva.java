package school.sptech.hub.crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class ItemReserva {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

private BigDecimal quantidade;

private List<Livro> livroReservados;


}
