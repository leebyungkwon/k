package com.skm.sso.member.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.skm.sso.config.enc.SsoUtil;
import com.skm.sso.config.exception.ExceptionCustom;
import com.skm.sso.member.domain.MemDomain;
import com.skm.sso.member.domain.MemKey;
import com.skm.sso.member.domain.MemSiteMapDomain;
import com.skm.sso.member.domain.MemSiteMapKey;
import com.skm.sso.member.repository.MemRepository;
import com.skm.sso.member.repository.MemSiteMapRepository;
import com.skm.sso.site.domain.SiteDomain;

@Service
public class MemService {

	@Autowired private MemRepository memRepo;
	@Autowired private MemSiteMapRepository memSiteMapRepo;
	

	@Transactional
	public MemDomain save(MemSiteMapDomain mapDomain, SiteDomain site){
		String secretKey = site.getSecretKey();
		String ci = SsoUtil.decrypt(mapDomain.getCi(),secretKey);
		String di = SsoUtil.decrypt(mapDomain.getDi(),secretKey);

		if(ci == null || di == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"P0001");
		
		mapDomain.setCi(ci);
		mapDomain.setDi(di);
		mapDomain.setSiteId(site.getSiteId());
		mapDomain.setMemId(SsoUtil.decrypt(mapDomain.getMemId(),secretKey));
		mapDomain.setMemNm(SsoUtil.decrypt(mapDomain.getMemNm(),secretKey));
		mapDomain.setBirth(SsoUtil.decrypt(mapDomain.getBirth(),secretKey));
		mapDomain.setPhone(SsoUtil.decrypt(mapDomain.getPhone(),secretKey));
		mapDomain.setEmail(SsoUtil.decrypt(mapDomain.getEmail(),secretKey));

		MemDomain mem = new MemDomain();
		mem.setCi(ci);
		mem.setDi(di);
		mem.setSafeKey(SsoUtil.decrypt(mapDomain.getSafeKey(),secretKey));
		
		memSiteMapRepo.save(mapDomain);
		return memRepo.save(mem); 
	}
	
	public int saveNullToken(String ci, String di) {
		return memRepo.saveNullToken(ci, di); 
	}

	@Transactional
	public List<MemSiteMapDomain> findByCiAndDi(MemDomain mem,String secretKey) {
		String ci = SsoUtil.decrypt(mem.getCi(),secretKey);
		String di = SsoUtil.decrypt(mem.getDi(),secretKey);
		if(ci == null || di == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"P0001");
		
		return memSiteMapRepo.findByCiAndDi(ci, di);
	}

}
