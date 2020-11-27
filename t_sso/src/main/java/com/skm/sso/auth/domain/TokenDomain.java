package com.skm.sso.auth.domain;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="access_token")
public class TokenDomain {
	
	@Id
	private String token;

	private String ci;
	private String di;
	private String sid;
	
    @CreationTimestamp
    private LocalDateTime regDt;

    private LocalDateTime expireDt;

}
