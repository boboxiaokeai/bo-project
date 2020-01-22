package cn.bo.project.base.core.model;

import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/1/3 9:31
 * @Description 部门机构model
 * @PackageName cn.bo.project.base.core.model
 **/
@Data
public class SysDepartModel {
    /**ID*/
    private String id;
    /**父机构ID*/
    private String parentId;
    /**机构/部门名称*/
    private String departName;
    /**英文名*/
    private String departNameEn;
    /**缩写*/
    private String departNameAbbr;
    /**排序*/
    private Integer departOrder;
    /**描述*/
    private Object description;
    /**机构类别 1组织机构，2岗位*/
    private String orgCategory;
    /**机构类型*/
    private String orgType;
    /**机构编码*/
    private String orgCode;
    /**手机号*/
    private String mobile;
    /**传真*/
    private String fax;
    /**地址*/
    private String address;
    /**备注*/
    private String memo;

}
