package school.sptech.hub.application.validators;

import school.sptech.hub.domain.entity.Venda;

public class VendaValidator {
    public static boolean isValidVenda(Venda venda){
        int INVALID_TOTAL_RESERVA_QTD = 0;
        if(venda.getTotalReserva() == null || venda.getTotalReserva() <= INVALID_TOTAL_RESERVA_QTD) {
            return false;
        }

        // Validar se o livro foi informado
        if(venda.getLivro() == null || venda.getLivro().getId() == null) {
            return false;
        }

        return true;
    }
}
