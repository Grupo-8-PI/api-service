package school.sptech.hub.application.adapter;

import school.sptech.hub.domain.entity.Livro;

public interface ChatGptAdapter {

    String gerarSinopse(Livro livro);
}