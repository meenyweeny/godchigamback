package com.godchigam.godchigam.global.security.jwt;

import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

// OncePerRequestFilter로 바꿔보기
public class JwtDecoderFilter implements Filter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String ACCESS_TOKEN = "AccessToken";
    private final String REFRESH_TOKEN = "RefreshToken";
    private final JwtTokenProvider jwtTokenProvider;

    public JwtDecoderFilter(JwtTokenProvider tokenProvider) {
        this.jwtTokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if(authorization == null) {
            filterChain.doFilter(request, response); // 필터를 등록해주지 않으면 이 함수 종료와 동시에 끝나게 됨
//            return;
        }

        // token을 만들어야 함 -> id, pw가 정상적으로 들어와서 login 완료되면 token을 만들어주고 그걸로 응답을 해줌
        // 요청 시 마다 header에 Authorization에 value 값으로 token을 가지고 올 것임
        // 그 때 token이 넘어오면 이 token이 내가 만든 token이 맞는지 검증만 하면 됨 (지금까지 배운 RSA, HS256으로)
        if(request.getMethod().equals("POST")) {
            System.out.println("POST 요청됨");
            System.out.println(authorization);
        }

//        SecurityContextHolder.getContext().setAuthentication();

//        if(authorization.equals("meenyweeny")) {
//            filterChain.doFilter(request, response); // 필터를 등록해주지 않으면 이 함수 종료와 동시에 끝나게 됨
//        } else {
//            PrintWriter outPrintWriter = response.getWriter();
//            outPrintWriter.println("인증 안 됐음");
//        }
    }
}
