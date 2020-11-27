package com.skm.sso.auth.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skm.sso.auth.domain.TokenDomain;

@Repository
public interface AuthRepository extends CrudRepository<TokenDomain, String> {
	public TokenDomain save(TokenDomain tokenDomain);
	
	@Query("SELECT a FROM access_token a WHERE a.token = ?1 and expire_dt > now()")
	public TokenDomain findByToken(String token);
	
}
