package com.skm.sso.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.skm.sso.config.exception.ExceptionCustom;
import com.skm.sso.site.domain.SiteDomain;
import com.skm.sso.site.repository.SiteRepository;

@Service
public class CommonService {

	@Autowired private SiteRepository siteRepo;
	
	public SiteDomain isValidSite(String siteId, String secretKey) {
		/* 1. 유효한 사이트 파라미터 체크 */
		if(siteId == null || secretKey == null || siteId.isEmpty() || secretKey.isEmpty())
			throw new ExceptionCustom(HttpStatus.UNAUTHORIZED ,"S0001");
		/* 2. 유효한 사이트 체크 */
		SiteDomain site = siteRepo.findBySiteIdAndSecretKey(siteId, secretKey);
		if(site == null) throw new ExceptionCustom(HttpStatus.UNAUTHORIZED ,"S0002");
		
		return site;
	}

}