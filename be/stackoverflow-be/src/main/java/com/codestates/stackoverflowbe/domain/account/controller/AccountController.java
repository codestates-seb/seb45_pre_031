package com.codestates.stackoverflowbe.domain.account.controller;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.MultiResponseDto;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
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


@Slf4j
@RestController
@Validated
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody AccountDto.Post accountPostDto) {
        AccountDto.Response accountResponseDto = accountService.createAccount(accountPostDto);

        return new ResponseEntity(new SingleResponseDto<>(HttpStatusCode.CREATED.getStatusCode(),"member created!",accountResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public String getLogin() {
        return "redirect:/login.html";
    }

    @PostMapping("/authenticate")


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
