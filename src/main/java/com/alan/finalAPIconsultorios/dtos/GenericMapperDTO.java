package com.alan.finalAPIconsultorios.dtos;

import com.alan.finalAPIconsultorios.models.User;
import org.modelmapper.ModelMapper;

public class GenericMapperDTO {
    private final ModelMapper modelMapper = new ModelMapper();

    private static GenericMapperDTO genericMapperDTO;

    private GenericMapperDTO(){}

    public static GenericMapperDTO singleInstance(){
        if(genericMapperDTO == null){
            genericMapperDTO = new GenericMapperDTO();
        }
        return genericMapperDTO;
    }

    public SimpleUserDTO mapToSimpleUserDTO(User user){
        return modelMapper.map(user,SimpleUserDTO.class);
    }




}
