package com.skm.sso.member.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Data
@Entity(name="member_site_map")
@IdClass(MemSiteMapKey.class)
public class MemSiteMapDomain {
	@Id
	private String ci;
	@Id
	private String di;
	@Id
	private String siteId;
	
	private String memId;
	private String memNm;
	private String birth;
	private String phone;
	private String email;
	private String safeKey;
	
    @CreationTimestamp
    private LocalDateTime regDt;
    @UpdateTimestamp
    private LocalDateTime uptDt;
	public String getSafeKey() {
		return this.safeKey;
	}
    
}


