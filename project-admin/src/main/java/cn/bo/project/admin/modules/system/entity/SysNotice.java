package cn.bo.project.admin.modules.system.entity;


import cn.bo.project.base.core.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/2/9 22:34
 * @Description 通知公告实体类
 * @PackageName cn.bo.project.admin.modules.system.entity
 **/
@Data
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long noticeId;

    /** 公告标题 */
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    private String noticeType;

    /** 公告内容 */
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    private String status;
}
