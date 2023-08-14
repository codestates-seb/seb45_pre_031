package com.codestates.stackoverflowbe.domain.account.mapper;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-14T13:36:23+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.14.1 (Azul Systems, Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto.Response accountToAccountResponseDto(Account account) {
        if ( account == null ) {
            return null;
        }

        List<String> roles = null;
        List<Answer> answers = null;
        Long account_id = null;
        String displayName = null;
        String email = null;
        Vote vote = null;

        List<String> list = account.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }
        List<Answer> list1 = account.getAnswers();
        if ( list1 != null ) {
            answers = new ArrayList<Answer>( list1 );
        }
        account_id = account.getAccount_id();
        displayName = account.getDisplayName();
        email = account.getEmail();
        vote = account.getVote();

        AccountDto.Response response = new AccountDto.Response( account_id, displayName, email, roles, answers, vote );

        return response;
    }
}
