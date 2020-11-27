package com.skm.sso.member.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skm.sso.common.service.CommonService;
import com.skm.sso.member.domain.MemDomain;
import com.skm.sso.member.domain.MemSiteMapDomain;
import com.skm.sso.member.service.MemService;
import com.skm.sso.site.domain.SiteDomain;

@RestController
@RequestMapping("/mem")
public class MemController {
	
	@Autowired private MemService memService;
	@Autowired private CommonService commonService;
	
	@GetMapping(value="/test")
	public String test(){
		return "######### success connecting";
	}
	/**
	 * 고객정보 조회
	 * @param mem
	 * @return
	 */
	@GetMapping(value="/get")
	public ResponseEntity<MemDomain> getMem(MemDomain mem){
		System.out.println("##### getMem");
		System.out.println(mem);
		//Optional<MemDomain> m = memService.findByMno(mem.getMid());
		return null;
	}
	
	/**
	 * 고객정보 저장
	 * @param mem
	 * @param sid
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@PostMapping(value="/save")
	public ResponseEntity<MemDomain> save(MemSiteMapDomain mem, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException{
		String sid = request.getHeader("site");
		String secretKey = request.getHeader("SecretKey");
		System.out.println("################## save");
		SiteDomain site = commonService.isValidSite(sid, secretKey);
		return new ResponseEntity<MemDomain>(memService.save(mem, site), HttpStatus.OK);
	}
	

	@GetMapping(value="/list")
	public ResponseEntity<List<MemSiteMapDomain>> getList(MemDomain mem, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException{
		String sid = request.getHeader("site");
		String secretKey = request.getHeader("SecretKey");
		System.out.println("##### getList");
		System.out.println(mem);

		commonService.isValidSite(sid, secretKey);
		return new ResponseEntity<List<MemSiteMapDomain>>(memService.findByCiAndDi(mem,secretKey), HttpStatus.OK);
	}
	

}