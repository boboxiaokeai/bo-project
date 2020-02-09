package cn.bo.project.admin.modules.system.model;

import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/1/6 13:59
 * @Description 路由显示信息
 * @PackageName cn.bo.project.admin.modules.system.model
 **/
@Data
public class MetaModel {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/icons/svg
     */
    private String icon;

    public MetaModel()
    {
    }

    public MetaModel(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }
}
