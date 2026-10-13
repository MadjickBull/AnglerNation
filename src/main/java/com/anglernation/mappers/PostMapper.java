package com.anglernation.mappers;

import com.anglernation.dtos.PostDto;
import com.anglernation.dtos.CreatePostRequest;
import com.anglernation.dtos.UpdatePostRequest;
import com.anglernation.models.posts.Post;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post toEntity(CreatePostRequest request);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void updateEntityFromDto(UpdatePostRequest request, @MappingTarget Post post);

    @Mapping(target = "authorId", source = "author.id")
    PostDto toDto(Post post);
}