package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.serratec.dto.usuario.UsuarioAlterarSenhaDTO;
import org.serratec.dto.usuario.UsuarioCadastroDTO;
import org.serratec.dto.usuario.UsuarioCompletoDTO;
import org.serratec.dto.usuario.UsuarioSolicitarEnvioSenhaEmailDTO;
import org.serratec.exception.UsuarioException;
import org.serratec.metodos.ValidaCPF;
import org.serratec.models.Usuario;
import org.serratec.repository.UsuarioRepository;
import org.serratec.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioResource {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping("/usuario/todos")
	public ResponseEntity<?> getTodos() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioCompletoDTO> usuariosDtos = new ArrayList<>();
		
		for (Usuario usuario : usuarios) {
			usuariosDtos.add(new UsuarioCompletoDTO(usuario));
		}
		
		return new ResponseEntity<>(usuariosDtos, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/detalhe/{id}")
	public ResponseEntity<?> getDetalheUsuario(@PathVariable Long id) {
		
		try {
			Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException("Usu�rio n�o encontrado."));
			return new ResponseEntity<>(new UsuarioCompletoDTO(usuario), HttpStatus.OK);
			
		} catch (UsuarioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/usuario")
	public ResponseEntity<?> postUsuario(@Validated @RequestBody UsuarioCadastroDTO usuarioDTO) {
		
		Usuario usuario = usuarioDTO.toUsuario();
		try {		
			  if(!ValidaCPF.isCPF(usuario.getCPF())) return new
			  ResponseEntity<>("CPF inv�lido!", HttpStatus.BAD_REQUEST);			 
			
			  usuarioRepository.save(usuario);
			
			  emailService.enviar("Bem vindo ao UniGames", "Seu cadastro foi realizado com sucesso!", usuario.getEmail());
			
			return new ResponseEntity<>("Usu�rio cadastrado com sucesso!", HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			if(usuarioRepository.existsByEmail(usuario.getEmail()) && usuarioRepository.existsByCPF(usuario.getCPF()))
				return new ResponseEntity<>("J� existe um usu�rio com este email e CPF", HttpStatus.BAD_REQUEST);
			if(usuarioRepository.existsByEmail(usuario.getEmail()))
				return new ResponseEntity<>("J� existe um usu�rio com este email", HttpStatus.BAD_REQUEST);
			if(usuarioRepository.existsByCPF(usuario.getCPF()))
				return new ResponseEntity<>("J� existe um usu�rio com este CPF", HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} 
		catch (MethodArgumentNotValidException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioCadastroDTO dto ) throws UsuarioException{
		
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException ("Usu�rio n�o encontrado."));
		
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
		usuario.setNome(dto.getNome());
		usuario.setCPF(dto.getCpf());
		usuario.setDataNascimento(dto.getDataNascimento());
		usuario.setUrl(dto.getUrl());
				
		usuario.setSenha(dto.getSenha());		
				
		usuarioRepository.save(usuario);
		
		return new  ResponseEntity<>("Cliente alterado com sucesso", HttpStatus.OK);		
	}
	
		
	@GetMapping("/usuario/desativar/{email}")
	public ResponseEntity<?> getDesativar(@PathVariable String email) {

		try {
			Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioException("Usu�rio n�o encontrado."));
			usuario.setStatusConta(false);
			usuarioRepository.save(usuario);
			return new ResponseEntity<>("Conta desativada com sucesso.", HttpStatus.OK);

		} catch (UsuarioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/usuario/reativar/{email}")
	public ResponseEntity<?> getAtivar(@PathVariable String email) {

		try {
			Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioException("Usu�rio n�o encontrado."));
			usuario.setStatusConta(true);
			usuarioRepository.save(usuario);
			return new ResponseEntity<>("Conta reativada com sucesso.", HttpStatus.OK);

		} catch (UsuarioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/usuario/email-senha")
	public ResponseEntity<?> sendEmail(@RequestBody UsuarioSolicitarEnvioSenhaEmailDTO dto) throws UsuarioException, MessagingException{
		
		Usuario usuario = dto.toUsuario(usuarioRepository);
		
		//String token = UUID.randomUUID().toString();
	    //dto.createPasswordResetTokenForUsuario(usuario, token);
		
		emailService.enviar("Altera��o de senha", "Voc� solicitou a recupera��o de senha. Poder� ser feito pelo seguinte link: http://localhost:8080/usuario/alterar-senha",usuario.getEmail());
				
		return new ResponseEntity<>("As instru��es para a recupera��o da senha foram enviadas para o seu email", HttpStatus.OK);
	}
	
	
	@PostMapping("/usuario/alterar-senha")
	public ResponseEntity<?> recuperarSenha(@RequestBody UsuarioAlterarSenhaDTO dto) throws UsuarioException{
		
		Usuario usuario = dto.toUsuario(usuarioRepository);
		
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
	}
	
	
			
	
	
} 
