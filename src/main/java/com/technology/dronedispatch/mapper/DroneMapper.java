package com.technology.dronedispatch.mapper;


import com.technology.dronedispatch.dto.request.DroneRegisterRequest;
import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.enums.DroneModel;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DroneMapper extends DefaultMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "model", qualifiedByName = "toModel")
    Drone toDrone(DroneRegisterRequest droneRegisterRequest);

    @Named("toModel")
    static DroneModel toModel(String model) {
        if (StringUtils.isBlank(model)) {
            return null;
        }
        return DroneModel.valueOf(model);
    }
}
