package com.example.lavaturopa.servicios;

import com.example.lavaturopa.dto.AuthenticationDTO;
import com.example.lavaturopa.dto.AuthenticationRequestDTO;
import com.example.lavaturopa.dto.UserDTO;
import com.example.lavaturopa.enums.Rol;
import com.example.lavaturopa.modelos.Usuario;
import com.example.lavaturopa.repositorios.UsuarioRepositorio;
import com.example.lavaturopa.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepositorio.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Usuario buscarUsuarioPorNombre(String username){
        return usuarioRepositorio.findByEmail(username).orElse(null);
    }

    public Usuario guardarUsuario(UserDTO dto){
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.USER);
        return usuarioRepositorio.save(usuario);
    }

    public AuthenticationDTO register(UserDTO userDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(userDTO.getNombre());
        usuario.setApellidos(userDTO.getApellidos());
        usuario.setEmail(userDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.USER);
        usuarioRepositorio.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return AuthenticationDTO.builder().token(jwtToken).build();
    }

    public AuthenticationDTO authenticate(AuthenticationRequestDTO dto) {
        return authenticationService.authenticate(dto);
    }
}