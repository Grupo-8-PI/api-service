package school.sptech.hub.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.hub.entity.Venda;

import static org.junit.jupiter.api.Assertions.*;

class VendaValidatorTest {

    @Test
    @DisplayName("Quando isValidVenda acionado com totalReserva válido deve retornar true")
    void when_thrown_valid_total_reserva_of_venda_should_return_true() {
        // ARRANGE
        Venda vendaValida = new Venda();
        vendaValida.setTotalReserva(5);

        // ACT
        Boolean resultado = VendaValidator.isValidVenda(vendaValida);

        // ASSERT
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Quando isValidVenda acionado com totalReserva inválido deve retornar false")
    void when_thrown_invalid_total_reserva_of_venda_should_return_false() {
        // ARRANGE
        Venda vendaZero = new Venda();
        vendaZero.setTotalReserva(0);

        Venda vendaNula = new Venda();
        vendaNula.setTotalReserva(null);

        // ACT
        Boolean resultadoZero = VendaValidator.isValidVenda(vendaZero);
        Boolean resultadoNulo = VendaValidator.isValidVenda(vendaNula);

        // ASSERT
        assertFalse(resultadoZero);
        assertFalse(resultadoNulo);
    }
}
