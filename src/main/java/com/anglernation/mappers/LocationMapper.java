package com.anglernation.mappers;

import com.anglernation.dtos.LocationDto;
import com.anglernation.dtos.CreateLocationRequest;
import com.anglernation.dtos.UpdateLocationRequest;
import com.anglernation.models.locations.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(target = "id", ignore = true)
    Location toEntity(CreateLocationRequest request);

    void updateEntityFromDto(UpdateLocationRequest request, @MappingTarget Location location);

    LocationDto toDto(Location location);
}