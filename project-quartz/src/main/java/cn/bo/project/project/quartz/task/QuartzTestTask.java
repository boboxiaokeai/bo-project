package cn.bo.project.project.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author zhangbo
 * @Date 2020/6/23 23:10
 * @Description QuartzTestTask
 * @PackageName cn.bo.project.quartz.task
 **/
public class QuartzTestTask implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            System.out.println("QuartzTestTask开始执行,currentTime:"+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
