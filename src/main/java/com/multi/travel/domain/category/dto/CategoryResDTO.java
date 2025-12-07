package com.multi.travel.domain.category.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : CategoryResDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 7. 일요일
 */


import com.multi.travel.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResDTO {
    private String categoryCode;
    private String categoryName;
    private int level;
    private String ParentCategoryCode;

    public static CategoryResDTO from(Category category) {
        return CategoryResDTO.builder()
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .level(category.getLevel())
                .build();
    }
}
