package com.metabit.ventasenlinea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.PasswordResetToken;
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
}
