package com.skm.sso.member.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity(name="member")
@IdClass(MemKey.class)
public class MemDomain {
	
	@Id
    public String ci;
	@Id
    public String di;
	
	private String safeKey;
	private String token;
	
    @CreationTimestamp
	private LocalDateTime regDt;
	
	private LocalDateTime tokenRegDt;
	
    
}
