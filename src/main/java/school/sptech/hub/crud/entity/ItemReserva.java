package school.sptech.hub.crud.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class ItemReserva {

private BigDecimal quantidade;

private List<Livro> livroReservados;


}
