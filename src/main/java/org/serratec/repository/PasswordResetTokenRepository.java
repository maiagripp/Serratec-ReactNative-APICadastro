package org.serratec.repository;

import org.serratec.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<Usuario, Long> {

	

}
