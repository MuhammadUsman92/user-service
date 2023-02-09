package com.muhammadusman92.userservice.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotNull(message = "name cannot be empty")
    private String name;
    @NotNull(message = "email cannot be empty")
    private String email;
    @NotNull(message = "password cannot be empty")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @JsonIgnore
    private String password;
    private AccountDto accountDto;
}
