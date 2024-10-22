package edu.du.sb1021.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {@Bean
public PasswordEncoder passwordEncoder() {
    // BCrypt 해싱 알고리즘을 사용하여 PasswordEncoder 빈을 생성합니다.
    // BCrypt는 비밀번호에 고유한 솔트를 추가하여 강력한 해싱 함수를 제공,
    // 레인보우 테이블 공격에 대해 더 안전합니다.
    return new BCryptPasswordEncoder();
}

    @Bean
    public UserDetailsService userDetailsService() {
        // 인증을 위한 사용자 세부 정보를 제공하는 UserDetailsService 빈을 생성합니다.
        // 여기서는 사용자 이름 "user1"과 비밀번호 "1234"를 가진
        // 사용자를 메모리 내에서 생성합니다. 비밀번호는 위에서 정의한
        // passwordEncoder를 사용해 인코딩됩니다.
        UserDetails user = User.withUsername("user1")
                .password(passwordEncoder().encode("1234")) // 비밀번호를 인코딩
                .roles("USER") // 이 사용자에게 "USER" 역할 부여
                .build();

        // InMemoryUserDetailsManager는 사용자 세부 정보를 메모리에 저장하는 데 사용되며,
        // 개발이나 테스트 용도로 적합합니다.
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HTTP 요청에 대한 보안 설정을 구성합니다.

        // HTTP 요청에 대한 권한 규칙을 정의합니다.
        http.authorizeHttpRequests()
                // CSS 및 JS 파일과 같은 정적 리소스에 대한 공개 접근 허용.
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                // 모든 다른 요청은 인증이 필요합니다.
                .anyRequest().authenticated();

        // 사용자에게 로그인 페이지를 제공하는 폼 기반 로그인을 활성화합니다.
        http.formLogin();

        // CSRF 보호를 비활성화합니다 (프로덕션 환경에서는 권장되지 않음).
        // 이는 개발 중이거나 무상태 API를 사용할 때 가끔 수행되지만,
        // 프로덕션에서는 이 설정에 주의해야 합니다.
        http.csrf().disable();

        // 정의된 설정으로 SecurityFilterChain 객체를 빌드합니다.
        return http.build();
    }

}
