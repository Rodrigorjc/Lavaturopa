package com.example.lavaturopa.controlers;

import com.example.lavaturopa.dto.AuthenticationDTO;
import com.example.lavaturopa.dto.AuthenticationRequestDTO;
import com.example.lavaturopa.dto.UserDTO;
import com.example.lavaturopa.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public AuthenticationDTO register(@RequestBody UserDTO userDTO) {
        return usuarioService.register(userDTO);
    }

    @PostMapping("/authenticate")
    public AuthenticationDTO authenticate(@RequestBody AuthenticationRequestDTO dto) {
        return usuarioService.authenticate(dto);
    }
}