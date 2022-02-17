package com.technomarket.model.dtos.discount;

import com.technomarket.model.pojos.Discount;
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
public class DiscountResponseDTO {

    @NotNull
    private int id;

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

    public DiscountResponseDTO(Discount discount) {
        this.id = discount.getId();
        this.title = discount.getTitle();
        this.discountPercentage = discount.getDiscountPercentage();
        this.startAt = discount.getStartAt();
        this.endAt = discount.getEndAt();
    }
}
