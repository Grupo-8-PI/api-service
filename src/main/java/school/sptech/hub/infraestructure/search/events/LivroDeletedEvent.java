package school.sptech.hub.infraestructure.search.events;

public class LivroDeletedEvent {
    private final String livroIsbn;

    public LivroDeletedEvent(String livroIsbn) {
        this.livroIsbn = livroIsbn;
    }

    public String getLivroIsbn() {
        return livroIsbn;
    }
}
