package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.AuthRequest;
import com.mmglowbeauty.dto.AuthResponse;
import com.mmglowbeauty.exception.AccesoNoAutorizadoException;
import com.mmglowbeauty.model.Usuario;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.security.JwtUtil;
import com.mmglowbeauty.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.correo)
                .orElseThrow(() -> {
                    log.warn("Intento de login fallido para: {}", request.correo);
                    return new AccesoNoAutorizadoException("Credenciales inválidas");
                });

        if (!passwordEncoder.matches(request.contrasena, usuario.getContrasena())) {
            log.warn("Intento de login fallido para: {}", request.correo);
            throw new AccesoNoAutorizadoException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().name());
        log.info("Login exitoso para: {}", request.correo);

        return new AuthResponse(token, usuario.getRol().name(), usuario.getNombre());
    }
}
