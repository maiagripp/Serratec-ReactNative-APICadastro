package org.serratec.dto.usuario;

import org.serratec.exception.UsuarioException;
import org.serratec.models.Usuario;
import org.serratec.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioAlterarSenhaDTO {

	private String email;
	private String senha;
	
	public Usuario toUsuario(UsuarioRepository usuarioRepository) throws UsuarioException {
		Usuario usuario = usuarioRepository.findByEmail(this.email)
				.orElseThrow(() -> new UsuarioException("Usuário não cadastrado."));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.senha);
		
		usuario.setSenha(senhaCodificada);
		
		usuarioRepository.save(usuario);
		
		return usuario;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}	
	
}
