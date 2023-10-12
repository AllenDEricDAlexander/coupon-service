package top.atluofu.interceptor;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: AtLuoFuUrlBlockHandler
 * @description: sentinel 自定义降级异常数据
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-12Day-20:47
 * @Version: 1.0
 */
@Component
public class AtLuoFuUrlBlockHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {

    }
}