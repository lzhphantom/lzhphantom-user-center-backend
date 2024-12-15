package com.lzhphantom.user_center.mapper;

import com.lzhphantom.user_center.model.domain.UserTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzhphantom.user_center.model.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author lzhphantom
*/
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

}




