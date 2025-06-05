

package school.sptech.hub.validators;

import school.sptech.hub.entity.Venda;

public class VendaValidator {
    public static boolean isValidVenda(Venda venda){
        int INVALID_TOTAL_RESERVA_QTD = 0;
        if(venda.getTotalReserva() == null || venda.getTotalReserva() <= INVALID_TOTAL_RESERVA_QTD) {
            return false;
        }
        return true;
    }
}
