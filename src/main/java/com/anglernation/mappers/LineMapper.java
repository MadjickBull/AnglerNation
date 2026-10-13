package com.anglernation.mappers;

import com.anglernation.dtos.LineDto;
import com.anglernation.dtos.CreateLineRequest;
import com.anglernation.dtos.UpdateLineRequest;
import com.anglernation.models.lines.Line;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LineMapper {

    LineMapper INSTANCE = Mappers.getMapper(LineMapper.class);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "id", ignore = true)
    Line toEntity(CreateLineRequest request);

    @Mapping(target = "owner", ignore = true)
    void updateEntityFromDto(UpdateLineRequest request, @MappingTarget Line line);

    @Mapping(target = "ownerId", source = "owner.id")
    LineDto toDto(Line line);
}