package com.anglernation.mappers;

import com.anglernation.dtos.CatchDto;
import com.anglernation.dtos.CreateCatchRequest;
import com.anglernation.dtos.UpdateCatchRequest;
import com.anglernation.models.catches.Catch;
import com.anglernation.models.locations.Location;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.lines.Line;
import com.anglernation.models.Fish.Fish;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CatchMapper {

    CatchMapper INSTANCE = Mappers.getMapper(CatchMapper.class);

    @Mapping(target = "location", ignore = true)
    @Mapping(target = "rod", ignore = true)
    @Mapping(target = "lure", ignore = true)
    @Mapping(target = "line", ignore = true)
    @Mapping(target = "fish", ignore = true)
    @Mapping(target = "angler", ignore = true)
    @Mapping(target = "id", ignore = true)
    Catch toEntity(CreateCatchRequest request);

    @Mapping(target = "location", ignore = true)
    @Mapping(target = "rod", ignore = true)
    @Mapping(target = "lure", ignore = true)
    @Mapping(target = "line", ignore = true)
    @Mapping(target = "fish", ignore = true)
    @Mapping(target = "angler", ignore = true)
    void updateEntityFromDto(UpdateCatchRequest request, @MappingTarget Catch catchEntity);

    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "rodId", source = "rod.id")
    @Mapping(target = "lureId", source = "lure.id")
    @Mapping(target = "lineId", source = "line.id")
    @Mapping(target = "fishId", source = "fish.id")
    @Mapping(target = "anglerId", source = "angler.id")
    CatchDto toDto(Catch catchEntity);
}