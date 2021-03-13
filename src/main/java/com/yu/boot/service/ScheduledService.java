package com.yu.boot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    /* 任务调度之定时执行
    cron表达式： 秒 分 时 月 星期几(0-7)0和7都是星期一 
    秒取值： 0 - 59
    分取值： 0 - 59
    时取值： 0 - 23
    月取值： 1 - 12
    星期几取值： 0 - 7 (0和7都是星期一)


    通配符
    *：表示匹配该域的任意值，假如在分域使用*, 即表示每分钟都会触发事件。
    ?:只能用在月和星期两个域。它也匹配域的任意值，但实际不会。因为月和星期会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, 其中最后一位只能用？，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。
    -:表示范围，例如在分钟域使用5-20，表示从5分到20分钟每分钟触发一次
    /：表示起始时间开始触发，然后每隔固定时间触发一次，例如在分钟域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次.
    ,:表示列出枚举值值。例如：在分域使用5,20，则意味着在第5和第20分钟分别触发一次
    详细信息查百度或者访问：https://baike.baidu.com/item/cron/10952601?fr=aladdin

     */

    @Scheduled(cron = "0/2 * * * * ?") //每隔2秒执行一次
    public void scheduled(){
        System.out.println("任务调度被执行了！！");
    }
}
