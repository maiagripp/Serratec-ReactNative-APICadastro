package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.usuario.UsuarioCadastroDTO;
import org.serratec.dto.usuario.UsuarioCompletoDTO;
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
			Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException("Usuário não encontrado."));
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
			  ResponseEntity<>("CPF inválido!", HttpStatus.BAD_REQUEST);			 
			
			  usuarioRepository.save(usuario);
			
			  emailService.enviar("Bem vindo ao UniGames", "Seu cadastro foi realizado com sucesso!", usuario.getEmail());
			
			return new ResponseEntity<>("Usuário cadastrado com sucesso!", HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			if(usuarioRepository.existsByEmail(usuario.getEmail()) && usuarioRepository.existsByCPF(usuario.getCPF()))
				return new ResponseEntity<>("Já existe um usuário com este email e CPF", HttpStatus.BAD_REQUEST);
			if(usuarioRepository.existsByEmail(usuario.getEmail()))
				return new ResponseEntity<>("Já existe um usuário com este email", HttpStatus.BAD_REQUEST);
			if(usuarioRepository.existsByCPF(usuario.getCPF()))
				return new ResponseEntity<>("Já existe um usuário com este CPF", HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} 
		catch (MethodArgumentNotValidException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioCadastroDTO dto ) throws UsuarioException{
		
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException ("Usuário não encontrado."));
		
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
			Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioException("Usuário não encontrado."));
			usuario.setStatusConta(false);
			usuarioRepository.save(usuario);
			return new ResponseEntity<>("Conta desativada com sucesso.", HttpStatus.OK);

		} catch (UsuarioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/usuario/ativar/{email}")
	public ResponseEntity<?> getAtivar(@PathVariable String email) {

		try {
			Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioException("Usuário não encontrado."));
			usuario.setStatusConta(true);
			usuarioRepository.save(usuario);
			return new ResponseEntity<>("Conta reativada com sucesso.", HttpStatus.OK);

		} catch (UsuarioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/*@ApiOperation(value = "Envio de email para recuperação de senha.")
	@PostMapping("cliente/email")
	public ResponseEntity<?> sendEmail(@RequestBody ClienteSolicitarEnvioEmailDTO dto) throws ClienteException, MessagingException{
		
		Cliente cliente = dto.toCliente(clienteRepository);
		
		emailService.enviar("Olá, você solicitou a recuperação de email. Poderá ser feito pelo seguinte link: http://localhost:3000/alterar-senha", cliente.getNome(),
				cliente.getEmail());
		
		return new ResponseEntity<>("As instruções para a recuperação da senha foram enviadas para o seu email", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Recuperação de senha.")
	@PostMapping("cliente/recuperacao")
	public ResponseEntity<?> recuperarSenha(@RequestBody ClienteAlterarSenhaDTO dto) throws ClienteException{
		
		Cliente cliente = dto.toCliente(clienteRepository);
		
		clienteRepository.save(cliente);
		
		return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
	}*/
	
	
} 
