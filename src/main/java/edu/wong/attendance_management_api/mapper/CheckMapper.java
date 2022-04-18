package edu.wong.attendance_management_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.wong.attendance_management_api.entity.Check;
import edu.wong.attendance_management_api.entity.dto.CheckDateDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
public interface CheckMapper extends BaseMapper<Check> {

    @Select("SELECT  date_format( start_date, '%Y-%m-%d' ) by_date, count(*) count_num  FROM t_check GROUP BY date_format( start_date, '%Y-%m-%d' );")
    List<CheckDateDTO> selectLeaveNumByDate();
}
