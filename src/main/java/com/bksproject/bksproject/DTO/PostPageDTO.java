package com.bksproject.bksproject.DTO;

import com.bksproject.bksproject.payload.response.PostResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class PostPageDTO {

    private int totalPage;
    private List<PostResponse> postResponseList;

    public PostPageDTO(int totalPage, List<PostResponse> postResponseList) {
        this.totalPage = totalPage;
        this.postResponseList = postResponseList;
    }
}
