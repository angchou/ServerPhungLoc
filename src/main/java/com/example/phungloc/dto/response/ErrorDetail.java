package com.example.phungloc.dto.response;

import com.example.phungloc.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private LocalDateTime timestamp;
    private int status;
    private ErrorCode errorCode;
    private String message;
    private String path;
}