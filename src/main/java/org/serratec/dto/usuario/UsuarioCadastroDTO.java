package org.serratec.dto.usuario;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.serratec.models.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioCadastroDTO {
	
	@NotNull
	@NotBlank
	@Email
	@Column(length = 64)
	private String email;
	
	@NotNull
	@NotBlank
	@Column(length = 255)
	@Size(min = 8, max = 255)
	private String senha;
	
	@NotNull
	@NotBlank
	@Column(length = 80)
	@Size(min = 2, max = 80)
	private String nome;
	
	@NotNull
	@NotBlank
	@Column(length = 11)
	private String cpf;
		
	@NotNull
	private LocalDate dataNascimento;
	
	private String url;
	
	
	public Usuario toUsuario() {

		Usuario c = new Usuario();
		
		c.setEmail(this.email);
				
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.senha);		
		c.setSenha(senhaCodificada);
		
		c.setNome(this.nome);
		c.setCPF(this.cpf);		
		c.setDataNascimento(this.dataNascimento);
		c.setUrl(this.url);
		
		return c;
	}
	
	
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public String getUrl() {
		return url;
	}
	
		
}
