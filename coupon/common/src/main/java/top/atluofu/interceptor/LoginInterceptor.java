package top.atluofu.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.model.LoginUser;
import top.atluofu.utils.CommonUtil;
import top.atluofu.utils.JWTUtils;
import top.atluofu.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginInterceptor
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-14:48
 * @Version: 1.0
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String accessToken = request.getHeader("token");
            if (accessToken == null) {
                accessToken = request.getParameter("token");
            }

            if (StringUtils.isNotBlank(accessToken)) {
                Claims claims = JWTUtils.checkJWT(accessToken);
                if (claims == null) {
                    //告诉登录过期，重新登录
                    CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGN));
                    return false;
                }
                Long id = Long.valueOf(claims.get("id").toString());
                String headImg = (String) claims.get("head_img");
                String mail = (String) claims.get("mail");
                String name = (String) claims.get("name");
                LoginUser loginUser = LoginUser.builder().id(id).name(name).mail(mail).headImg(headImg).build();
                threadLocal.set(loginUser);
                return true;
            }
        } catch (Exception e) {
            log.error("拦截器错误:{}", e);
        }
        CommonUtil.sendJsonMessage(response, JsonData.buildError(409, "token不存在，重新登录"));
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
