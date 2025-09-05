package com.example.TelConnect.security;

import com.example.TelConnect.DTO.UserSessionInfo;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomCustomerDetailsService customerDetailsService;
    private final ActiveUserStore activeUserStore;
    private final BlacklistJwt blacklistJwt;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider, CustomCustomerDetailsService customerDetailsService, ActiveUserStore activeUserStore, BlacklistJwt blacklistJwt){
        this.jwtTokenProvider=jwtTokenProvider;
        this.customerDetailsService=customerDetailsService;
        this.activeUserStore=activeUserStore;
        this.blacklistJwt = blacklistJwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {
        try {
            String token = extractToken(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String name = jwtTokenProvider.getUserName(token);
                UserDetails userDetails = customerDetailsService.loadUserByUsername(name);
                if(blacklistJwt.isBlacklisted(token)) {
                    activeUserStore.removeUser(userDetails.getUsername());
                    throw new ExpiredJwtException(null, null, "Token has expired");
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                request.setAttribute("userName", userDetails.getUsername());
                request.setAttribute("Role", userDetails.getAuthorities());

                activeUserStore.addUser(userDetails.getUsername(), new UserSessionInfo(userDetails.getUsername(),jwtTokenProvider.issuedAt(token), jwtTokenProvider.getExpiry(token), userDetails.getAuthorities()));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request,response);
    }

    private String extractToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
