package com.skm.sso.member.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skm.sso.member.domain.MemDomain;
import com.skm.sso.member.domain.MemKey;

@Repository
public interface MemRepository extends CrudRepository<MemDomain, MemKey> {
	
	public MemDomain findByCiAndDi(String ci, String di);
	public MemDomain save(MemDomain mem);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE member a SET a.token = null , token_reg_dt = null WHERE a.ci = ?1 and a.di = ?2")
	public int saveNullToken(String ci, String di);
}
