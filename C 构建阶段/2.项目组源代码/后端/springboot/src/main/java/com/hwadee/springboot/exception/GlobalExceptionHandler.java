//package com.hwadee.springboot.exception;
//
//import com.hwadee.springboot.utils.result.DataResult;
//import com.hwadee.springboot.utils.result.code.Code;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@ControllerAdvice("com.hwadee.springboot")
//public class GlobalExceptionHandler {
//    /**
//     * 出现异常返回错误提示, 并且回滚事务
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public DataResult exceptionHandler(Exception e){
//        System.out.println("抓到异常");
//        return DataResult.errByErrCode(Code.ERROR);
//    }
//}

package com.hwadee.springboot.exception;

import com.hwadee.springboot.utils.result.DataResult;
import com.hwadee.springboot.utils.result.code.Code;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hwadee.springboot.entity.Result;

@ControllerAdvice("com.hwadee.springboot")
public class GlobalExceptionHandler {
    /**
     * 出现异常返回错误提示, 并且回滚事务
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        System.out.println("抓到异常");
        e.printStackTrace();
        Result res = new Result();
        res.setCode(0);
        res.setResult("操作有误：" + e.getMessage());
        return res;
    }
}

