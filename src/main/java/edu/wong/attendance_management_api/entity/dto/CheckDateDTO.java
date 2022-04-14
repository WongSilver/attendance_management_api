package edu.wong.attendance_management_api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckDateDTO {
    private String byDate;
    private int countNum;
}
