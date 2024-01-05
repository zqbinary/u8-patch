package com.zq.u8Patch;

import com.zq.u8Patch.mapper.PatchMapper;
import com.zq.u8Patch.util.PatchTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
@ConditionalOnProperty(name = "crawl.patch.main", havingValue = "true")
@Order(1)
public class CrawlMainCommand implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CrawlMainCommand.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Autowired
    PatchMapper patchMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("run main patch command");
        ExecutorService ex = Executors.newCachedThreadPool();
        int pageSize;
        pageSize = 200;
        for (int i = 0; i <= pageSize; i++) {
            ex.submit(new PatchTask(i, patchMapper));
        }
        log.info("线程分配好了...");
        ex.shutdown();
        log.info("任务全部进入线程池");
    }
}
