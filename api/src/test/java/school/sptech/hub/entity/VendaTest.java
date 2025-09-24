package school.sptech.hub.entity;
import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.Venda;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class VendaTest {

    @Test
    public void testSetAndGetId() {
        Venda venda = new Venda();
        venda.setId(1);
        assertEquals(1, venda.getId());
    }

    @Test
    public void testSetAndGetDtReserva() {
        Venda venda = new Venda();
        LocalDateTime now = LocalDateTime.now();
        venda.setDtReserva(now);
        assertEquals(now, venda.getDtReserva());
    }

    @Test
    public void testSetAndGetDtLimite() {
        Venda venda = new Venda();
        LocalDateTime limite = LocalDateTime.now().plusDays(2);
        venda.setDtLimite(limite);
        assertEquals(limite, venda.getDtLimite());
    }

    @Test
    public void testSetAndGetStatusReserva() {
        Venda venda = new Venda();
        venda.setStatusReserva("Confirmada");
        assertEquals("Confirmada", venda.getStatusReserva());
    }

    @Test
    public void testSetAndGetTotalReserva() {
        Venda venda = new Venda();
        venda.setTotalReserva(5);
        assertEquals(5, venda.getTotalReserva());
    }
}
