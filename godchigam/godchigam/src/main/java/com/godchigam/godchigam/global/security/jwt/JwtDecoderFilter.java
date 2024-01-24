package com.godchigam.godchigam.global.security.jwt;

import com.godchigam.godchigam.domain.auth.entity.User;
import com.godchigam.godchigam.domain.auth.repository.UserRepository;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import com.godchigam.godchigam.global.security.UserAuthentication;
import com.godchigam.godchigam.global.security.UserDetail;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// OncePerRequestFilter로 바꿔보기
@RequiredArgsConstructor
public class JwtDecoderFilter implements Filter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String ACCESS_TOKEN = "AccessToken";
    private final String REFRESH_TOKEN = "RefreshToken";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String receiverdToken = request.getHeader(AUTHORIZATION_HEADER);


        if(receiverdToken == null) {
            System.out.println("NO AUTHORIZATION TOKEN");
            filterChain.doFilter(request, response); // 필터를 등록해주지 않으면 이 함수 종료와 동시에 끝나게 됨
            return;
        }

        boolean isValidUser = jwtTokenProvider.isValidToken(receiverdToken);
        if(!isValidUser) {
            System.out.println("TOKEN EXPIRED ");
            filterChain.doFilter(request, response);
            return;
        }
        String email = jwtTokenProvider.getUserEmail(receiverdToken);
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("유저업슴....."));
        UserDetail userDetail = new UserDetail(user);
        SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user));

        System.out.println("HOLDER! " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        filterChain.doFilter(request, response);

        // token을 만들어야 함 -> id, pw가 정상적으로 들어와서 login 완료되면 token을 만들어주고 그걸로 응답을 해줌
        // 요청 시 마다 header에 Authorization에 value 값으로 token을 가지고 올 것임
        // 그 때 token이 넘어오면 이 token이 내가 만든 token이 맞는지 검증만 하면 됨 (지금까지 배운 RSA, HS256으로)

//        if(request.getMethod().equals("POST")) {
//            System.out.println("POST 요청됨");
//            System.out.println(receiverdToken);
//        }

//        SecurityContextHolder.getContext().setAuthentication();

//        if(authorization.equals("meenyweeny")) {
//            filterChain.doFilter(request, response); // 필터를 등록해주지 않으면 이 함수 종료와 동시에 끝나게 됨
//        } else {
//            PrintWriter outPrintWriter = response.getWriter();
//            outPrintWriter.println("인증 안 됐음");
//        }
    }
}
