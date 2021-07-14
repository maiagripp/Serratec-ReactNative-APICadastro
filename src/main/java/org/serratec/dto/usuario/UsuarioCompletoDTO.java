package org.serratec.dto.usuario;

import java.time.LocalDate;
import org.serratec.models.Usuario;

public class UsuarioCompletoDTO {
	
	private String email;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private Boolean statusConta;
	private String url;
	
	public UsuarioCompletoDTO(Usuario usuario) {
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCPF();
		this.dataNascimento = usuario.getDataNascimento();
		this.url = usuario.getUrl();
		this.statusConta = usuario.getStatusConta();
	}

	public Boolean getStatusConta() {
		return statusConta;
	}	
	public String getEmail() {
		return email;
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
