package school.sptech.hub.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlGenerator {

    @Value("${aws.bucket}")
    private String bucket;

    @Value("${aws.regiao}")
    private String region;

    /**
     * Gera a URL da capa do livro baseada no ID do livro
     * Formato: https://{bucket}.s3.{region}.amazonaws.com/livros/{livroId}/capa-{livroId}.jpg
     *
     * @param livroId ID do livro
     * @return URL completa da capa do livro
     */
    public String gerarUrlCapa(Integer livroId) {
        if (livroId == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo");
        }

        String key = String.format("livros/%d/capa-%d.jpg", livroId, livroId);
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }

    /**
     * Gera a URL da capa do livro com extensão específica
     *
     * @param livroId ID do livro
     * @param extensao Extensão do arquivo (jpg, png, gif, webp)
     * @return URL completa da capa do livro
     */
    public String gerarUrlCapa(Integer livroId, String extensao) {
        if (livroId == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo");
        }

        if (extensao == null || extensao.trim().isEmpty()) {
            extensao = "jpg"; // default
        }

        String key = String.format("livros/%d/capa-%d.%s", livroId, livroId, extensao);
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }
}
