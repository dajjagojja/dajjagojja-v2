package com.multi.travel.domain.category.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : CategoryCreateReqDTO
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
public class CategoryReqDTO {
    private String categoryCode;
    private String categoryName;
    private String parentCategoryCode;

    public Category toEntity(String categoryCode, Category parentCategory) {
        return Category.builder()
                .categoryCode(categoryCode)
                .categoryName(categoryName)
                .parent(parentCategory)
                .level(parentCategory.getLevel() + 1)
                .deleted(false)
                .build();
    }
}
