package com.cgm.common.constant;
/**
 * 重新整理微信常量类
 * @author xxc
 * @since 2017年8月3日 上午9:38:00
 *
 */
public class WXConstant {
	
	public static String TOKEN ="xuxc";
	
	public static String APPID ="wx10b07160e58d1288";
	
	public static String APPSECRET = "ba4fa12f8329bab762399b57272a1ed2";

    /**
     * 上传图片地址
     */
    public static String IMAGE_ROOT_PATH = "/opt/webs/renshu/webapps/static/images/wx";

    /**
     * 访问图片地址
     */
    public static String IMAGE_READ_PATH = "http://116.62.104.24:8080/static/images/wx/";
    /**
     * 创建菜单地址
     */
    public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    /**
     * 获取access_token地址
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    

    /**
     * 描述  :用户信息在session中的key<br>
     */
    public static String SessionKey_openId = "weixin.openId";
    
    public static String TEST_OPEN_ID = "ba4fa12f8329bab762399b57272a1ed2";
    
    public static Boolean IS_DEV = true;

}

