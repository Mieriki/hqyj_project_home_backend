package com.mugen.inventory.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mugen.inventory.exception.UserForceLogoutException;
import com.mugen.inventory.utils.JwtUtils;
import com.mugen.inventory.utils.RestBean;
import com.mugen.inventory.utils.constant.JwtConstant;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {
    @Resource
    JwtUtils utils;

    @Resource
    StringRedisTemplate template;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt != null) {
            if (Boolean.TRUE.equals(template.hasKey(JwtConstant.JWT_FORCE_LOGOUT + utils.getId(jwt)))) {
                template.delete(JwtConstant.JWT_FORCE_LOGOUT + utils.getId(jwt));
                utils.invalidataJwt(authorization);
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(RestBean.failure(603, "该账号已被强制下线，请重新登录").asJsonString());
                return;
            }
            UserDetails user = utils.toUser(jwt);
            UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            request.setAttribute("id", utils.getId(jwt));
        }
        filterChain.doFilter(request, response);
    }
}
