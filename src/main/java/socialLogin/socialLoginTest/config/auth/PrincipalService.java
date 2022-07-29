package socialLogin.socialLoginTest.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialLogin.socialLoginTest.domain.User;
import socialLogin.socialLoginTest.repository.UserRepository;

// 시큐리티에서 설정에서 LoginProcessingUrl("/login");
// "/login" 요청이 오면 자동으로 UserDetailsService 타입으로 loC 되어있는  loadUserByUsername 함수가 실행된다.!
// Authentication 객체로 만들어준다

@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    //시큐리티 session = Authentication = UserDetails
    // 여기서 리턴 된 값이 Authentication 안에 들어간다.(리턴될때 들어간다.)
    // 그리고 시큐리티 session 안에 Authentication 이 들어간다.
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findUser = userRepository.findByUsername(username);
        if(findUser!=null) {
            return new PrincipalDetails(findUser);
        }

        return null;
    }
}
