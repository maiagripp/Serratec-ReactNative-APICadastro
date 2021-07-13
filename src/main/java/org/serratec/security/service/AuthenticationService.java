package org.serratec.security.service;

import java.util.Optional;

import org.serratec.models.Usuario;
import org.serratec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = repository.findByEmail(username);
		
		if(optional.isPresent()) {
			return (UserDetails) optional.get();
		}
		
		throw new UsernameNotFoundException("User not found");
	}	
	
	public Usuario getCliente() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String corrente = authentication.getName();
		
		return (Usuario)loadUserByUsername(corrente);
	}

}
