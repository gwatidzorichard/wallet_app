package io.basaltx.walletapp.dto;


import lombok.Builder;

@Builder
public record LoginResponse(String username,String token) {}
