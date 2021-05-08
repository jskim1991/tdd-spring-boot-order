package io.jay.tddspringbootorderinsideout.authentication.rest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {

    private String email;
    private String password;
}
