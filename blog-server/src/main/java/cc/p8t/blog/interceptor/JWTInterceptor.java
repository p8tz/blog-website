package cc.p8t.blog.interceptor;

import cc.p8t.blog.utils.JWTUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/27 19:25
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String, Object> ret = new HashMap<>();
        try {
            JWTUtil.verify(token);
            return true;
        } catch (JWTDecodeException e) {
            ret.put("msg", "token被篡改");
        } catch (AlgorithmMismatchException e) {
            ret.put("msg", "算法不匹配");
        } catch (SignatureVerificationException e) {
            ret.put("msg", "私钥不匹配");
        } catch (TokenExpiredException e) {
            ret.put("msg", "token过期");
        } catch (Exception e) {
            ret.put("msg", "token无效");
        }
        ret.put("status", 404);
        String json = new ObjectMapper().writeValueAsString(ret);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
