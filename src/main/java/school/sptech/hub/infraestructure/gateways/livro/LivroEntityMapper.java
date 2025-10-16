package school.sptech.hub.infraestructure.gateways.livro;

import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.infraestructure.persistance.livroPersistance.LivroEntity;
import school.sptech.hub.infraestructure.persistance.acabamentoPersistance.AcabamentoEntity;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaEntity;
import school.sptech.hub.infraestructure.persistance.conservacaoPersistance.ConservacaoEntity;
import school.sptech.hub.infraestructure.gateways.acabamento.AcabamentoEntityMapper;
import school.sptech.hub.infraestructure.gateways.categoria.CategoriaEntityMapper;
import school.sptech.hub.infraestructure.gateways.conservacao.ConservacaoEntityMapper;

public class LivroEntityMapper {

    public static LivroEntity toEntity(Livro livro) {
        if (livro == null) return null;

        LivroEntity entity = new LivroEntity();
        entity.setId(livro.getId());
        entity.setTitulo(livro.getTitulo());
        entity.setIsbn(livro.getIsbn());
        entity.setAutor(livro.getAutor());
        entity.setEditora(livro.getEditora());
        entity.setAnoPublicacao(livro.getAnoPublicacao());
        entity.setPaginas(livro.getPaginas());
        entity.setCapa(livro.getCapa());
        entity.setPreco(livro.getPreco());

        if (livro.getAcabamento() != null && livro.getAcabamento().getId() != null) {
            AcabamentoEntity acabamentoRef = new AcabamentoEntity();
            acabamentoRef.setId(livro.getAcabamento().getId());
            entity.setAcabamento(acabamentoRef);
        }

        if (livro.getCategoria() != null) {
            entity.setCategoria(CategoriaEntityMapper.toEntity(livro.getCategoria()));
        }

        if (livro.getEstadoConservacao() != null && livro.getEstadoConservacao().getId() != null) {
            ConservacaoEntity conservacaoRef = new ConservacaoEntity();
            conservacaoRef.setId(livro.getEstadoConservacao().getId());
            entity.setEstadoConservacao(conservacaoRef);
        }

        entity.setDescricao(livro.getDescricao());

        return entity;
    }

    public static Livro toDomain(LivroEntity entity) {
        if (entity == null) return null;

        Livro livro = new Livro();
        livro.setId(entity.getId());
        livro.setTitulo(entity.getTitulo());
        livro.setIsbn(entity.getIsbn());
        livro.setAutor(entity.getAutor());
        livro.setEditora(entity.getEditora());
        livro.setAnoPublicacao(entity.getAnoPublicacao());
        livro.setPaginas(entity.getPaginas());
        livro.setCapa(entity.getCapa());
        livro.setPreco(entity.getPreco());

        if (entity.getAcabamento() != null) {
            livro.setAcabamento(AcabamentoEntityMapper.toDomain(entity.getAcabamento()));
        }

        if (entity.getCategoria() != null) {
            livro.setCategoria(CategoriaEntityMapper.toDomain(entity.getCategoria()));
        }

        if (entity.getEstadoConservacao() != null) {
            livro.setEstadoConservacao(ConservacaoEntityMapper.toDomain(entity.getEstadoConservacao()));
        }

        livro.setDescricao(entity.getDescricao());

        return livro;
    }
}
