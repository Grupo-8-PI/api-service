package school.sptech.hub.infraestructure.persistance.livroPersistance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroImagemStorageGateway;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Locale;

@Component
public class LivroCapaS3Persistence implements LivroImagemStorageGateway {

    private final S3Client s3Client;

    @Value("${aws.s3.livros-bucket}")
    private String bucket;

    @Value("${aws.regiao}")
    private String region;

    public LivroCapaS3Persistence(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String upload(byte[] conteudo, String key) {
        if (conteudo == null || conteudo.length == 0) {
            throw new IllegalArgumentException("Conteúdo da imagem vazio");
        }
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Chave (key) inválida para upload");
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(detectContentType(key))
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(conteudo));

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }

    @Override
    public void delete(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Chave (key) inválida para deletar");
        }

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    @Override
    public boolean exists(String key) {
        if (key == null || key.isBlank()) {
            return false;
        }

        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3Client.headObject(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String detectContentType(String key) {
        String lower = key.toLowerCase(Locale.ROOT);
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".gif")) return "image/gif";
        if (lower.endsWith(".webp")) return "image/webp";
        if (lower.endsWith(".jpeg") || lower.endsWith(".jpg")) return "image/jpeg";
        return "application/octet-stream";
    }
}
