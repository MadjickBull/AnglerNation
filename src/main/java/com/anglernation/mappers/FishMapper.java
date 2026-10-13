package com.anglernation.mappers;

import com.anglernation.dtos.FishDto;
import com.anglernation.dtos.CreateFishRequest;
import com.anglernation.dtos.UpdateFishRequest;
import com.anglernation.models.Fish.Fish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FishMapper {

    FishMapper INSTANCE = Mappers.getMapper(FishMapper.class);

    @Mapping(target = "id", ignore = true)
    Fish toEntity(CreateFishRequest request);

    void updateEntityFromDto(UpdateFishRequest request, @MappingTarget Fish fish);

    FishDto toDto(Fish fish);
}