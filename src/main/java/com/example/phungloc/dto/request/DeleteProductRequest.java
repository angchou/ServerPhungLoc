package com.example.phungloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteProductRequest {
    @NotBlank(message = "Mã chi tiết menu đang có giá trị null!")
    private String maCtMenu;
}
