package com.codestates.stackoverflowbe.global.response;

import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApiResponse {
    @Schema(example = "OK")
    private int status;

    @Schema(example = "7eleven")
    private String message;
}
