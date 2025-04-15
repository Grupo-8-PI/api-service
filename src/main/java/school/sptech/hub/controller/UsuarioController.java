package school.sptech.hub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.controller.dto.UsuarioCreateDto;
import school.sptech.hub.controller.dto.UsuarioResponseDto;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioCreateDto usuario) {
        UsuarioResponseDto createdUser = service.createUser(usuario);
        if (createdUser == null) {
            return ResponseEntity.status(400)
                    .body(null);
        }
        return ResponseEntity.status(201)
                .body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Integer id) {
        UsuarioResponseDto usuario = service.getUserById(id);
        if (usuario != null) {
            return ResponseEntity.status(200)
                    .body(usuario);
        }
        return ResponseEntity.status(404)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUserById(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        Usuario updateUser = service.updateUserById(id, usuario);
        if (updateUser == null) {
            return ResponseEntity.status(404)
                    .build();
        }
        return ResponseEntity.status(200)
                .body(updateUser);
    }
}
