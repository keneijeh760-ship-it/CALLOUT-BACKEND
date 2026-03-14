package com.phope.realcalloutbackend.category;

import com.phope.realcalloutbackend.Shared.config.model.ApiResponse;
import com.phope.realcalloutbackend.Shared.config.tenant.TenantContext;
import com.phope.realcalloutbackend.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {


        UUID orgId = TenantContext.getTenant();

        List<Category> categories = categoryService.getAllbyOrg(orgId);

        List<CategoryResponse> response = categories.stream()
                .map(c -> CategoryResponse.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .slug(c.getSlug())
                        .description(c.getDescription())
                        .build())
                .toList();

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}