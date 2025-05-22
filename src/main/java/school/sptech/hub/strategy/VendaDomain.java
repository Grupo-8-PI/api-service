

package school.sptech.hub.strategy;

import school.sptech.hub.entity.Venda;

public class VendaDomain {
    public static boolean isValidVenda(Venda venda){
        int INVALID_TOTAL_RESERVA_QTD = 0;
        if(venda.getTotalReserva() <= INVALID_TOTAL_RESERVA_QTD|| venda.getTotalReserva() == null) {
            return false;
        }
        return true;
    }
}
