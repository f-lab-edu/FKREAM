package com.flab.fkream.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMsg {

    private String msg;
    private String errorCode;


    public ErrorMsg(String msg, String errorCode) {
        this.msg = msg;
        this.errorCode = errorCode;
    }
}
