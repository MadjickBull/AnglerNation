package com.anglernation.mappers;

import com.anglernation.dtos.LureDto;
import com.anglernation.dtos.CreateLureRequest;
import com.anglernation.dtos.UpdateLureRequest;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LureMapper {

    LureMapper INSTANCE = Mappers.getMapper(LureMapper.class);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "id", ignore = true)
    Lure toEntity(CreateLureRequest request);

    @Mapping(target = "owner", ignore = true)
    void updateEntityFromDto(UpdateLureRequest request, @MappingTarget Lure lure);

    @Mapping(target = "ownerId", source = "owner.id")
    LureDto toDto(Lure lure);
}