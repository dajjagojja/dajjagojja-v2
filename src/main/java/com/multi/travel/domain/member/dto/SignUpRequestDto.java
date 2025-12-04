package com.multi.travel.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : MemberSignUpRequestDto
 * @since : 2025. 12. 4. 목요일
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min=8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 50, message = "이름은 50자를 초과할 수 없습니다.")
    private String username;
}
