package com.phope.realcalloutbackend.category;

import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public  List<Category> getAllbyOrg(UUID orgId){
        return categoryRepository.findAllByOrgId(orgId);
    }

    public Category getBySlug(UUID orgId, String slug){
         return categoryRepository.findByOrgIdAndSlug(orgId, slug)
                .orElseThrow(() -> new NotFoundException("Category", slug));


    }
}
