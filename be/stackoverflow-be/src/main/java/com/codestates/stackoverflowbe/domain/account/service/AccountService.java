package com.codestates.stackoverflowbe.domain.account.service;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.mapper.AccountMapper;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.global.auth.utils.CustomAuthorityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
//    private final ApplicationEventPublisher publisher;

    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    // AccountController와의 순환참조 문제 해결
    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper,
//                          ApplicationEventPublisher publisher,
                          PasswordEncoder passwordEncoder,
                          CustomAuthorityUtils authorityUtils) {
        this.accountRepository = accountRepository;
        this.mapper = accountMapper;
//        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public AccountDto.Response createAccount(AccountDto.Post accountPostDto) {
        verifyExistsEmail(accountPostDto.getEmail());
        //PostDto로 입력된 이메일 정보를 기반으로 권한 생성
        List<String> roles = authorityUtils.createRoles(accountPostDto.getEmail());

        String encryptedPassword = passwordEncoder.encode(accountPostDto.getPassword());
        Account willBeSavedAccount = new Account(
                0L, // id 지정하지 않음 (AUTO_INCREMENT)
                accountPostDto.getDisplayName(),
                accountPostDto.getEmail(),
                encryptedPassword, // 암호화된 비밀번호로 등록
                roles,             // 이메일 정보에 따른 권한 등록
                null,   //답변 목록 null
                null              //투표 목록 null
        );

        Account savedAccount = accountRepository.save(willBeSavedAccount);

        return mapper.accountToAccountResponseDto(savedAccount);
    }

    public Page<Account> findAccounts(int page, int size) {
        return accountRepository.findAll(PageRequest.of(page, size, Sort.by("account_id").descending()));
    }

    private void verifyExistsEmail(String email) {
        Optional<Account> findAccount = accountRepository.findByEmail(email);
        if(findAccount.isPresent())
            throw  new RuntimeException();
    }
}
