package com.zagbor.click.mapper;

import com.zagbor.click.dto.LinkDto;
import com.zagbor.click.model.Link;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkMapper {

    LinkDto toDto(Link link);

    Link toEntity(LinkDto linkDto);
}
