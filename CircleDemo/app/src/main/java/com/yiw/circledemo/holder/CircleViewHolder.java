package com.yiw.circledemo.holder;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiw.circledemo.R;
import com.yiw.circledemo.widgets.CommentListView;
import com.yiw.circledemo.widgets.ExpandTextView;
import com.yiw.circledemo.widgets.PraiseTextView;
import com.yiw.circledemo.widgets.SnsPopupWindow;
import com.yiw.circledemo.videolist.model.VideoLoadMvpView;
import com.yiw.circledemo.videolist.widget.TextureVideoView;

/**
 * Created by yiw on 2016/8/16.
 * ViewStub 的使用
 */
public abstract class CircleViewHolder extends RecyclerView.ViewHolder implements VideoLoadMvpView {

    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;

    public int viewType;

    public ImageView headIv;
    public TextView nameTv;
    public TextView urlTipTv;
    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /** 点赞列表*/
    public PraiseTextView praiseTextView;

    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    /*
     * 构造传入不同的的 viewType
     */
    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);
        initSubView(viewType, viewStub); // 调用子类的方法

        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        digLine = itemView.findViewById(R.id.lin_dig);

        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
        praiseTextView = (PraiseTextView) itemView.findViewById(R.id.praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);

        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

    @Override
    public TextureVideoView getVideoView() {
        return null;
    }

    @Override
    public void videoBeginning() {

    }

    @Override
    public void videoStopped() {

    }

    @Override
    public void videoPrepared(MediaPlayer player) {

    }

    @Override
    public void videoResourceReady(String videoPath) {

    }
}
