//package com.paranoia.webfluxreactive.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author PARANOIA_ZK
// * @date 2018/11/25 16:27
// */
//@ControllerAdvice
//@Slf4j
//public class GloableExceptionHandler {
//
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public String gloabExceptionHandler(Exception e){
//        log.error("操作失败",e);
//        return "操作失败";
//    }
//}
