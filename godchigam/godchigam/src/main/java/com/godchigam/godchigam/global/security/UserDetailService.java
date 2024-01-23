package com.godchigam.godchigam.global.security;

import com.godchigam.godchigam.domain.auth.entity.User;
import com.godchigam.godchigam.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=====================================");
        System.out.println("UserDetailService loadUserByUsername called()");
        User userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("USER EMAIL 해다ㅣㅇ하는 사람 ㄴㄴ"));
        System.out.println("USER ENTITY -> " + userEntity);
        System.out.println("UserDetailService loadUserByUsername ENDED");
        System.out.println("=====================================");
        return new UserDetail(userEntity);
    }
}
