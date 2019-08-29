package com.denghuiwen.tiger.controller;

import com.denghuiwen.tiger.bo.HspBO;
import com.denghuiwen.tiger.util.ExcelUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.denghuiwen.tiger.util.JsoupUtil.getDocument;


/**
 * @author PARANOIA_ZK
 * @date 2018/4/23 11:53
 */

@RestController
public class BaiDuController {

    @GetMapping("/do")
    public String doIt() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\冠心宁查等级邓惠文(1).xls");
        List<HspBO> excelBos = ExcelUtil.readExcelToEntity(HspBO.class, file, 680);
        excelBos = excelBos.stream()
                .filter(s -> !StringUtils.isEmpty(s.getHspName()))
                .collect(Collectors.toList());
        System.out.println("预处理医院数量：" + excelBos.size());
        System.out.println("excelBos = " + excelBos.get(0));
        HspBO hspBO = excelBos.get(excelBos.size() - 1);
        System.out.println("hspBO = " + hspBO);

        List<String> fails = new ArrayList<>();

        List<HspBO> result = excelBos.stream().skip(450)
                .peek(h -> {
                    try {
                        String level = this.ceshi(h.getHspName());
                        if (StringUtils.isEmpty(level)) {
                            fails.add(h.getHspName());
                        }
                        h.setLevel(level);
                    } catch (UnsupportedEncodingException e) {
                        fails.add(h.getHspName());
                    }
                })
                .filter(re -> re.getLevel().contains("级") || re.getLevel().contains("民"))
                .collect(Collectors.toList());
        System.out.println("result = " + result);
        System.out.println("fails = " + fails);

        write(result);

        return "cute";
    }

    private void write(List<HspBO> result) throws IOException {
        File fileKK = new File("C:\\Users\\Administrator\\Desktop\\kk.txt");
        FileWriter fw = new FileWriter(fileKK, true);

        PrintWriter pw = new PrintWriter(fw);

        result.forEach(l -> {
            System.out.println(l.getHspName() + "---" + l.getLevel());
            pw.println(l.getHspName() + "---" + l.getLevel());
        });
        pw.flush();
        fw.flush();
        pw.close();
        fw.close();
    }

    @RequestMapping("/ceshi")
    public String ceshi(@RequestParam String key) throws UnsupportedEncodingException {
        Map map = new HashMap();

        System.out.println("key = " + key);
        String requestUrl = "https://www.baidu.com/s?ie=utf-8&newi=1&mod=11&isbd=1&isid=9a0ea9450002df62&wd=" + key +
                "&rsv_spt=1&rsv_iqid=0xbca8b48c0002a1bb&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=02003390_2_hao_pg&rsv_enter=1&oq=" + URLEncoder.encode(key, "UTF-8") +
                "&rsv_t=9ad6F8IPnV9z3ex5Mg5dpEdAXdOi5LzoWbNOO8D4%2Bj2BG3j5%2BnIdzYRJgds9nSWlUQLYJgmIOAM&rsv_pq=9a0ea9450002df62&rsv_sug3=11&bs=" + key +
                "&rsv_sid=&_ss=1&clist=756a0e4bd2a0c14f&hsug=&csor=2&pstg=5&_cr1=33625";
        List<String> pages = getAllPages(requestUrl);
        System.out.println("pages = " + pages.size());

        System.out.println("pages = " + pages.get(0));

        String h3AndHref = getH3AndHref(pages.get(0));

        System.out.println("h3AndHref = " + h3AndHref);

        return h3AndHref;
    }


    /**
     * 拿取搜索关键字下的所有分页
     *
     * @param url
     * @return 结果集set(去重)
     */
    public static List<String> getAllPages(String url) {
        List<String> result = new ArrayList<>(10000);
        result.add(url);
        String nextPageUrl = url;
        for (int i = 0; i < 1; i++) {
            try {
                Document parentDoc = getDocument(nextPageUrl);
                Element pageElements = parentDoc.getElementById("page");
                Element nextPage = pageElements.getElementsByClass("n").last();
                nextPageUrl = "https://www.baidu.com" + nextPage.attr("href");
                //判断是否到达了最后一页
                if (nextPageUrl.contains("page=-1")) {
                    break;
                }
                System.out.println("nextPageUrl = " + nextPageUrl);
                result.add(nextPageUrl);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return result;
    }


    /**
     * 拿取当前页下所有标题和链接
     *
     * @param url
     * @return
     */
    public static String getH3AndHref(String url) {

//        StringBuilder sb = new StringBuilder();
//        System.out.println("解析的url = " + url);
//        Document parentDoc = getDocument(url);
//        Element content_left = parentDoc.getElementById("content_left");
//        Element div = content_left.getElementsByTag("div").get(0);
//        Element hspLevel = div.getElementsByTag("span ").get(1);
//        System.out.println("hspLevel = " + hspLevel);
//
//        return hspLevel.text();

        StringBuilder sb = new StringBuilder();

        System.out.println("解析的url = " + url);
        Document parentDoc = getDocument(url);
        Element content_left = parentDoc.getElementById("content_left");
        Elements resultsInOnePage = content_left.children();
        System.out.println("resultsInOnePage.size() = " + resultsInOnePage.size());
        Element h3;
        String h3Text;
        String h3Href;
        for (Element one : resultsInOnePage) {
            try {
                h3 = one.getElementsByTag("h3").get(0);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            h3Text = h3.text();
            h3Href = h3.getElementsByTag("a").get(0).attr("href");
            sb.append(h3Text);
            System.out.println("h3Text = " + h3Text);
            System.out.println("h3Href = " + h3Href);
        }
        return sb.toString();

    }

}
