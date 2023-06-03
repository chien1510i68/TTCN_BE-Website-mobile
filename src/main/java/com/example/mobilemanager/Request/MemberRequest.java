package com.example.mobilemanager.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class MemberRequest {
    @NotBlank(message = "phonenumber is not null")
    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "addr is not null")
    @Pattern(regexp="\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}", message="Invalid email format")
    private String addr;
}
