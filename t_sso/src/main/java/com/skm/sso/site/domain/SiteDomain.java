package com.skm.sso.site.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="site")
public class SiteDomain {
	
	@Id
	private String siteId;
	
	private String siteNm;
	private String secretKey;
	private String url;
	
}
