package com.example.api.Config;


import com.example.api.Service.Serviceimpl.JwtService;
import com.example.api.Service.Serviceimpl.UserServiceimp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InvalidClassException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceimp userServiceimp;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull    HttpServletResponse response,
            @NonNull    FilterChain filterChain) throws ServletException, IOException {
        // lay token tu request
        final String AuthHeader = request.getHeader("Authorization");
        String jwt;
        //kiem tra xem co ton tai token trong Authorization
        if(StringUtils.isEmpty(AuthHeader)||!StringUtils.startsWithIgnoreCase(AuthHeader,"Bearer"))
        {
          filterChain.doFilter(request,response);
          return;
        }
        //cat bo Bearer ra khoi chuoi token
        jwt = AuthHeader.substring(7);
        String username = jwtService.extractUsernamefromToken(jwt);
        if(!jwt.isEmpty()||jwtService.validateJwtToken(jwt)){
          //lay thong tin userdetail
         UserDetails userDetails = userServiceimp.loadUserByUsername(username);
            //dai dien thong tin xac thuc nguoi dung
            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            if(authenticationToken.isAuthenticated() ){
                //  được sử dụng để thiết lập chi tiết về quá trình xác thực của người dùng
                // , bao gồm thông tin về yêu cầu HTTP mà yêu cầu xác thực được thực hiện.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //luu thong tin da xac thuc  vao  SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }else {
                throw  new InvalidClassException("thong tin that bai");
            }

            filterChain.doFilter(request,response);


        }

    }
}
