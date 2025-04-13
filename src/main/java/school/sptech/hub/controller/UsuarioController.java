package school.sptech.hub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> createUser(@Valid @RequestBody Usuario usuario) {
        Usuario createdUser = service.createUser(usuario);
        if (createdUser == null) {
            return ResponseEntity.status(400)
                    .body(null);
        }
        return ResponseEntity.status(201)
                .body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Integer id) {
        Usuario usuario = service.getUserById(id);
        if (usuario != null) {
            return ResponseEntity.ok()
                    .body(usuario);
        }
        return ResponseEntity.notFound()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUserById(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
         Usuario updateUser = service.updateUserById(id, usuario);
         if(updateUser == null){
             return ResponseEntity.notFound()
                     .build();
         }
         return ResponseEntity.status(200).body(updateUser);
    }
}
