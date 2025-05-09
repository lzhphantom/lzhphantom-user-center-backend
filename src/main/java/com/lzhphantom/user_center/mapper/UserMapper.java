package com.lzhphantom.user_center.mapper;

import com.lzhphantom.user_center.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lzhphantom
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-12-06 17:14:55
* @Entity com.lzhphantom.user_center.model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




