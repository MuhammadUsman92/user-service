package com.muhammadusman92.userservice.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class AccountResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timeStamp;
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("status")
    private HttpStatus status;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private String error;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private AccountDto data;
}
