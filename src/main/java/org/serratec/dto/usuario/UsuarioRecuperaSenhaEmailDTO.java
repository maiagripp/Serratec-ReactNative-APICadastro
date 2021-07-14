package org.serratec.dto.usuario;

import org.serratec.exception.UsuarioException;
import org.serratec.models.Usuario;
import org.serratec.repository.UsuarioRepository;

public class UsuarioRecuperaSenhaEmailDTO {

private String email;
	
	public Usuario toUsuario(UsuarioRepository usuarioRepository) throws UsuarioException {
		Usuario usuario = usuarioRepository.findByEmail(this.email)
				.orElseThrow(() -> new UsuarioException("Usuário não cadastrado."));

		return usuario;
	}
	
	
	/*public void setCpf(String email) {
		this.email = email;
	}*/
	
}
