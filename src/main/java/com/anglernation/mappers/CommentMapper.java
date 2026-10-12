package com.anglernation.mappers;

import com.anglernation.dtos.CommentDto;
import com.anglernation.dtos.CreateCommentRequest;
import com.anglernation.dtos.UpdateCommentRequest;
import com.anglernation.models.comments.Comment;
import com.anglernation.models.posts.Post;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "id", ignore = true)
    Comment toEntity(CreateCommentRequest request);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "post", ignore = true)
    void updateEntityFromDto(UpdateCommentRequest request, @MappingTarget Comment comment);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "postId", source = "post.id")
    CommentDto toDto(Comment comment);
}