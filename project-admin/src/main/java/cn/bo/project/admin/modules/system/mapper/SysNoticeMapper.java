package cn.bo.project.admin.modules.system.mapper;

import cn.bo.project.admin.modules.system.entity.SysNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:12
 * @Description 通知公告mapper接口
 * @PackageName cn.bo.project.admin.modules.system.mapper
 **/
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(SysNotice notice);

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(SysNotice notice);

    /**
     * 批量删除公告
     * 
     * @param noticeId 公告ID
     * @return 结果
     */
    public int deleteNoticeById(Long noticeId);

    /**
     * 批量删除公告信息
     * 
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(Long noticeIds);
}