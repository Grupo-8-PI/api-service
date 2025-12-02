package school.sptech.hub.infraestructure.search.events;

public class LivroCreatedEvent {
    private final Integer livroId;

    public LivroCreatedEvent(Integer livroId) {
        this.livroId = livroId;
    }

    public Integer getLivroId() {
        return livroId;
    }
}
