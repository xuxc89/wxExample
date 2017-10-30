package com.cgm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cgm.WXUtils.WeixinUtil;
import com.cgm.common.constant.WXConstant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
@Controller
public class WXOAuth2Controller extends BaseController {
//    private final static Logger logger = LoggerFactory.getLogger(WXOAuth2Controller.class);
//    @Autowired
//    private RestTemplate        restTemplate;
//    @Autowired
//    private WebChatConfig       config;

    // 路径
    private String path;

    /**
     *
     * @title osaTest
     * @param request
     * @param response
     * @param session
     * @return String
     * @throws IOException
     */
    @RequestMapping(value = "/oauth2.do")
    public String oauth2(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String openId = null;

        if (WXConstant.IS_DEV) {
            openId = WXConstant.TEST_OPEN_ID;
        } else {
            String code = request.getParameter("code");
            // 微信端取得个人信息
            openId = getOpenidFromWX(code);
        }

        session.setAttribute(WXConstant.SessionKey_openId, openId);

        // session.setAttribute(Constant.SessionKey_UserInfo, userInfo);

        // 跳转到对应页面
        String sourceUrl = request.getParameter("sourceUrl");

//      response.sendRedirect(sourceUrl);
        response.sendRedirect("wx01001/index");

        return "wx01001.index";
    }

    /**
     * 跳转至预约页面
     * @title osaTest
     * @param request
     * @param response
     * @param session
     * @return String
     * @throws IOException
     */
    @RequestMapping(value = "/oauth2.do3")
    public String oauth2for01003(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String openId = null;

        if (WXConstant.IS_DEV) {
            openId = WXConstant.TEST_OPEN_ID;
        } else {
            String code = request.getParameter("code");
            // 微信端取得个人信息
            openId = getOpenidFromWX(code);
        }

        session.setAttribute(WXConstant.SessionKey_openId, openId);

        // 跳转到对应页面
        String sourceUrl = request.getParameter("sourceUrl");

//      response.sendRedirect(sourceUrl);
        response.sendRedirect("wx01003/index");

        return "wx01003.index";
    }

    /**
     * 跳转至个人信息页面
     * @title osaTest
     * @param request
     * @param response
     * @param session
     * @return String
     * @throws IOException
     */
    @RequestMapping(value = "/oauth2.do6")
    public String oauth2for01006(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String openId = null;

        if (WXConstant.IS_DEV) {
            openId = WXConstant.TEST_OPEN_ID;
        } else {
            String code = request.getParameter("code");
            // 微信端取得个人信息
            openId = getOpenidFromWX(code);
        }

        session.setAttribute(WXConstant.SessionKey_openId, openId);

        // 跳转到对应页面
        String sourceUrl = request.getParameter("sourceUrl");

        response.sendRedirect("wx01006/index");

        return "wx01006.index";
    }
    
    

    /**
     * Method description
     *
     *
     * @param code
     *
     * @return
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    private String getOpenidFromWX(String code) throws JsonParseException, JsonMappingException, IOException {
    	return "";
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 通过code换取网页授权access_token
//        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"
//        		+ "?appid=" + WXConstant.APPID
//                + "&secret=" + WXConstant.APPSECRET
//                + "&code=" + code 
//                + "&grant_type=authorization_code";
//        
//        String      accessTokentxt = URLStreamUtil.retriveGetMethodResult(accessTokenUrl);
//        AccessToken accessToken    = objectMapper.readValue(accessTokentxt, AccessToken.class);
//
//        // 拉取用户信息(需scope为 snsapi_userinfo)
//
//        /*
//         *       String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=" + accessToken.getAccess_token()
//         *                       + "&openid=" + accessToken.getOpenid() + "&lang=zh_CN";
//         *       String userInfoTxt = URLStreamUtil.retriveGetMethodResult(userInfoUrl);
//         *
//         *       UserInfo userInfo = objectMapper.readValue(userInfoTxt, UserInfo.class);
//         *       return userInfo;
//         */
//        return accessToken.getOpenid();
    }
    
    /**
     * Method description
     *
     * @param request
     * @param response
     * @return
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/weixinConnect")
    public String weixinConnect(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String tempSignature = WeixinUtil.getSignature(WXConstant.TOKEN, timestamp, nonce);

        if (signature.equals(tempSignature)) {
            return echostr;
        }

        return "";
    }
    
    @RequestMapping("/weixincreateMenu")
    public String weixincreateMenu(HttpServletRequest request, HttpServletResponse response){
    	String json = readFile();
        WeixinUtil.createMenu(json);

        return "ok";
    }
    private String readFile() {
		InputStream inputStream = this.getClass().getResourceAsStream("/menu_20170724.json");
		String json = null;
		try {
			json = IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return "IOException";
		}
		return json;
	}

    /**
     * Method description
     *
     * @param request
     * @param response
     * @return
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/getToken")
    public String getToken(HttpServletRequest request, HttpServletResponse response) {
        String token = WeixinUtil.getAccessToken();
        return token;
    }
}