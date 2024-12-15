package com.lzhphantom.user_center.service;

import com.lzhphantom.user_center.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzhphantom.user_center.model.domain.User;
import com.lzhphantom.user_center.model.dto.TeamQuery;
import com.lzhphantom.user_center.model.request.team.*;
import com.lzhphantom.user_center.model.vo.TeamUserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lzhphantom
 */
public interface TeamService extends IService<Team> {
    /**
     * 创建队伍
     *
     * @param team      队伍信息
     * @param loginUser 当前用户
     * @return team id
     */
    long addTeam(TeamAddRequest team, final User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery 查询条件
     * @param isAdmin 是否是管理员
     * @return 队伍列表
     */
    List<TeamUserVo> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍信息
     * @param request 修改队伍信息
     * @param loginUser 登录用户
     * @return 是否成功
     */
    boolean updateTeam(TeamUpdateRequest request,User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest 加入队伍信息
     * @param loginUser 登录用户
     * @return 是否成功
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest 退出队伍信息
     * @param loginUser 登录用户
     * @return 是否成功
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 解散队伍
     * @param teamDeleteRequest 解散队伍信息
     * @param loginUser 登录用户
     * @return 是否成功
     */
    boolean deleteTeam(TeamDeleteRequest teamDeleteRequest, User loginUser);
}
