package school.sptech.demo.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.demo.crud.entity.Livro;
import school.sptech.demo.crud.repository.LivroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    LivroRepository repository;

    public List<Livro> getAllLivros(){
        return repository.findAll();
    }
    public Livro getLivroById(Integer id){
        Optional<Livro> livroFound = repository.findById(id);
       if(livroFound.isPresent()){
            return livroFound.get();
        }
        return null;
    }
    public Livro postLivro(Livro livro) {
        List<Livro> existingLivro = repository.findByNomeOrId(livro.getNome(), livro.getId());
        if(existingLivro.isEmpty()){
            return repository.save(livro);
        }else if(existingLivro.size()==1){
            Livro livroToUpdate = existingLivro.get(0);
            livroToUpdate.setQtdLivros((livroToUpdate.getQtdLivros())+ livro.getQtdLivros());
            return repository.save(livroToUpdate);
        }else{
            return null;
        }
    }
    public Livro putLivro(Integer id,Livro livro){
        if(repository.existsById(id)){
            livro.setId(id);
            return repository.save(livro);
        }else{
            return null;
        }
    }
    public Livro deleteLivroById(Integer id){
        if(repository.existsById(id)){
            Optional<Livro> livroToBeDeleted = repository.findById(id);
            repository.deleteById(id);
            return livroToBeDeleted.get();
        }
        return null;
    }

}
