package com.skm.sso.member.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemSiteMapKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ci;
	private String di;
	private String siteId;
}
