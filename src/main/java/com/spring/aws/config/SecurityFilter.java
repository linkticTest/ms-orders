package com.spring.aws.config;

import com.spring.aws.dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Random;

public class SecurityFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    private final long loggerID = new Random().nextLong();

    @Autowired
    private RedisTemplate<String, Object> template = new RedisTemplate<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        UserDTO user = new UserDTO();
        user.setFirstName("Herman");
        user.setLastName("Fernandez");
        user.setIdNumber("70359874");
        user.setIdType("CC");
        user.setAddress("calle 3");
        user.setPhone("2111111");
        user.setCityOfResidence("Medellin");

        String requestHeader = httpServletRequest.getHeader("Authorization");
        String authToken = requestHeader.substring("Bearer ".length());
        logger.info("{}:authToken={}",loggerID,authToken);
        UserDTO usuario = (UserDTO) template.opsForValue().get(authToken);
        template.opsForValue().set("70359874", user);
        logger.info("{}:usuario1={}",loggerID, usuario);
        usuario = (UserDTO) template.opsForValue().get("85441750");
        logger.info("{}:usuario2={}",loggerID, usuario);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
