package com.jhipster.service.mapper;

import com.jhipster.domain.Country;
import com.jhipster.domain.Location;
import com.jhipster.service.dto.CountryDTO;
import com.jhipster.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    LocationDTO toDto(Location s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);
}
