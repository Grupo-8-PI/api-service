package school.sptech.hub.infraestructure.gateways.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;

public class VendaEntityMapper {
    public static VendaEntity toEntity(Venda venda) {
        if (venda == null) return null;
        VendaEntity entity = new VendaEntity();
        entity.setId(venda.getId());
        entity.setDtReserva(venda.getDtReserva());
        entity.setDtLimite(venda.getDtLimite());
        entity.setStatusReserva(venda.getStatusReserva());
        entity.setTotalReserva(venda.getTotalReserva());
        if (venda.getUsuarios() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(venda.getUsuarios().getId());
            usuarioEntity.setNome(venda.getUsuarios().getNome());
            usuarioEntity.setEmail(venda.getUsuarios().getEmail());
            usuarioEntity.setTelefone(venda.getUsuarios().getTelefone());
            usuarioEntity.setTipo_usuario(venda.getUsuarios().getTipo_usuario());
            usuarioEntity.setCpf(venda.getUsuarios().getCpf());
            usuarioEntity.setSenha(venda.getUsuarios().getSenha());
            usuarioEntity.setDtNascimento(venda.getUsuarios().getDtNascimento());
            entity.setUsuarios(usuarioEntity);
        }
        return entity;
    }

    public static Venda toDomain(VendaEntity entity) {
        if (entity == null) return null;
        Venda venda = new Venda();
        venda.setId(entity.getId());
        venda.setDtReserva(entity.getDtReserva());
        venda.setDtLimite(entity.getDtLimite());
        venda.setStatusReserva(entity.getStatusReserva());
        venda.setTotalReserva(entity.getTotalReserva());
        if (entity.getUsuarios() != null) {
            school.sptech.hub.domain.entity.Usuario usuario = new school.sptech.hub.domain.entity.Usuario();
            usuario.setId(entity.getUsuarios().getId());
            usuario.setNome(entity.getUsuarios().getNome());
            usuario.setEmail(entity.getUsuarios().getEmail());
            usuario.setTelefone(entity.getUsuarios().getTelefone());
            usuario.setTipo_usuario(entity.getUsuarios().getTipo_usuario());
            usuario.setCpf(entity.getUsuarios().getCpf());
            usuario.setSenha(entity.getUsuarios().getSenha());
            usuario.setDtNascimento(entity.getUsuarios().getDtNascimento());
            venda.setUsuarios(usuario);
        }
        return venda;
    }
}
