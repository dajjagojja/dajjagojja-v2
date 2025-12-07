package com.multi.travel.domain.category.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : Category
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */

import com.multi.travel.domain.category.dto.CategoryReqDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_cat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "category_code", length = 10)
    private String categoryCode;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code")
    private Category parent;



    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private List<Category> children = new ArrayList<>();


    public void updateValue(CategoryReqDTO request, Category parent) {
        if (request.getCategoryName() != null) {
            this.categoryName = request.getCategoryName();
        }

        if (parent != null) {
            this.parent = parent;
            this.level = parent.getLevel() + 1;
        } else {
            this.parent = null;
            this.level = 0;
        }
    }


    public void softDelete() {
        this.deleted = true;
        for (Category child : this.children) {
            child.softDelete();
        }
    }
}
