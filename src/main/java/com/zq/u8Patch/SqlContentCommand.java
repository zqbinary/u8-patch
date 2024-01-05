package com.zq.u8Patch;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zq.u8Patch.entity.SqlPatch;
import com.zq.u8Patch.mapper.SqlPatchMapper;
import com.zq.u8Patch.service.GetContentTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@ConditionalOnProperty(name = "crawl.sql-patch.main", havingValue = "true")
public class SqlContentCommand implements CommandLineRunner {
    @Autowired
    private SqlPatchMapper sqlPatchMapper;

    public static void main(String[] args) {
        SpringApplication.run(SqlContentCommand.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(SqlContentCommand.class + " start run");
        long startTime = System.nanoTime();
        QueryWrapper<SqlPatch> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", 52);
        List<SqlPatch> sqlPatches = sqlPatchMapper.selectList(wrapper);

        ExecutorService es = Executors.newFixedThreadPool(10);

        for (SqlPatch sqlPatch : sqlPatches) {
            GetContentTask task = new GetContentTask(sqlPatch.getTitle(), sqlPatch, sqlPatchMapper);
            es.execute(task);
        }
        es.shutdown();
        System.out.println("------->end:" + (System.nanoTime() - startTime));
    }
}
