package com.jhipster.service.mapper;

import com.jhipster.domain.Department;
import com.jhipster.domain.Location;
import com.jhipster.service.dto.DepartmentDTO;
import com.jhipster.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    DepartmentDTO toDto(Department s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
