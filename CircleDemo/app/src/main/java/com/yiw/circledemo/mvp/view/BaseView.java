package com.yiw.circledemo.mvp.view;

/**
 * Created by yiwei on 16/4/1.
 * View 基础
 */
public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);
}
