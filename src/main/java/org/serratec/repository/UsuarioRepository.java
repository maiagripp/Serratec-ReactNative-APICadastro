package org.serratec.repository;

import java.util.Optional;

import org.serratec.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	boolean existsByEmail(String email);

	Optional<Usuario> findByEmail(String email);

	boolean existsByCPF(String cpf);

}
