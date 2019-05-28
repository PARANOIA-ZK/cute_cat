package com.example.zk.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EmbeddedZkui {

    private static final String ZKUI_HOST = "http://localhost:9090";
    private static final String ZKUI_USERNAME = "admin";
    private static final String ZKUI_PASSWORD = "manager";

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();

        new Thread(()->{
            try {
                System.out.println("服务启动中...");

                checkServer();

                System.out.println("准备导入开发环境配置......");
                Map<String, Object> params = new HashMap<>();
                params.put("username", ZKUI_USERNAME);
                params.put("password", ZKUI_PASSWORD);
                params.put("action", "Sign In");

                String cookie = login( ZKUI_HOST + "/login", params);
                if (StringUtils.isNotBlank(cookie)) {
                    boolean importResult = true;
                    //importResult = importResult && importConfig(ZKUI_HOST + "/import", new File(
                    //        EmbeddedZkui.class.getResource("/").getFile(),
                    //        "../../../../zkconfig/zk_config.txt"), cookie);
                    //importResult = importResult && importConfig(ZKUI_HOST + "/import", new File(
                    //        EmbeddedZkui.class.getResource("/").getFile(),
                    //        "../../../../zkconfig/zk_config_dev.txt"), cookie);
                    if (importResult) {
                        System.out.println("\n导入配置完成~~~ 愉快的使用Zookeeper服务吧 (*^▽^*)....\n");
                    }
                    else {
                        System.err.println("\nZKUI 启动失败 (ಥ﹏ಥ) .....\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        String path = EmbeddedZkui.class.getResource("/").getFile() + "../resources/";
        String cmd = "";

        if (System.getProperty("os.name").startsWith("Windows")) {
            path = path.substring(1);
            cmd = "cmd.exe /c \"cd /d "  + path  + " & " + path + "run-zkui.bat \"";
        }
        else {
            cmd = "/bin/sh " + path + "run-zkui.sh " + path;
        }

        Process p = Runtime.getRuntime().exec(cmd);
        print(p.getInputStream(), false);
        print(p.getErrorStream(), true);
    }

    private static void print(InputStream is, boolean error) throws IOException {
        InputStreamReader esr = new InputStreamReader(is, "GBK");
        BufferedReader ebr = new BufferedReader (esr);
        String line = null;
        while ( (line = ebr.readLine ()) != null) {
            if (error) {
                System.err.println(line);
            }
            else {
                System.out.println(line);
            }
        }
    }

    private static void checkServer() {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse resp = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpPost = new HttpGet(ZKUI_HOST);
            while (true) {
                Thread.sleep(1000);
                try {
                    resp = httpclient.execute(httpPost);
                    if (resp.getStatusLine().getStatusCode() != 404) {
                        break;
                    }
                } catch (Exception e) {
                    log.error("exception inner:", e);
                }
            }
        } catch (Exception e) {
            System.out.printf("exception: ", e);
        } finally {
            IOUtils.closeQuietly(resp);
            IOUtils.closeQuietly(httpclient);
        }
    }


    private static String login(String url, Map<String, Object> params) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse resp = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> valuePairs = params.keySet()
                    .stream()
                    .map(key -> new BasicNameValuePair(key, String.valueOf(params.get(key))))
                    .collect(Collectors.toList());

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "UTF-8");
            httpPost.setEntity(formEntity);
            resp = httpclient.execute(httpPost);
            String cookie = resp.getFirstHeader("Set-Cookie").getValue().split(";")[0];
            return cookie;
        } catch (Exception e) {
            return null;
        } finally {
            IOUtils.closeQuietly(resp);
            IOUtils.closeQuietly(httpclient);
        }
    }

    private static boolean importConfig(String url, File localFile, String cookie) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse resp = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Cookie", cookie);

            System.out.println("开始导入配置: " + localFile.getAbsolutePath());

            HttpEntity httpEntity =  MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setContentType(ContentType.MULTIPART_FORM_DATA)
                    .addTextBody("scmOverwrite", "true")
                    .addBinaryBody("scmFile", localFile)
                    .build();

            httpPost.setEntity(httpEntity);
            resp = httpclient.execute(httpPost);
            System.out.println("导入成功!");
            return resp.getStatusLine().getStatusCode() != 404;
        } catch (Exception e) {
            System.err.println("导入失败!!");
            return false;
        } finally {
            IOUtils.closeQuietly(resp);
            IOUtils.closeQuietly(httpclient);
        }
    }
}
