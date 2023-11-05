package com.bksproject.bksproject.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
public class PurchaseUpdateRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String serial;

}
