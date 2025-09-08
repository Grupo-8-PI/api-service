package school.sptech.hub.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import school.sptech.hub.entity.Venda;

public class VendaValidator {

    public static final Logger logger = LoggerFactory.getLogger(VendaValidator.class);
    public static final int INVALID_TOTAL_RESERVA_QTD = 0;

    public static boolean isValidVenda(Venda venda){
        if (venda == null) {
            logger.warn("Tentativa de validação de venda nula.");
            return false;
        }

        if (venda.getUsuarios() == null) {
            logger.warn("Venda inválida: usuário não associado.");
            return false;
        }

        if (venda.getTotalReserva() == null || venda.getTotalReserva() <= INVALID_TOTAL_RESERVA_QTD) {
            logger.warn("Venda inválida: Valor total da reserva inválido. Valor recebido: {}", venda.getTotalReserva());
            return false;
        }

        return true;
    }
}
