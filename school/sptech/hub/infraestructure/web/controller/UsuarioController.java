// ...existing code...
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
// ...existing code...

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Exemplo para cadastro de usuário
    @PostMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@Valid @RequestBody UsuarioCreateDto usuarioDto) {
        try {
            UsuarioResponseDto usuarioCriado = usuarioService.createNewUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Exemplo para listagem de usuários
    @GetMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios() {
        List<UsuarioResponseDto> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Exemplo para buscar usuário por ID
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDto> buscarUsuarioPorId(@PathVariable Integer id) {
        UsuarioResponseDto usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Exemplo para atualizar usuário
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioCreateDto usuarioDto) {
        try {
            UsuarioResponseDto usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDto);
            if (usuarioAtualizado == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
            }
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Exemplo para deletar usuário
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        boolean deletado = usuarioService.deletarUsuario(id);
        if (!deletado) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return ResponseEntity.noContent().build();
    }

    // ...existing code...
}

