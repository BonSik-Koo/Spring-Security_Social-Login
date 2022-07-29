package socialLogin.socialLoginTest.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import socialLogin.socialLoginTest.domain.Role;
import socialLogin.socialLoginTest.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 "/login" 주소 요청이 오면 낚아 채서 로그인을 진행해준다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다.(Security Session(Session안에 특정영역))
// 해당 세션안에는 Authentication 타입객체가 들어간다.
// Authentication 은 UserDetails 타입 객체가 들어갈수 있다.
// UserDetails 안에 use(사용자)를 가지고 있는다.

@Data
public class PrincipalDetails implements UserDetails , OAuth2User {

    private User user;
    private Map<String, Object> attributes;


    //일반 로그인 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //OAuth 로그인 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes ) {
        this.user = user;
        this.attributes = attributes;
    }

    /**
     * OAuth2User 인터페이스 메소드
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    /**
     * UserDetails 인터페이스 메소드
     */
    // 해당 User의 권한을 리턴하는 곳!(role)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return String.valueOf(user.getRole());
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //우리사이트!! 1년 동안 사용하지 않으면 휴면 계정으로 바꾼다.
        // 현재 시간 - 마지막 로그인 시간 => 1년을 초기하면 return false로 바꾼다.
        // 이러한 로직을 여기 넣는다.
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
