package cn.bo.project.project.quartz.service;

import cn.bo.project.project.quartz.Response.ResponseData;
import cn.bo.project.project.quartz.model.TaskInfo;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/6/23 23:10
 * @Description TaskService
 * @PackageName cn.bo.project.project.quartz.service
 **/
public interface TaskService {

    ResponseData addJob(TaskInfo taskInfo);

    List<TaskInfo> queryJobList();

    ResponseData editJob(TaskInfo taskInfo);

    void deleteJob(String jobName, String jobGroup);

    void pauseJob(String jobName, String jobGroup);

    void resumeJob(String jobName, String jobGroup);
}
