package com.skm.sso.auth.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skm.sso.auth.service.AuthService;
import com.skm.sso.common.service.CommonService;
import com.skm.sso.config.ResponseMsg;
import com.skm.sso.config.StatusEnum;
import com.skm.sso.config.enc.SsoUtil;
import com.skm.sso.config.exception.ExceptionCustom;
import com.skm.sso.member.domain.MemDomain;
import com.skm.sso.member.service.MemService;

/**
 * 인증
 * @author lbk
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired private MemService memService;
	@Autowired private AuthService authService;
	@Autowired private CommonService commonService;
	
	/**
	 * Token 발급
	 * @param sid
	 * @param ci
	 * @param di
	 * @param response
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@GetMapping(value="/access")
	@ExceptionHandler(value = ExceptionCustom.class) 
	public ResponseEntity<ResponseMsg> access(String ci, String di, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException{
		String sid = request.getHeader("Site");
		String secretKey = request.getHeader("SecretKey");
		commonService.isValidSite(sid, secretKey);
		ci = SsoUtil.decrypt(ci,secretKey);
		di = SsoUtil.decrypt(di,secretKey);
		ResponseMsg result = new ResponseMsg();
		String token = SsoUtil.bytesToHex(SsoUtil.generateToken(sid+ci+di));
		response.setHeader("Authorization", token); 
		result.setMessage(StatusEnum.OK);
		result.setData(token);
		result.setStatus(HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Token 가져오기
	 * @param sid
	 * @param ci
	 * @param di
	 * @param response
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@GetMapping(value="/get")
	public ResponseEntity<ResponseMsg> get(String ci, String di, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException{
		String sid = request.getHeader("Site");
		String secretKey = request.getHeader("SecretKey");
		commonService.isValidSite(sid, secretKey);
		
		ci = SsoUtil.decrypt(ci,secretKey);
		di = SsoUtil.decrypt(di,secretKey);
		ResponseMsg result = new ResponseMsg();
		String token = authService.getToken(ci, di);
		response.setHeader("Authorization", token); 
		
		result.setMessage(StatusEnum.OK);
		result.setData(token);
		result.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Token 유효 체크
	 * @param sid
	 * @param ci
	 * @param di
	 * @param response
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@GetMapping(value="/check")
	public ResponseEntity<ResponseMsg> check(String token, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException{
		String sid = request.getHeader("Site");
		String secretKey = request.getHeader("SecretKey");
		System.out.println("### /check " + sid + " , " + secretKey + " , " + token);
		
		commonService.isValidSite(sid, secretKey);
		
		//token = SsoUtil.decrypt(token,secretKey);
		
		ResponseMsg result = new ResponseMsg();
		authService.checkToken(token);
		response.setHeader("Authorization", token); 
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Token 삭제
	 * @param ci
	 * @param di
	 * @param response
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@GetMapping(value="/out")
	public ResponseEntity<ResponseMsg> out(String ci, String di, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException{
		String sid = request.getHeader("Site");
		String secretKey = request.getHeader("SecretKey");
		commonService.isValidSite(sid, secretKey);
		
		ci = SsoUtil.decrypt(ci,secretKey);
		di = SsoUtil.decrypt(di,secretKey);
		System.out.println("### /out " + ci + " , " + di);
		ResponseMsg result = new ResponseMsg();
		memService.saveNullToken(ci, di);
		
		result.setMessage(StatusEnum.OK);
		result.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
