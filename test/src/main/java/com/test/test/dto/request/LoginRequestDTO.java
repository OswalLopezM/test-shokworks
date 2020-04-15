package com.test.test.dto.request;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginRequestDTO {

    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    private String password;

}
