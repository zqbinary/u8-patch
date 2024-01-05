package com.zq.u8Patch;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zq.u8Patch.entity.Patch;
import com.zq.u8Patch.mapper.PatchMapper;
import com.zq.u8Patch.util.PatchDetailTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
@ConditionalOnProperty(name = "crawl.patch.detail", havingValue = "true")
@Order(2)
public class CrawlDetailCommand implements CommandLineRunner {

    @Autowired
    private PatchMapper patchMapper;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CrawlDetailCommand.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("run patch detail command");

        QueryWrapper<Patch> wrapper = new QueryWrapper<>();
        wrapper.isNull("content");
//        wrapper.eq("id", 1);
        List<Patch> patches = patchMapper.selectList(wrapper);

        ExecutorService es = Executors.newFixedThreadPool(40);
        for (Patch patch : patches) {
            log.info("to execute:{}", patch.getId());
            es.execute(new PatchDetailTask(patch, patchMapper));
        }
        es.shutdown();
        log.info("任务分发了:{}", patches.size());
    }
}
