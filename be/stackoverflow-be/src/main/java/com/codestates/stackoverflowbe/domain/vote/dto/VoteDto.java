package com.codestates.stackoverflowbe.domain.vote.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VoteDto {
    @Getter
    public static class Request {
        private Long accountId;
        private Long questionId;
        private Long answerId;
        private Boolean upVote;
        private Boolean downVote;
    }

    @Getter
    public static class Response {
        private List<String> upVotes;
        private List<String> downVotes;

        @Builder
        public Response(List<String> upVotes, List<String> downVotes) {
            this.upVotes = upVotes;
            this.downVotes = downVotes;
        }
    }
}
//=======
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class VoteDto {
//    private Long voteId;
//    private Integer voteValue;
//    // ... 추가 필요한 정보들
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Response {
//        private Long voteId;
//        private Integer voteValue;
//        // ... 추가 필요한 정보들
//    }
//}
//>>>>>>> 94cfb42a830da7433659ae24ac39eb17cd9b7b6b
