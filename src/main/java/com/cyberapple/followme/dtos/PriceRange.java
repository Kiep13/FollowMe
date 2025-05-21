package com.cyberapple.followme.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRange {
    private Integer minPrice;
    private Integer maxPrice;

    public PriceRange(Integer minPrice, Integer maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
