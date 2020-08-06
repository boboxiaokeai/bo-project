package cn.bo.project.common.page;

import cn.bo.project.common.response.ResponseData;
import cn.bo.project.common.utils.HttpContextUtil;
import cn.bo.project.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhangbo
 * @Date 2020/8/4 18:40
 * @Description 默认分页参数
 * @PackageName cn.bo.project.Page
 **/
public class PageFactory {

    public static Page defaultPage() {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        //每页多少条数据
        String pageSize = request.getParameter("size");
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        int limit = Integer.valueOf(pageSize);

        //第几页
        String pageString = request.getParameter("page");
        if (StringUtils.isEmpty(pageString)) {
            pageString = "1";
        }
        int page = Integer.valueOf(pageString);

        return new Page(page, limit);
    }

    public static PageInfo createPageInfo(IPage page) {
        PageInfo result = new PageInfo();
        result.setCount(page.getTotal());
        result.setData(page.getRecords());
        result.setCode(ResponseData.DEFAULT_SUCCESS_CODE);
        result.setMessage(ResponseData.DEFAULT_SUCCESS_MESSAGE);
        return result;
    }
}
