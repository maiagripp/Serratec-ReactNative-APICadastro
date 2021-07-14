package org.serratec.dto.usuario;

import org.serratec.exception.UsuarioException;
import org.serratec.models.Usuario;
import org.serratec.repository.UsuarioRepository;

public class UsuarioSolicitarEnvioSenhaEmailDTO {

private String email;

//@Autowired
//PasswordResetTokenRepository passwordRepository;
	
	public Usuario toUsuario(UsuarioRepository usuarioRepository) throws UsuarioException {
		Usuario usuario = usuarioRepository.findByEmail(this.email)
				.orElseThrow(() -> new UsuarioException("Usuário não cadastrado."));

		return usuario;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	/*private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, Usuario usuario) {
			    String url = contextPath + "/user/changePassword?token=" + token;
			    //String message = messages.getMessage("message.resetPassword", null, locale);
			    return constructEmail("Reset Password \r\n" + url, url, usuario);
	}	
	private SimpleMailMessage constructEmail(String subject, String body, Usuario usuario) {
			    SimpleMailMessage email = new SimpleMailMessage();
			    email.setSubject(subject);
			    email.setText(body);
			    email.setTo(usuario.getEmail());
			    //email.setFrom(env.getProperty("support.email"));
			    return email;
	}
	public void createPasswordResetTokenForUsuario(Usuario usuario, String token) {
		PasswordResetToken myToken = new PasswordResetToken();
	    passwordRepository.saveAll((Iterable<Usuario>) myToken);		
	}*/
	
	
}
