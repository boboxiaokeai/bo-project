package cn.bo.project.base.core.base.controller;

import cn.bo.project.base.api.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sys/common")
public class CommonController {

	@GetMapping("/403")
	public ResultBean<?> noauth()  {
		return ResultBean.error("没有权限，请联系管理员授权");
	}
}
