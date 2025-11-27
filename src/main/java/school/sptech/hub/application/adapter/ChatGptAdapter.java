package school.sptech.hub.application.adapter;

import school.sptech.hub.domain.entity.Livro;

public interface ChatGptAdapter {
    // Método antigo mantido para compatibilidade
    String gerarSinopse(String titulo, String autor);

    // Novo método que aceita o livro completo (melhor abordagem)
    String gerarSinopse(Livro livro);
}