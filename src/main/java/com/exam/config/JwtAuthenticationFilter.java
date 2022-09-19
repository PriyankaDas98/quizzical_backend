package com.exam.config;

import com.exam.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;

import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	   private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
        {
            //yes
            jwtToken=requestTokenHeader.substring(7);
            try {
                username = jwtUtils.extractUsername(jwtToken);
            }catch (IllegalArgumentException e){
                logger.warn("unable to get jwt token");
            }
            catch(ExpiredJwtException e)
            {
                log.warn("{} has expired", jwtToken);
            }
            catch (MalformedJwtException e){
                log.warn("invalid jwt");
            }
        }
        else{
        	logger.info("Invalid Token");
        }
        //validate
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // to avoid null pointer exception, primitive boolean expression is used
            if(Boolean.TRUE.equals(this.jwtUtils.validateToken(jwtToken,userDetails))){
                //token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication =
                        new UsernamePasswordAuthenticationToken
                                (userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
            }
            else {
            	logger.error("Token is not valid");
            }

        }
        filterChain.doFilter(request,response);
    }
}

