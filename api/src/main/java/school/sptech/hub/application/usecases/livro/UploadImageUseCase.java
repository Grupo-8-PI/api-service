package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.CapaUploadException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.gateways.livro.LivroImagemStorageGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

import java.util.UUID;

@Component
public class UploadImageUseCase {

    private final LivroGateway livroGateway;
    private final LivroImagemStorageGateway imagemStorageGateway;

    public UploadImageUseCase(LivroGateway livroGateway, LivroImagemStorageGateway imagemStorageGateway) {
        this.livroGateway = livroGateway;
        this.imagemStorageGateway = imagemStorageGateway;
    }

    public LivroResponseDto execute(Integer id, byte[] conteudoArquivo, String nomeArquivo, String contentType) {
        if (conteudoArquivo == null || conteudoArquivo.length == 0) {
            throw new CapaUploadException("O arquivo não pode ser vazio");
        }

        if (id == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo");
        }

        // Validar tamanho do arquivo (máximo 5MB)
        if (conteudoArquivo.length > 5 * 1024 * 1024) {
            throw new CapaUploadException("Arquivo muito grande. Tamanho máximo: 5MB");
        }

        // Validar tipo de conteúdo
        if (!isValidImageContentType(contentType)) {
            throw new CapaUploadException("Tipo de arquivo não suportado. Use: JPG, PNG, GIF ou WEBP");
        }

        // Buscar livro existente
        Livro livro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        // Gerar chave única para o S3
        String extensao = extrairExtensao(nomeArquivo, contentType);
        String s3Key = gerarChaveS3(id, extensao);

        try {
            // Upload para S3 via gateway
            String urlCapa = imagemStorageGateway.upload(conteudoArquivo, s3Key);

            // Atualizar URL da capa no livro
            livro.setCapa(urlCapa);

            // Salvar livro atualizado
            Livro livroAtualizado = livroGateway.updateLivro(livro)
                    .orElseThrow(() -> new CapaUploadException("Erro ao salvar URL da capa"));

            return LivroMapper.toResponseDto(livroAtualizado);

        } catch (Exception e) {
            throw new CapaUploadException("Erro ao fazer upload da capa: " + e.getMessage());
        }
    }

    private boolean isValidImageContentType(String contentType) {
        if (contentType == null) return false;
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/webp");
    }

    private String extrairExtensao(String nomeArquivo, String contentType) {
        // Primeiro tenta extrair do nome do arquivo
        if (nomeArquivo != null && nomeArquivo.contains(".")) {
            String ext = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toLowerCase();
            if (ext.matches("(jpg|jpeg|png|gif|webp)")) {
                return ext;
            }
        }

        // Se não conseguir, usa o contentType
        if (contentType != null) {
            switch (contentType) {
                case "image/jpeg":
                    return "jpg";
                case "image/png":
                    return "png";
                case "image/gif":
                    return "gif";
                case "image/webp":
                    return "webp";
            }
        }

        return "jpg"; // default
    }

    private String gerarChaveS3(Integer livroId, String extensao) {
        return String.format("livros/%d/capa-%s.%s", livroId, UUID.randomUUID(), extensao);
    }
}
