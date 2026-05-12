package com.example.phungloc.annonation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@NotBlank(message = "Gọi API thiếu mã chi nhánh!")
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidMaChiNhanh {
    String message() default "Mã chi nhánh không hợp lệ!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
