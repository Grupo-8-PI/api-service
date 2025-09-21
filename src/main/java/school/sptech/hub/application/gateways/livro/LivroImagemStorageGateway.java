package school.sptech.hub.application.gateways.livro;

public interface LivroImagemStorageGateway {
    String upload(byte[] conteudo, String key);
    void delete(String key);
    boolean exists(String key);
}
