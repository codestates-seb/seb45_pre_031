package com.codestates.stackoverflowbe.global.auth.userdetails;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.global.auth.utils.CustomAuthorityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class AccountDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final CustomAuthorityUtils authorityUtils;

    public AccountDetailService(AccountRepository accountRepository, CustomAuthorityUtils authorityUtils) {
        this.accountRepository = accountRepository;
        this.authorityUtils = authorityUtils;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmail(username);
        Account findAccount =
                optionalAccount.orElseThrow(
                        () -> new RuntimeException()); //🌟 리팩토링 필요 사용자 정의 예외로 추후 변경

        return new AccountDetails(findAccount);
    }

    private final class AccountDetails extends Account implements UserDetails {

        public AccountDetails(Account account) {
            account.builder()
                    .accountId(account.getAccountId())
                    .email(account.getEmail())
                    .password(account.getPassword())
                    .roles(account.getRoles())
                    .build();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        //🔥 코드 개선 필요
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
            return true;
        }
    }
}
