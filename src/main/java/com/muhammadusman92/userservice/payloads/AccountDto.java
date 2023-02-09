package com.muhammadusman92.userservice.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long userId;
    private String name;
    private String type;
    private Long balance;
    private Long withDrawLimit;

}
