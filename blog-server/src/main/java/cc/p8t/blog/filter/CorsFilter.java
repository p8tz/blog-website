package cc.p8t.blog.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 16:31
 */
@Component
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        /*
         * The Access-Control-Allow-Credentials header Indicates whether
         * or not the response to the request can be exposed when the
         * credentials flag is true. When used as part of a response
         * to a preflight request, this indicates whether or not the
         * actual request can be made using credentials. Note that
         * simple GET requests are not preflighted, and so if a request
         * is made for a resource with credentials, if this header is
         * not returned with the resource, the response is ignored by
         * the browser and not returned to web content.
         *
         */
        res.addHeader("Access-Control-Allow-Credentials", "true");

        // 允许跨域的[protocol:ip:port]
        res.addHeader("Access-Control-Allow-Origin", "http://localhost:9998");
        // 允许跨域请求方式
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        // 允许请求头包含的字段, 其中token字段是用来做JWT验证的
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN,token");
        res.addHeader("Access-Control-Max-Age", "864000");
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            response.getWriter().println("ok");
            return;
        }
        chain.doFilter(request, response);
    }
}
