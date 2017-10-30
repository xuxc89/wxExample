package com.cgm.WXUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.util.StringUtils;

import com.cgm.common.constant.WXConstant;

public class WeixinUtil {
	private static final Logger log = Logger.getLogger(WeixinUtil.class);
	public static Date TOKENTIME;

	public static String WX_ACCESS_TOKEN;

	public static String getWxAccessToken() {
		return WX_ACCESS_TOKEN;
	}

	public static void setWxAccessToken(String wxAccessToken) {
		WX_ACCESS_TOKEN = wxAccessToken;
	}

	public static Date getTOKENTIME() {
		return TOKENTIME;
	}

	public static void setTOKENTIME(Date TOKENTIME) {
		WeixinUtil.TOKENTIME = TOKENTIME;
	}

	/**
	 * 根据json规定的样式在公众号上创建菜单
	 * 
	 * @author xxc
	 * @since 2017年8月4日 下午2:39:16 WeixinUtil.java TODO
	 */
	public static String createMenu(String json) {
		String url = WXConstant.MENU_CREATE_URL + getAccessToken();
		HttpUtil.post(url, json);

		return "ok";
	}

	

	/**
	 * 从变量中获得access_token 判断是否超时 超时或程序中不存在时调用微信服务器获得
	 *
	 * @return
	 */
	public static String getAccessToken() {
		String accessToken = "";
		if (!StringUtils.isEmpty(getWxAccessToken())) {
			if (new Date().getTime() - getTOKENTIME().getTime() < 1000 * 60 * 60) {
				accessToken = getWxAccessToken();
				log.info("获取TOKEN时间：--------------------------------------------" + getTOKENTIME().toString());
			} else {
				accessToken = getAccessTokenFromWeixin();
				setWxAccessToken(accessToken);
				setTOKENTIME(new Date());
				log.info(
						"重新获取TOKEN，获取TOKEN时间：--------------------------------------------" + getTOKENTIME().toString());
			}
		} else {
			accessToken = getAccessTokenFromWeixin();
			setWxAccessToken(accessToken);
			setTOKENTIME(new Date());
			log.info("重新获取TOKEN，获取TOKEN时间：--------------------------------------------" + getTOKENTIME().toString());
		}

		return accessToken;
	}

	/**
	 * 从微信服务器获的access_token
	 *
	 * @return
	 */
	private static String getAccessTokenFromWeixin() {
		String url = WXConstant.ACCESS_TOKEN_URL + "&appid=" + WXConstant.APPID + "&secret=" + WXConstant.APPSECRET;
		String result = HttpUtil.get(url);

		System.out.println("---------result-----------" + result);

		JSONObject obj = null;
		String access_token = null;
		try {
			obj = new JSONObject(result);
			access_token = obj.getString("access_token");
			// int expires = obj.getInt("expires_in");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if ((null == access_token) || "".equals(access_token)) {
			throw new RuntimeException(result);
		}

		return access_token;
	}

	/**
	 * Method description
	 *
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String getSignature(String token, String timestamp, String nonce) {
		StringBuilder signatureBuilder = new StringBuilder();

		try {
			ArrayList<String> paramList = new ArrayList<String>();

			paramList.add(token);
			paramList.add(timestamp);
			paramList.add(nonce);
			Collections.sort(paramList);

			StringBuilder stringBuilder = new StringBuilder();

			for (String param : paramList) {
				stringBuilder.append(param);
			}

			MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");

			sha1Digest.update(stringBuilder.toString().getBytes());

			byte[] digest = sha1Digest.digest();
			String shaHex = "";

			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);

				if (shaHex.length() < 2) {
					signatureBuilder.append(0);
				}

				signatureBuilder.append(shaHex);
			}
		} catch (Exception e) {
			log.error("WeixinUtil: getSignature: hit exception:" + e);
		}

		return signatureBuilder.toString();
	}

	// public static String getOpenidBycode(String code) {
	// String url = String.format(WeixinConstant.USER_INFO_ACCESS_TOKEN_URL,
	// WeixinConstant.APP_ID,
	// WeixinConstant.APP_SECRET, code);
	// System.out.println("------getOpenidBycode----url-------------------:" +
	// url);
	//
	// String result = HttpUtil.get(url);
	// JSONObject obj = new JSONObject(result);
	// String access_token = obj.getString("access_token");
	// String openid = obj.getString("openid");
	// System.out.println("------getOpenidBycode----access_token-------------------:"
	// + access_token);
	// System.out.println("------getOpenidBycode----openid-------------------:"
	// + openid);
	// if (null == access_token || "".equals(access_token)) {
	// throw new RuntimeException(result);
	// }
	// return openid;
	// }

	/**
	 * Method description
	 */
	@Test
	public void getToken() {
		String asstoken = getAccessToken();

		System.out.println(asstoken);
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
