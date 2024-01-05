package com.zq.u8Patch.service;

import com.zq.u8Patch.entity.SqlPatch;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SqlLogHelper {
    public List<SqlPatch> index() {
        ArrayList<SqlPatch> lists = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("D:\\learn\\javas\\update-u8\\sql\\src\\main\\resources\\updateLog\\log_999.log");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            int i = 0;
            while (null != (line = bufferedReader.readLine())) {
                System.out.println(line);
                if (0 != line.indexOf('-')) {
                    SqlPatch item = parseToSqlPatch(line, ++i);
                    lists.add(item);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }

    private SqlPatch parseToSqlPatch(String line, int sort) {
        SqlPatch sqlPatch = new SqlPatch();
        String timeTex = line.substring(0, 19);
        Date runAt = null;
        try {
            runAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeTex);
            int start = line.lastIndexOf('\\');
            int end = line.lastIndexOf('.');
            String title = line.substring(start + 1, end);
            sqlPatch.setRunAt(runAt)
                    .setTitle(title)
                    .setSort(sort)
            ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sqlPatch;
    }


}
