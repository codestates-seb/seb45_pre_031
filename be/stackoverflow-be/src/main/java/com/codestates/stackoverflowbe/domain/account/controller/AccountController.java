package com.codestates.stackoverflowbe.domain.account.controller;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.MultiResponseDto;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@Tag(name = "Account", description = "계정 기능")
@Slf4j
@RestController
@Validated
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Post Account", description = "계정 생성 기능")
    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody AccountDto.Post accountPostDto) {
        AccountDto.Response accountResponseDto = accountService.createAccount(accountPostDto);

        return new ResponseEntity(new SingleResponseDto<>(HttpStatusCode.CREATED.getStatusCode(),"member created!",accountResponseDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Login Member", description = "로그인 기능")
    @GetMapping("/login")
    public String getLogin() {
        return "redirect:/login.html";
    }

    @PostMapping("/authenticate")

    @Operation(summary = "Get All Member", description = "전체 계정 조회 기능")
    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Account> accountsPage = accountService.findAccounts(page-1, size);
        List<Account> accounts = accountsPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(HttpStatusCode.OK.getStatusCode(), "get accountsPage", accounts, accountsPage)
        , HttpStatus.OK);
    }

    @GetMapping("is-admin")
    public String isAdmin() {
        return "you are a admin";
    }

}
