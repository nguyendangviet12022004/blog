package com.viet.blog_api.dto.exception;

import java.time.LocalDateTime;

import com.viet.blog_api.constant.ExecptionCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private String message;
    private LocalDateTime timestamp;
    private ExecptionCode code;
}
