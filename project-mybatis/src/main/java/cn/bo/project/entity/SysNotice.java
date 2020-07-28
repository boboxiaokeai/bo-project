package cn.bo.project.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author zhangbo
 * @Date 2020/2/9 22:34
 * @Description 通知公告实体类
 * @PackageName cn.bo.project.admin.modules.system.entity
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysNotice{
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

    private Long id;
    /** 创建人 */
    private String createBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新人 */
    private String updateBy;
    /** 更新时间 */
    private java.util.Date updateTime;
    /** 备注 */
    private String remark;
    /** 数据权限 */
    private String dataScope;
    /** 开始时间 */
    private String beginTime;
    /** 结束时间 */
    private String endTime;
}
