package cibertec.edu.pe.controller;

import cibertec.edu.pe.dto.*;
import cibertec.edu.pe.model.Rol;
import cibertec.edu.pe.model.Usuario;
import cibertec.edu.pe.repository.RolRepository;
import cibertec.edu.pe.repository.UsuarioRepository;
import cibertec.edu.pe.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // 1. Buscamos al usuario manualmente
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElse(null);

        // 2. Validamos manual
        if (usuario != null && passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            String token = jwtUtil.generarToken(request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        Rol rol = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
    }

    @GetMapping("/crear-test")
    public ResponseEntity<String> crearUsuarioTest() {
        // 1. Buscamos o creamos el rol ADMIN
        Rol rol = rolRepository.findByNombre("ADMIN").orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("ADMIN");
            return rolRepository.save(nuevoRol);
        });

        // 2. Creamos el usuario asegurándonos de usar TU passwordEncoder
        if (usuarioRepository.findByUsername("demo").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setUsername("demo");
            // Aquí ocurre la magia: Java encripta con sus propias reglas
            usuario.setPassword(passwordEncoder.encode("demo123"));
            usuario.setRol(rol);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario 'demo' creado con éxito");
        }
        return ResponseEntity.ok("El usuario 'demo' ya existía");
    }
}
