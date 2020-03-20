package com.ccy.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) res;
        String token = httpServletRequest.getHeader(tokenHeader);
        String userName =  jwtTokenUtils.getUsernameFromToken(token);
        if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
            if(this.jwtTokenUtils.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                // 将权限写入本次会话
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        chain.doFilter(req,res);
        //super.doFilter(req, res, chain);
    }
}
