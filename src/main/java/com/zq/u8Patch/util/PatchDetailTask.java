package com.zq.u8Patch.util;

import com.zq.u8Patch.entity.Patch;
import com.zq.u8Patch.mapper.PatchMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PatchDetailTask implements Runnable {
    public final String BASE_URL = "https://www.iufida.com/e/wap";
    private final Patch patch;

    PatchMapper patchMapper;


    public PatchDetailTask(Patch patch, PatchMapper patchMapper) {
        this.patch = patch;
        this.patchMapper = patchMapper;
    }

    @Override
    public void run() {
        String urlText = BASE_URL + '/' + patch.getUrl();
        log.info("start page url:{}", urlText);
        String doc;
        try {
            doc = U8Utils.getDoc(urlText);
        } catch (Exception e) {
            log.error("详情页{}获取失败{}", urlText, e);
            try {
                TimeUnit.SECONDS.sleep(2);
                doc = U8Utils.getDoc(urlText);
            } catch (Exception ex) {
                log.error("详情页{}获取失败2{}", urlText, e);
                throw new RuntimeException(ex);
            }
        }
        Document document = Jsoup.parseBodyFragment(doc);
        String dateString = null;

        try {
            Element select = document.selectFirst("body > p > b:contains(日期:)");
            dateString = select.nextSibling().toString();

            if (dateString == null) {
                log.warn("详情页没有发现时间");
            }
            if (dateString != null) {
                Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
                patch.setPublishedAt(parse);
            }
            patch.setContent(document.html());
            log.info("update patch to execute:{}", patch.getId());
            patchMapper.updateById(patch);
        } catch (ParseException e) {
            log.error("解析错误{}", e);
        } catch (Exception e) {
            log.error("详情页没有发现时间{}", e);
        }
        log.info("end page url:{}", urlText);

    }


}
