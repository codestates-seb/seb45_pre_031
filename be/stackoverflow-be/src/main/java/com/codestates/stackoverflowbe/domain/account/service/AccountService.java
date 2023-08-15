package com.codestates.stackoverflowbe.domain.account.service;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.mapper.AccountMapper;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.global.auth.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Lazy;
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

    // SecurityConfiguration와의 순환참조 문제 해결
    @Lazy
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
//        Account beSavedAccount = accountPostDto.toEntity();
//        beSavedAccount = beSavedAccount.builder().roles(roles).password(encryptedPassword).build();
        Account beSavedAccount = new Account(
                0L, // id 지정하지 않음 (AUTO_INCREMENT)
                accountPostDto.getDisplayName(),
                accountPostDto.getEmail(),
                encryptedPassword, // 암호화된 비밀번호로 등록
                roles,             // 이메일 정보에 따른 권한 등록
                null,   //답변 목록 null
                null              //투표 목록 null
        );

        Account savedAccount = accountRepository.save(beSavedAccount);

        return mapper.accountToAccountResponseDto(savedAccount);
    }

    //OAuth2.0 를 통해 가입된 회원 정보 저장 (DB에 해당 정보 존재하면 메서드 리턴하고 존재하지 않으면 저장)
    public Account createAccountOAuth2(AccountDto.Post accountPostDto) {
        Optional<Account> findAccount = accountRepository.findByEmail(accountPostDto.getEmail());
        if(findAccount.isPresent()) {
            return findAccount.get(); //이미 DB에 저장된 정보가 있다면 반환
        }
        // DB에 저장된 정보가 없다면
        List<String> roles = authorityUtils.createRoles(accountPostDto.getEmail());
        Account beSavedAccount = new Account(
                0L,
                "",
                accountPostDto.getEmail(),
                "", // 이미 암호화된 기존 비밀번호로 등록
                roles,             // 이메일 정보에 따른 권한 등록
                null,               //답변 목록 null
                null              //투표 목록 null
        );
        verifyExistsEmail(accountPostDto.getEmail());
        return accountRepository.save(beSavedAccount);
    }

    public Page<Account> findAccounts(int page, int size) {
        return accountRepository.findAll(PageRequest.of(page, size, Sort.by("accountId").descending()));
    }

    private void verifyExistsEmail(String email) {
        Optional<Account> findAccount = accountRepository.findByEmail(email);
        if(findAccount.isPresent())
            throw  new RuntimeException();
    }
}
