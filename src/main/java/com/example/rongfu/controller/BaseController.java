package com.example.rongfu.controller;

import com.example.rongfu.service.ex.*;
import com.example.rongfu.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sun.rmi.runtime.Log;

/**
 * Cotroller类的基类
 */
public abstract class BaseController {
    protected static final int OK = 1000;
    protected static final int FAILED = 1001;

    /**
     * 统一处理ServiceException方法
     *
     * @param e ServiceException对象
     * @return JsonResult对象
     */
    @ExceptionHandler
    public JsonResult<Void> handleServiceException(ServiceException e) {
        JsonResult<Void> jr = new JsonResult<>(e);
        /*if (e instanceof UsernameDuplicateException) {
            jr.setState(4001);
        } else if (e instanceof UserNotFoundException) {
            jr.setState(4002);
        } else if (e instanceof PasswordNotMatchException) {
            jr.setState(4003);
        } else if (e instanceof InsertException) {
            jr.setState(5001);
        }*/
        jr.setState(FAILED);
        jr.setMessage(e.getMessage());
        System.out.println(e.getMessage());
        return jr;
    }
}
