package ru.innopolis.demo.configurations;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.innopolis.demo.services.UserProfileDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is used for JWT token identification
 */
@Slf4j
@Service
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.prefix}")
    private String PREFIX;
    @Value("${jwt.requestHeader}")
    private String AUTH;

    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final UserProfileDetailsService userDetailsService;

    @Autowired
    public TokenAuthenticationFilter(TokenAuthenticationProvider tokenAuthenticationProvider, UserProfileDetailsService userProfileDetailsService) {
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.userDetailsService = userProfileDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(httpServletRequest);

            if (StringUtils.hasText(jwt) && tokenAuthenticationProvider.validateToken(jwt)) {
                String username = tokenAuthenticationProvider.getUsernameFromToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (Exception e) {
            log.warn("Could not set user authentication in security context. Exception: " + e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX)) {
            return bearerToken.substring(PREFIX.length() + 1);
        }
        return null;
    }
}
