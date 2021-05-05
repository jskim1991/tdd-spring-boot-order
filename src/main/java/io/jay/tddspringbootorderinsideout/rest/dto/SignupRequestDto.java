package io.jay.tddspringbootorderinsideout.rest.dto;

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
