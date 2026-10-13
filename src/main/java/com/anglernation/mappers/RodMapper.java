package com.anglernation.mappers;

import com.anglernation.dtos.RodDto;
import com.anglernation.dtos.CreateRodRequest;
import com.anglernation.dtos.UpdateRodRequest;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RodMapper {

    RodMapper INSTANCE = Mappers.getMapper(RodMapper.class);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "id", ignore = true)
    Rod toEntity(CreateRodRequest request);

    @Mapping(target = "owner", ignore = true)
    void updateEntityFromDto(UpdateRodRequest request, @MappingTarget Rod rod);

    @Mapping(target = "ownerId", source = "owner.id")
    RodDto toDto(Rod rod);
}