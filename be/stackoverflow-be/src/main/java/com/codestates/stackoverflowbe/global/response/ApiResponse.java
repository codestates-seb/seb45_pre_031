package com.codestates.stackoverflowbe.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApiResponse {

    @Schema(example = "200")
    private int status;

    @Schema(example = "7eleven")
    private String message;
}
