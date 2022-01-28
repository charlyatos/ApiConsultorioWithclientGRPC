package com.alan.finalAPIconsultorios.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DoctorGrpcDTO {
    private Long id ;
    private Long userdId;
    private float fee;
    private Integer specialtyId;
    private Integer shiftId;
    private Integer status;
}
