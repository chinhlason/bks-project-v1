package com.bksproject.bksproject.Service.Impl;

import com.bksproject.bksproject.Model.Comments;
import com.bksproject.bksproject.Model.Posts;
import com.bksproject.bksproject.Repository.CommentRepository;
import com.bksproject.bksproject.Repository.PostRepository;
import com.bksproject.bksproject.Service.IPostService;
import com.bksproject.bksproject.Service.ModelMapperService;
import com.bksproject.bksproject.advice.CustomMapper;
import com.bksproject.bksproject.payload.response.CommentResponse;
import com.bksproject.bksproject.payload.response.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapperService mapperService;

    @Autowired
    private ModelMapper mapper;

    public CustomMapper<Posts, PostResponse> customMapper = posts1 -> {
        PostResponse postResponse = mapper.map(posts1,PostResponse.class);
        Set<CommentResponse> commentResponses = convertCommentsToCommentResponses(posts1.getPost_comments());
        postResponse.setCommentPost(commentResponses);
        postResponse.setUserPost(posts1.getUser_post().getUsername());
        return postResponse;
    };

    public static Set<CommentResponse> convertCommentsToCommentResponses(Set<Comments> comments) {
        Set<CommentResponse> commentResponses = new HashSet<>();

        for (Comments comment : comments) {
            CommentResponse commentResponse = new CommentResponse(
                    comment.getId(),
                    comment.getCreateAt(),
                    comment.getContent(),
                    comment.getUserId().getUsername()
//                    "sonnvt"
            );
            commentResponses.add(commentResponse);
        }

        return commentResponses;
    }

    @Override
    public List<PostResponse> findAllPostInCurrentPage(Pageable pageable) {
        List<PostResponse> result = new ArrayList<>();
        List<Posts> posts = postRepository.findAll(pageable).getContent();
        for (Posts post : posts) {
            PostResponse postResponse = mapperService.mapObject(post,customMapper);
            result.add(postResponse);
        }
        return result;
    }

    @Override
    public Integer totalItems() {
        return (int) postRepository.count();
    }


}
