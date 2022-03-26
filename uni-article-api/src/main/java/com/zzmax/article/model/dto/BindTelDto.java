package com.zzmax.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BindTelDto {
    private String phone;
    private String wxOpenId;
    private String code;
}
