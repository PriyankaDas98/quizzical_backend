package com.exam.config;

import com.exam.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;

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
	   private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
//        System.out.println(requestTokenHeader);
        String username = null;
        String jwtToken = null;
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
        {
            //yes
            jwtToken=requestTokenHeader.substring(7);
            try {
                username = jwtUtils.extractUsername(jwtToken);
            }catch(ExpiredJwtException e)
            {
                logger.warn(jwtToken + "Has expired!");
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else{
        	logger.info("Invalid Token");
//            System.out.println("Invalid Token,not start with bearer");
        }
        //validate
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if(this.jwtUtils.validateToken(jwtToken,userDetails)){
                //token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
            }
            else {
            	logger.error("Token is not valid");
//                System.out.println("Token is not valid");
            }

        }
        filterChain.doFilter(request,response);
    }
}

