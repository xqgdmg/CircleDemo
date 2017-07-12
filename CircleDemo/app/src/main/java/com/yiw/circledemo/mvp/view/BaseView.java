package com.yiw.circledemo.mvp.view;

import com.yiw.circledemo.bean.CircleItem;
import com.yiw.circledemo.bean.CommentConfig;
import com.yiw.circledemo.bean.CommentItem;
import com.yiw.circledemo.bean.FavortItem;

import java.util.List;

/**
 * Created by yiwei on 16/4/1.
 * View 基础
 */
public interface BaseView {
    void deleteCircleView(String circleId);
    void addFavoriteView(int circlePosition, FavortItem addItem);
    void deleteFavortView(int circlePosition, String favortId);
    void addCommentView(int circlePosition, CommentItem addItem);
    void feleteCommentView(int circlePosition, String commentId);
    void editTextBodyVisibleView(int visibility, CommentConfig commentConfig);
    void loadDataView(int loadType, List<CircleItem> datas); // 加载显示数据
}
