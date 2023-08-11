package com.codestates.stackoverflowbe.domain.account.mapper;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto.Response accountToAccountResponseDto(Account account);
}
