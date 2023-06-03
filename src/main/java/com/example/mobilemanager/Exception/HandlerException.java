package com.example.mobilemanager.Exception;

import com.example.mobilemanager.Constant.ErrorCodeDefs;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class HandlerException {


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .error(processFielError(fieldErrors)).build();
        return baseResponse;
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse httpMediaTypeNotAcceptableException(HttpMessageNotReadableException ex) {
        return BaseResponse.builder().success(false).error(
                BaseResponse.Error.builder().status(ErrorCodeDefs.VALIDATION_ERROR)
                        .message(ErrorCodeDefs.getErrMsg(ErrorCodeDefs.VALIDATION_ERROR)).build()
        ).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return BaseResponse.builder().success(false).error(
                BaseResponse.Error.builder().status(ErrorCodeDefs.BAD_REQUEST)
                        .message(ErrorCodeDefs.getErrMsg(ErrorCodeDefs.BAD_REQUEST)).build()
        ).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(AlertBadException.class)
    public BaseResponse alertBadException(AlertBadException ex) {
        return BaseResponse.builder().success(false).error(
                BaseResponse.Error.builder().status(ErrorCodeDefs.BAD_REQUEST)
                        .message(ex.getMsg()).build()
        ).build();
    }


    private BaseResponse.Error processFielError(List<FieldError> fieldErrors) {
        BaseResponse.Error error = BaseResponse.Error.builder().status(ErrorCodeDefs.VALIDATION_ERROR)
                .message(ErrorCodeDefs.getErrMsg(ErrorCodeDefs.VALIDATION_ERROR)).build();
        List<BaseResponse.DetailError> detailErrorList = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            BaseResponse.DetailError detailError = BaseResponse.DetailError.builder().message(fieldError.getDefaultMessage())
                    .id(fieldError.getField()).build();
            detailErrorList.add(detailError);
        }
        error.setDetailErrorList(detailErrorList);
        return error;
    }


}
//
//package com.example.mobilemanager.Exception;
//import com.example.mobilemanager.Constant.ErrorCodeDefs;
//import com.example.mobilemanager.Response.ResponseError.BaseResponse;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.http.HttpStatus.OK;
//
//
//@ControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CustomExceptionHandler {
//
//    @ResponseStatus(OK)
//    @ResponseBody
//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public BaseResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
////        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        BaseResponse response = BaseResponse.builder()
//                .success(false)
//                .error(processFieldErrors(fieldErrors))
//                .build();
//        return response;
//    }
//
//
//
//    @ResponseStatus(OK)
//    @ResponseBody
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public BaseResponse notReadableExceptionHandle(HttpMessageNotReadableException ex) {
//        return BaseResponse.builder().success(false).error(
//                        BaseResponse.Error.builder()
//                                .code(ErrorCodeDefs.VALIDATION_ERROR)
//                                .message(ErrorCodeDefs.getErrMsg(ErrorCodeDefs.VALIDATION_ERROR))
//                                .build()
//                )
//                .build();
//    }
//
//    @ResponseStatus(OK)
//    @ResponseBody
//    @ExceptionHandler(value = {Exception.class})
//    public BaseResponse methodArgumentNotValidException(Exception ex) {
//        BaseResponse response = new BaseResponse();
//        response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
//        return response;
//    }
//
//    private BaseResponse.Error processFieldErrors(List<FieldError> fieldErrors) {
//        BaseResponse.Error error = BaseResponse.Error.builder()
//                .code(ErrorCodeDefs.VALIDATION_ERROR)
//                .message(ErrorCodeDefs.getErrMsg(ErrorCodeDefs.VALIDATION_ERROR))
//                .build();
//        List<BaseResponse.ErrorDetail> errorDetailList = new ArrayList<>();
//        for (FieldError fieldError : fieldErrors) {
//            BaseResponse.ErrorDetail errorDetail = BaseResponse.ErrorDetail.builder()
//                    .id(fieldError.getField())
//                    .message(fieldError.getDefaultMessage())
//                    .build();
//            errorDetailList.add(errorDetail);
//        }
//        error.setErrorDetailList(errorDetailList);
//        return error;
//    }

//}


