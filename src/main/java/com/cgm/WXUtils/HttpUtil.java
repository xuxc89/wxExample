package com.cgm.WXUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgm.common.constant.WXConstant;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Method description
     *
     * @param url
     * @return
     */
    public static String getfile(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response;
        String imageName = String.valueOf(new Date().getTime());
        File file = new File(WXConstant.IMAGE_ROOT_PATH + "/" + imageName + ".jpg");

        logger.info("file____________________________:" + file.getPath());
        logger.info("url____________________________:" + url);

        InputStream input = null;

        try {
            response = httpclient.execute(httpGet);
            input = response.getEntity().getContent();
            FileUtils.copyInputStreamToFile(input, file);

            /*
             *       responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");
             * System.out.println("--------get---responseContent-----------" + responseContent);
             */
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(input)) {
                    input.close();
                }

                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageName + ".jpg";
    }

    /**
     * Method description
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String post(String url, String jsonStr) {
        String responseContent = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));

        CloseableHttpResponse response;

        try {
            response = httpclient.execute(httpPost);
            responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("--------post---responseContent-----------" + responseContent);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseContent;
    }

    /**
     * Method description
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        String responseContent = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response;

        try {
            response = httpclient.execute(httpGet);
            responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("--------get---responseContent-----------" + responseContent);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseContent;
    }

    @Test
    public void ListTest() {

        List<String> myList = new ArrayList<>();
        myList.add("aaaa/bbb.jpg");
        myList.add("cccc/ddd.jpg");
        myList.add("aaaa/bbb/eee.jpg");
        myList.add("aaaa/bbb/fff/fff/qqq.jpg");
        for (int i = 0; i < myList.size(); i++) {
            String[] sourceStrArray = myList.get(i).split("/");
            myList.set(i, sourceStrArray[sourceStrArray.length - 1]);
        }
        for (String s : myList) {
            System.out.println(s);

        }


    }

}

//~ Formatted by Jindent --- http://www.jindent.com
