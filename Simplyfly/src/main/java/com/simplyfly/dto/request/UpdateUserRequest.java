package com.simplyfly.dto.request;

import jakarta.validation.constraints.*;

public record UpdateUserRequest(
        @NotBlank(message = "Full name is required")
        String fullName,

        String phone
) {


}