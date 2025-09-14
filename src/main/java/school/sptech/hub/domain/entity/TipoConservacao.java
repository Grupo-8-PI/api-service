package school.sptech.hub.domain.entity;

public enum TipoConservacao {
    EXCELENTE(1, "EXCELENTE"),
    BOM(2, "BOM"),
    RAZOAVEL(3, "RAZOÁVEL"),
    DEGRADADO(4, "DEGRADADO");

    private final Integer id;
    private final String descricao;

    TipoConservacao(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoConservacao fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da conservação não pode ser nulo");
        }

        for (TipoConservacao tipo : values()) {
            if (tipo.getId().equals(id)) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("ID de conservação inválido: " + id + ". IDs válidos: 1-EXCELENTE, 2-BOM, 3-RAZOÁVEL, 4-DEGRADADO");
    }

    public static TipoConservacao fromDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da conservação não pode ser nula ou vazia");
        }

        String descricaoNormalizada = descricao.trim().toUpperCase();
        for (TipoConservacao tipo : values()) {
            if (tipo.getDescricao().equals(descricaoNormalizada)) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Descrição de conservação inválida: " + descricao + ". Valores válidos: EXCELENTE, BOM, RAZOÁVEL, DEGRADADO");
    }

    @Override
    public String toString() {
        return descricao;
    }
}
