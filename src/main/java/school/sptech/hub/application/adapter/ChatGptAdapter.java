package school.sptech.hub.application.adapter;

import school.sptech.hub.domain.entity.Livro;

public interface ChatGptAdapter {

    // Novo método que aceita o livro completo (melhor abordagem)
    String gerarSinopse(Livro livro);
}