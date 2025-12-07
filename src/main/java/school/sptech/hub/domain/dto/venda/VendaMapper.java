package school.sptech.hub.domain.dto.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;
import school.sptech.hub.infraestructure.gateways.livro.LivroEntityMapper;
import school.sptech.hub.infraestructure.gateways.usuario.UsuarioEntityMapper;

public class VendaMapper {

    public static Venda toEntity(VendaCreateDto dto) {
        Venda venda = new Venda();
        venda.setDtLimite(dto.getDtLimite());
        venda.setDtReserva(dto.getDtReserva());
        venda.setStatusReserva(dto.getStatusReserva());
        venda.setTotalReserva(dto.getTotalReserva());

        if (dto.getLivroId() != null) {
            Livro livro = new Livro();
            livro.setId(dto.getLivroId());
            venda.setLivro(livro);
        }

        return venda;
    }

    public static Venda toEntity(VendaCreateDto dto, Usuario usuario) {
        Venda venda = toEntity(dto);
        venda.setUsuarios(usuario);
        return venda;
    }

    public static VendaResponseDto toResponseDto(Venda venda) {
        VendaResponseDto dto = new VendaResponseDto();
        dto.setId(venda.getId());
        dto.setDtLimite(venda.getDtLimite().toString());
        dto.setDtReserva(venda.getDtReserva().toString());
        dto.setStatusReserva(venda.getStatusReserva());
        dto.setTotalReserva(venda.getTotalReserva());

        if (venda.getLivro() != null) {
            dto.setLivroId(venda.getLivro().getId());
        }

        if (venda.getUsuarios() != null) {
            dto.setUsuarioId(venda.getUsuarios().getId());
        }

        return dto;
    }

    public static Venda updateVendaFields(Venda existingVenda, Venda toBeUpdatedVenda) {
        if (toBeUpdatedVenda.getDtLimite() != null) {
            existingVenda.setDtLimite(toBeUpdatedVenda.getDtLimite());
        }
        if (toBeUpdatedVenda.getDtReserva() != null) {
            existingVenda.setDtReserva(toBeUpdatedVenda.getDtReserva());
        }
        if (toBeUpdatedVenda.getStatusReserva() != null) {
            existingVenda.setStatusReserva(toBeUpdatedVenda.getStatusReserva());
        }
        if (toBeUpdatedVenda.getTotalReserva() != null) {
            existingVenda.setTotalReserva(toBeUpdatedVenda.getTotalReserva());
        }
        if (toBeUpdatedVenda.getLivro() != null) {
            existingVenda.setLivro(toBeUpdatedVenda.getLivro());
        }
        return existingVenda;
    }

    public static Venda toDomain(VendaEntity entity) {
        if (entity == null) return null;

        Venda venda = new Venda();
        venda.setId(entity.getId());
        venda.setDtReserva(entity.getDtReserva());
        venda.setDtLimite(entity.getDtLimite());
        venda.setStatusReserva(entity.getStatusReserva());
        venda.setTotalReserva(entity.getTotalReserva());

        if (entity.getLivro() != null) {
            venda.setLivro(LivroEntityMapper.toDomain(entity.getLivro()));
        }

        if (entity.getUsuarios() != null) {
            venda.setUsuarios(UsuarioEntityMapper.toDomain(entity.getUsuarios()));
        }

        return venda;
    }
}
