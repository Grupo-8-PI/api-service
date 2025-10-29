package school.sptech.hub.application.usecases.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.hub.domain.dto.venda.ReservaPaginatedResponseDto;
import school.sptech.hub.domain.dto.venda.VendaMapper;
import school.sptech.hub.domain.dto.venda.VendaResponseDto;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListReservasPaginatedUseCase {
    @Autowired
    private VendaRepository vendaRepository;

    public ReservaPaginatedResponseDto execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VendaEntity> vendaPage = vendaRepository.findAll(pageable);
        List<VendaResponseDto> reservas = vendaPage.getContent().stream()
                .map(VendaMapper::toDomain)
                .map(VendaMapper::toResponseDto)
                .collect(Collectors.toList());
        return new ReservaPaginatedResponseDto(
                reservas,
                page,
                size,
                vendaPage.getTotalElements(),
                vendaPage.getTotalPages()
        );
    }
}
