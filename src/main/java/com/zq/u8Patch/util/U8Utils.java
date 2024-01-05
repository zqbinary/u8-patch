package com.zq.u8Patch.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class U8Utils {

    static public String getDoc(String urlText) throws Exception {
        StringBuilder doc = new StringBuilder();
        try {
            URL url = new URL(urlText);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "PostmanRuntime/7.29.0");
            con.setRequestProperty("Host", "www.iufida.com");

            con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            con.setRequestProperty("Accept-Encoding", "zip, deflate");
            InputStream is = con.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while (null != (line = bf.readLine())) {
                doc.append(line).append(System.getProperty("line.separator"));
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            log.error("malformed url error{}", e);
            throw new Exception("server wrong ");
        } catch (IOException e) {
            log.error("io error{}:{}", urlText, e);
            throw new Exception("server wrong ");
        }
        return doc.toString();
    }


    static public String getDocFake() {

        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader("out.html");) {
            int len = 0;
            char[] chars = new char[1024];
            while ((len = fr.read(chars)) > 0) {
                sb.append(chars, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
