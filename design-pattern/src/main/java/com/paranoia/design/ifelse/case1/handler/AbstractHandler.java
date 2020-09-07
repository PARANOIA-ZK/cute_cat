package com.paranoia.design.ifelse.case1.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2020/9/6
 * @version: 1.0
 */
public abstract class AbstractHandler implements InitializingBean {

    public void methodA() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void methodB() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
