package edu.wong.attendance_management_api.entity.dto;

import edu.wong.attendance_management_api.entity.Check;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckDTO extends Check {
    private String userName;
}
