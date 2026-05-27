package com.rosariodev.medik.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginDTO {
    

    @Email
    private String email;
    @NotBlank @Size(min = 4, max = 20) 
    private String password;

}
