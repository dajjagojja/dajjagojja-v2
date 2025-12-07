package com.multi.travel.domain.category.controller;

/*
 * Please explain the class!!!
 *
 * @filename    : CategoryController
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 7. 일요일
 */

import com.multi.travel.common.ResponseDto;
import com.multi.travel.domain.category.dto.CategoryReqDTO;
import com.multi.travel.domain.category.dto.CategoryResDTO;
import com.multi.travel.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<ResponseDto> getCategories(
            @RequestParam(required = false) String parent
    ) {
        List<CategoryResDTO> byParentCode = categoryService.findByParentCode(parent);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "카테고리 목록 조회 성공", byParentCode));
    }

    @PostMapping("/admin/category")
    public ResponseEntity<ResponseDto> createCategory(
            @RequestBody CategoryReqDTO request
    ) {

        String result = categoryService.createCategory(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "카테고리 생성 성공", result));
    }

    @PatchMapping("/admin/category/{categoryCode}")
    public ResponseEntity<ResponseDto> updateCategory(
            @PathVariable String categoryCode,
            @RequestBody CategoryReqDTO request
    ) {
        request.setCategoryCode(categoryCode);
        categoryService.updateCategory(request);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "카테고리 수정 성공", categoryCode));
    }

    @DeleteMapping("/admin/category/{categoryCode}")
    public ResponseEntity<ResponseDto> deleteCategory(
            @PathVariable String categoryCode
    ) {
        categoryService.deleteCategory(categoryCode);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "해당 카테고리 및 하위 카테고리 비활성 성공", categoryCode));
    }

}
