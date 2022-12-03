package com.example.rest.security;

import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {
	// Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
	private final String JWT_SECRET = "Bank@2000";

	// Thời gian có hiệu lực của chuỗi jwt
	private final long ACCESS_JWT_EXPIRATION = 86400000L;//1 day

	private final long REFRESH_JWT_EXPIRATION = 604800000L;//1 week

	// Tạo ra jwt từ thông tin user
	public String generateAccessToken(String phoneNumber) {
		return Jwts.builder().setSubject(phoneNumber).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + ACCESS_JWT_EXPIRATION)).signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}
	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + ACCESS_JWT_EXPIRATION)).signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}

//	public String generateRefreshToken(String email) {
//		Date now = new Date();
//		Date expiryDate = new Date(now.getTime() + REFRESH_JWT_EXPIRATION);
//		// Tạo chuỗi json web token từ username của user.
//		return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiryDate)
//				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
//	}

	// Lấy phone number từ jwt
	public String getPhoneNumberFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	// Lấy uuid từ jwt
	public String getUUIDFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		return claims.getId();
	}

	public boolean validateToken(String authToken) {
		System.out.println("Token = " + authToken);
		try {
			Jws<Claims> jwtParser = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			System.out.println(ex.getMessage());
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}