package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public abstract class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public ResponseData exception(Exception e) {
        log.error("Uncaught exception: ", e);

        if (e instanceof ObjectOptimisticLockingFailureException) {
            return new ResponseData(ErrorMessage.VERSION_INVALID);
        }

        return new ResponseData(e.getMessage());
    }
}