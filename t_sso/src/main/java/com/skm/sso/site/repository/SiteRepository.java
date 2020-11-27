package com.skm.sso.site.repository;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skm.sso.site.domain.SiteDomain;

@Repository
public interface SiteRepository extends CrudRepository<SiteDomain, Long> {
	
	@Cacheable(value = "site")
	public SiteDomain findBySiteIdAndSecretKey(String siteId, String secretKey);
}
