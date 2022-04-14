package edu.wong.attendance_management_api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleRightDTO implements Serializable {
    private int roleId;
    private List<Integer> rightIds;
}
