package com.yiw.circledemo.mvp.manager;

import com.yiw.circledemo.bean.CircleItem;
import com.yiw.circledemo.bean.CommentConfig;
import com.yiw.circledemo.bean.CommentItem;
import com.yiw.circledemo.bean.FavortItem;
import com.yiw.circledemo.mvp.contract.BaseView;
import com.yiw.circledemo.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * View extends BaseView
 * Presenter extends BasePresenter
 * 这种写法真是。。。。怎么想的
 * 只有 VIew Presenter 没有 Model，不标准的写法
 */
public interface ViewAndPresenter {

    interface View extends BaseView {
        void deleteCircleView(String circleId);
        void addFavoriteView(int circlePosition, FavortItem addItem);
        void deleteFavortView(int circlePosition, String favortId);
        void addCommentView(int circlePosition, CommentItem addItem);
        void feleteCommentView(int circlePosition, String commentId);
        void editTextBodyVisibleView(int visibility, CommentConfig commentConfig);
        void loadDataView(int loadType, List<CircleItem> datas); // 加载显示数据
    }

    interface Presenter extends BasePresenter {
        void loadDataPresenter(int loadType);
        void deleteCirclePresenter(final String circleId);
        void addFavortPresenter(final int circlePosition);
        void deleteFavortPresenter(final int circlePosition, final String favortId);
        void deleteCommentPresenter(final int circlePosition, final String commentId);

    }
}
