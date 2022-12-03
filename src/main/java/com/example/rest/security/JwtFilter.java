package com.example.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.rest.common.CommonService;
import com.example.rest.model.entity.Comment;
import com.example.rest.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CommonService commonService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = request.getHeader("Authorization");
			if (token != null && jwtProvider.validateToken(token)) {
				String phoneNumber = jwtProvider.getPhoneNumberFromJWT(token);
				UserDetails userDetails = userService.loadUserByUsername(phoneNumber);
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}");
			System.out.println(e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

}
