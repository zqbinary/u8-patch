package com.zq.u8Patch.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zq.u8Patch.entity.Patch;
import com.zq.u8Patch.mapper.PatchMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PatchTask implements Runnable {

    private int page;
    PatchMapper patchMapper;

    public PatchTask(int page, PatchMapper patchMapper) {
        this.page = page;
        this.patchMapper = patchMapper;
    }

    @Override
    public void run() {
        String urlText = "https://www.iufida.com/e/wap/list.php?classid=122&style=0&bclassid=1&page=" + page;
        String doc = null;
        try {
            doc = U8Utils.getDoc(urlText);
        } catch (Exception e) {
            log.error("获取页面失败", e);
            throw new RuntimeException(e);
        }
        List<PatchMapper> patchList = getAndInertPatchList(doc);
        log.info("打开第{} 页，共 {} 操作", page, patchList.size());
    }


    private List<PatchMapper> getAndInertPatchList(String input) {
        String selector = "body>p>a:gt(1)";
        List<PatchMapper> patchMappers = new ArrayList<>();
        //可以引用工具包吗
        Document document = Jsoup.parseBodyFragment(input);
        Element firstLink = document.selectFirst(selector);
        Element p = null;
        if (null == firstLink) {
            return patchMappers;
        }
        p = firstLink.parent();
        Elements aList = p.select("a");
        Elements smallList;
        smallList = p.select("small");
        int itemCount = aList.size();
        for (int i = 0; i < itemCount; i++) {
            Element link = aList.get(i);
            String url = link.attr("href");
            QueryWrapper<Patch> patchQueryWrapper = Wrappers.query();
            patchQueryWrapper.eq("url", url);
            if (patchMapper.selectCount(patchQueryWrapper) > 0) {
                continue;
            }
            Element textNode = smallList.get(i);
            Patch patch = new Patch();
            patch.setTitle(link.text());
            patch.setUrl(url);
            patch.setInfo(textNode.text());
            patchMapper.insert(patch);
            patchMappers.add(patchMapper);
        }
        return patchMappers;
    }
}
