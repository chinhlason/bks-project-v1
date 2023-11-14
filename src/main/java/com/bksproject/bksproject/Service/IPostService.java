package com.bksproject.bksproject.Service;

import com.bksproject.bksproject.payload.response.PostResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    List<PostResponse> findAllPostInCurrentPage(Pageable pageable);
    Integer totalItems();
}
