package com.yiw.circledemo.mvp.presenter;

/**
 * Created by suneee on 2016/7/15.
 */
public interface BasePresenter {
    void loadDataPresenter(int loadType);
    void deleteCirclePresenter(final String circleId);
    void addFavortPresenter(final int circlePosition);
    void deleteFavortPresenter(final int circlePosition, final String favortId);
    void deleteCommentPresenter(final int circlePosition, final String commentId);
}
