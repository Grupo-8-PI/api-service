package school.sptech.hub.infraestructure.search.events;

public class LivroUpdatedEvent {
    private final Integer livroId;

    public LivroUpdatedEvent(Integer livroId) {
        this.livroId = livroId;
    }

    public Integer getLivroId() {
        return livroId;
    }
}
