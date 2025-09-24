package school.sptech.hub.domain.entity;

public enum TipoAcabamento {
    CAPA_DURA(1, "CAPA DURA"),
    BROCHURA(2, "BROCHURA");

    private final Integer id;
    private final String descricao;

    TipoAcabamento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAcabamento fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do acabamento não pode ser nulo");
        }

        for (TipoAcabamento tipo : values()) {
            if (tipo.getId().equals(id)) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("ID de acabamento inválido: " + id + ". IDs válidos: 1-CAPA DURA, 2-BROCHURA");
    }

    public static TipoAcabamento fromDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do acabamento não pode ser nula ou vazia");
        }

        String descricaoNormalizada = descricao.trim().toUpperCase();
        for (TipoAcabamento tipo : values()) {
            if (tipo.getDescricao().equals(descricaoNormalizada)) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Descrição de acabamento inválida: " + descricao + ". Valores válidos: CAPA DURA, BROCHURA");
    }

    @Override
    public String toString() {
        return descricao;
    }
}
