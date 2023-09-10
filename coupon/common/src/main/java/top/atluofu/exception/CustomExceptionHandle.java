package top.atluofu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.atluofu.utils.JsonData;

/**
 * @ClassName: CustomExceptionHandle
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-06Day-21:10
 * @Version: 1.0
 */
//@ControllerAdvice
//@Slf4j
//public class CustomExceptionHandle {
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public JsonData Handle(Exception e) {
//
//        if (e instanceof BizException) {
//            BizException bizException = (BizException) e;
//            log.info("[业务异常]{}", e);
//            return JsonData.buildError(bizException.getCode(),bizException.getMsg());
//
//        } else {
//            log.info("[系统异常]{}", e);
//            return JsonData.buildError(304,"全局异常，未知错误");
//        }
//
//    }
//}
