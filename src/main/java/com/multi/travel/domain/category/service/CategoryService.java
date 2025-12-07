package com.multi.travel.domain.category.service;

/*
 * Please explain the class!!!
 *
 * @filename    : CategoryService
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 7. 일요일
 */

import com.multi.travel.domain.category.dto.CategoryReqDTO;
import com.multi.travel.domain.category.dto.CategoryResDTO;
import com.multi.travel.domain.category.entity.Category;
import com.multi.travel.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResDTO> findByParentCode(String parent) {
        if (parent == null) {
            return categoryRepository.findByParentIsNullAndDeletedFalse().stream().map(CategoryResDTO::from).toList();
        } else {
            return categoryRepository.findByParent_CategoryCodeAndDeletedFalse(parent).stream().map(CategoryResDTO::from).toList();
        }
    }

    @Transactional
    public String createCategory(CategoryReqDTO request) {
        Category parent = categoryRepository.findById(request.getParentCategoryCode())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 카테고리를 찾을 수 없습니다. categoryCode=" + request.getParentCategoryCode()
                ));

        Category created = request.toEntity(request.getCategoryCode(), parent);
        categoryRepository.save(created);
        return created.getCategoryCode();
    }

    @Transactional
    public void updateCategory(CategoryReqDTO request) {

        Category target = categoryRepository.findById(request.getCategoryCode())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 카테고리를 찾을 수 없습니다. categoryCode=" + request.getCategoryCode()
                ));

        Category parent = null;

        if (request.getParentCategoryCode() != null) {

            if (request.getParentCategoryCode().equals(request.getCategoryCode())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 부모로 지정할 수 없습니다.");
            }

            parent = categoryRepository.findById(request.getParentCategoryCode())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "해당 카테고리를 찾을 수 없습니다. categoryCode=" + request.getParentCategoryCode()
                    ));

            if (parent.isDeleted()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제된 카테고리를 부모로 설정할 수 없습니다.");
            }

            if (isChildOf(parent, target)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자식 카테고리를 부모로 설정할 수 없습니다.");
            }
        }

        target.updateValue(request, parent);
    }


    @Transactional
    public void deleteCategory(String categoryCode) {
        Category target = categoryRepository.findById(categoryCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 카테고리를 찾을 수 없습니다. categoryCode=" + categoryCode
                ));

        target.softDelete();
    }

    private boolean isChildOf(Category parent, Category target) {
        Category p = parent;
        while (p != null) {
            if (p.equals(target)) return true; // cycle 발생
            p = p.getParent();
        }
        return false;
    }

}
