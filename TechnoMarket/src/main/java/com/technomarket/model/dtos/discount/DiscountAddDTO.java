package com.technomarket.model.dtos.discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
@Setter
@Getter
@NoArgsConstructor
public class DiscountAddDTO {

    @NotBlank
    private String title;

    @NotNull
    @Max(80)
    @Min(1)
    private int discountPercentage;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;
}
