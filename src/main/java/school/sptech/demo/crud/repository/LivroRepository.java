package school.sptech.demo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.demo.crud.entity.Livro;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
  List<Livro> findByNomeOrId(String nome, Integer id);
  List<Livro> findByNome(String nome);
}
