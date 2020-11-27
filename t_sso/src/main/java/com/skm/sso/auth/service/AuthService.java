package com.skm.sso.auth.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.skm.sso.auth.domain.TokenDomain;
import com.skm.sso.auth.repository.AuthRepository;
import com.skm.sso.config.exception.ExceptionCustom;
import com.skm.sso.member.domain.MemDomain;
import com.skm.sso.member.domain.MemKey;
import com.skm.sso.member.repository.MemRepository;
import com.skm.sso.member.repository.MemSiteMapRepository;
import com.skm.sso.site.repository.SiteRepository;

@Service
public class AuthService {

	@Autowired private AuthRepository authRepo;
	@Autowired private MemRepository memRepo;
	@Autowired private SiteRepository siteRepo;
	
	@Autowired private MemSiteMapRepository memSiteMapRepo;
	public TokenDomain save(TokenDomain auth){
		return authRepo.save(auth); 
	}
	
	@Transactional
	public void saveToken(String sid, String ci, String di, String secretKey, String token){
		/* 1. 필수 파라미터 확인 */
		if(sid == null || ci == null || di == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST , "P0001");

		/* 2. 유효한 사용자 체크 */
		MemKey memKey = new MemKey();
		memKey.setCi(ci);
		memKey.setDi(di);
		MemDomain mem = memRepo.findByCiAndDi(ci, di);
		if(mem == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"M0001");
		
		
		LocalDateTime now = LocalDateTime.now(); 
		LocalDateTime expireDt = now.plusHours(1); //토큰만료 시간을 한시간 이후로 설정
		
		TokenDomain tokenDomain = new TokenDomain();
		tokenDomain.setSid(sid);
		tokenDomain.setCi(ci);
		tokenDomain.setDi(di);
		tokenDomain.setExpireDt(expireDt);
		tokenDomain.setToken(token);
		authRepo.save(tokenDomain);
		
		mem.setTokenRegDt(now);
		mem.setToken(token);
		memRepo.save(mem); 
	}

	public String getToken(String ci, String di) {
		/* 1. 필수 파라미터 확인 */
		if(ci == null || di == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"P0001");
		
		/* 2. 유효한 사용자 체크 */
		MemKey memKey = new MemKey();
		memKey.setCi(ci);
		memKey.setDi(di);
		MemDomain mem = memRepo.findByCiAndDi(ci, di);
		if(mem == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"M0001");
		if(mem.getToken().isEmpty()) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"M0002");

		/* 3. Token만료 여부 */
		TokenDomain pToken = new TokenDomain();
		pToken.setToken(mem.getToken());
		TokenDomain rToken = authRepo.findByToken(mem.getToken());
		if(rToken == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"M0003");
		
		return mem.getToken();
	}

	public void checkToken(String token) {
		TokenDomain pToken = new TokenDomain();
		pToken.setToken(token);
		TokenDomain rToken = authRepo.findByToken(token);
		if(rToken == null) throw new ExceptionCustom(HttpStatus.BAD_REQUEST ,"M0003");
	}

	/*
	MemSiteMapKey msKey = new MemSiteMapKey();
	msKey.setMid(auth.getMid());
	msKey.setSid(auth.getSid());
	msDomain.setMemSiteMapKey(msKey);
	memSiteMapRepo.save(msDomain);
	*/
}
