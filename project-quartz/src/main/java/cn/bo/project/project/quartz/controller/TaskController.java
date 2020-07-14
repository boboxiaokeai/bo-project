package cn.bo.project.project.quartz.controller;

import cn.bo.project.project.quartz.Response.ResponseData;
import cn.bo.project.project.quartz.model.TaskInfo;
import cn.bo.project.project.quartz.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhangbo
 * @Date 2020/6/23 23:10
 * @Description 任务控制器
 * @PackageName cn.bo.project.quartz.controller
 **/
@Api(tags = "定时任务Api")
@RestController
@RequestMapping("job")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PutMapping()
    @ApiOperation("新增定时任务")
    public ResponseData addJob(@RequestBody TaskInfo taskInfo){
        return taskService.addJob(taskInfo);
    }

    @GetMapping
    @ApiOperation("定时任务列表")
    public ResponseData getJobs(String jobDescription) {
        List<TaskInfo> taskInfoList = taskService.queryJobList();
        if (taskInfoList!=null && taskInfoList.size()>0) {
            taskInfoList = taskInfoList.stream()
                    .filter((TaskInfo taskInfo) -> taskInfo.getJobDescription().contains(jobDescription))
                    .collect(Collectors.toList());
        }
        return ResponseData.success(taskInfoList);
    }

    @PostMapping
    @ApiOperation("定时任务编辑")
    public ResponseData editJob(TaskInfo taskInfo) {
        return taskService.editJob(taskInfo);
    }

    @DeleteMapping
    @ApiOperation("定时任务删除")
    public ResponseData delJob(String jobName, String jobGroup) {
        taskService.deleteJob(jobName,jobGroup);
        return ResponseData.success();
    }

    @PostMapping
    @ApiOperation("定时任务暂停")
    public ResponseData pauseJob(String jobName, String jobGroup) {
        taskService.pauseJob(jobName,jobGroup);
        return ResponseData.success();
    }

    @PostMapping
    @ApiOperation("恢复暂停的定时任务")
    public ResponseData resumeJob(String jobName, String jobGroup) {
        taskService.resumeJob(jobName,jobGroup);
        return ResponseData.success();
    }

}
