package com.skm.sso.member.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skm.sso.member.domain.MemSiteMapDomain;
import com.skm.sso.member.domain.MemSiteMapKey;

@Repository
public interface MemSiteMapRepository extends CrudRepository<MemSiteMapDomain, MemSiteMapKey> {
	public MemSiteMapDomain save(MemSiteMapDomain mem);

	public List<MemSiteMapDomain> findByCiAndDi(String ci, String di);
}
