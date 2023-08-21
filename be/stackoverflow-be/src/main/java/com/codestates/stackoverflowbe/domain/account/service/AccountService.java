package com.codestates.stackoverflowbe.domain.account.service;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.dto.AccountPageResponseDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.mapper.AccountMapper;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.global.auth.utils.CustomAuthorityUtils;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    // @Lazy : SecurityConfiguration와의 순환참조 문제 해결
    @Lazy
    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper,
                          PasswordEncoder passwordEncoder,
                          CustomAuthorityUtils authorityUtils) {
        this.accountRepository = accountRepository;
        this.mapper = accountMapper;
//        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    //POST(회원 등록) : DB에 데이터 저장
    public AccountDto.Response createAccount(AccountDto.Post accountPostDto) {
        verifyExistsEmail(accountPostDto.getEmail());
        //PostDto로 입력된 이메일 정보를 기반으로 권한 생성
        List<String> roles = authorityUtils.createRoles(accountPostDto.getEmail());

        String encryptedPassword = passwordEncoder.encode(accountPostDto.getPassword());
//        Account beSavedAccount = accountPostDto.toEntity();
//        beSavedAccount = beSavedAccount.builder().roles(roles).password(encryptedPassword).build();

        Account beSavedAccount = new Account(
                accountPostDto.getDisplayName(),
                accountPostDto.getEmail(),
                encryptedPassword, // 암호화된 비밀번호로 등록
                roles           // 권한
        );

//        Account beSavedAccount = Account.builder()
//                .displayName(accountPostDto.getDisplayName())
//                .email(accountPostDto.getEmail())
//                .password(encryptedPassword)
//                .roles(roles)
//                .build();

        Account savedAccount = accountRepository.save(beSavedAccount);

        return mapper.accountToAccountResponseDto(savedAccount);
    }

    //POST(OAuth2.0 회원 등록) : OAuth2.0 를 통해 가입된 회원 정보 저장 (DB에 해당 정보 존재하면 메서드 리턴하고 존재하지 않으면 저장)
    public AccountDto.Response createAccountOAuth2(AccountDto.Post accountPostDto) {
        Optional<Account> findAccount = accountRepository.findByEmail(accountPostDto.getEmail());
        if(findAccount.isPresent()) {
            return findAccount.get().toResponse(); //이미 DB에 저장된 정보가 있다면 반환
        }

        // DB에 저장된 정보가 없다면
        List<String> roles = authorityUtils.createRoles(accountPostDto.getEmail());
        Account beSavedAccount = new Account(
                accountPostDto.getDisplayName(),          // DisplayName null (이후 추가로 변경하는 창을 redirection 할 수 있음)
                accountPostDto.getEmail(), // 구글 이메일을 DB에 등록
                "",                //암호화된 비밀번호 빈 문자열
                roles               //권한 목록
        );

        return accountRepository.save(beSavedAccount).toResponse();
    }

    //PATCH(특정 계정 정보 수정) patchAccount : DB에서 튜플을 꺼내와 사용자가 입력한 특정 정보만 수정
//    @Transactional
    public AccountDto.Response updateAccount(AccountDto.Patch accountPatchDto, Long accountId) {
        Account findAccount = findVerifiedAccount(accountId);

        findAccount.update(
                accountPatchDto.getDisplayName(),
                accountPatchDto.getLocation(),
                accountPatchDto.getTitle(),
                accountPatchDto.getAboutMe(),
                accountPatchDto.getWebsiteLink(),
                accountPatchDto.getTwitterLink(),
                accountPatchDto.getGithubLink()
        );

        return findAccount.toResponse();
    }

    //GET(특정 계정 조회) getAccount : account_id를 컨트롤러로부터 받아 DB에서 조회한 계정 정보 반환
    @Transactional(readOnly = true) // 읽기 전용
    public AccountDto.Response findAccount(Long accountId) {
        return findVerifiedAccount(accountId).toResponse();
    }

//    @Transactional(readOnly = true) // 읽기 전용
    private Account findVerifiedAccount(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    //GET(계정 페이지 조회) getAccounts : page와 size를 바탕으로 DB에서 목록을 조회하여 페이지로 반환.
    public Page<AccountPageResponseDto> findAccounts(int page, int size) {
        Page<Account> accountPage = accountRepository.findAll(PageRequest.of(page, size, Sort.by("accountId").descending()));
        List<AccountPageResponseDto> accountPageResponseDtos = accountPage.getContent().stream()
                .map( account -> account.toPageResponseDto()).collect(Collectors.toList());
        Page<AccountPageResponseDto> accountPageResponseDtoPage = new PageImpl<AccountPageResponseDto>(accountPageResponseDtos, PageRequest.of(page, size), accountPageResponseDtos.size());
        return accountPageResponseDtoPage;
    }

    //DELETE(계정 삭제) deleteAccount : accountId를 전달받아 DB에서 자원 삭제
    public void deleteAccount(long accountId) {
        Account findAccount = findVerifiedAccount(accountId);
        accountRepository.delete(findAccount);
    }

    // findAccountEmail(계정 찾기) : 이메일이 DB에 존재하는지 여부를 판단하여 존재하지 않으면 없는 Email임을 알림.
//    public Account findAccountEmail(String email) {
//        Optional<Account> findAccount = accountRepository.findByEmail(email);
//        return findAccount.orElseThrow( () ->
//                new BusinessLogicException(ExceptionCode.EMAIL_NOT_FOUND)
//        );
//    }

    // verifyExistsEmail : 이메일 unique 속성을 판별해 이미 존재하는 회원인지 여부를 검증하여 예외 던짐.
    private void verifyExistsEmail(String email) {
        Optional<Account> findAccount = accountRepository.findByEmail(email);
        if(findAccount.isPresent())
            throw  new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
    public Account findByEmail(String email) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        return accountOptional.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)); // 해당 이메일로 찾은 Account가 없으면 null 반환
    }

    // 주석안무거나
}
