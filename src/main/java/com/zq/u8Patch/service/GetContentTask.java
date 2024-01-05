package com.zq.u8Patch.service;

import com.zq.u8Patch.entity.SqlPatch;
import com.zq.u8Patch.mapper.SqlPatchMapper;

import java.io.*;

public class GetContentTask implements Runnable {
    private SqlPatch sqlPatch;
    private String name;
    //    @Autowired
    private SqlPatchMapper sqlPatchMapper;

    public GetContentTask(String name, SqlPatch sqlPatch, SqlPatchMapper sqlPatchMapper) {
        this.sqlPatchMapper = sqlPatchMapper;
        this.name = name;
        this.sqlPatch = sqlPatch;
    }

    public String getContentFromName(String name) {

        //todo  怎么办
        StringBuilder sb = new StringBuilder();
        try {
            String filename = "D:\\learn\\javas\\update-u8\\sql\\src\\main\\resources\\UFDBTMP\\" + name + ".sql";
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis, "GB2312");
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line = null;
            while (null != (line = bufferedReader.readLine())) {
                sb.append(line + System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void run() {
        String content = getContentFromName(name);
        sqlPatch.setContent(content);
        int i = sqlPatchMapper.updateById(sqlPatch);

        System.out.println(this.name + "--- run----updated rows:" + i);
    }
}
