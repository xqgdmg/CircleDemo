package com.yiw.circledemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yiw.circledemo.R;
import com.yiw.circledemo.adapter.CircleAdapter;
import com.yiw.circledemo.bean.CircleItem;
import com.yiw.circledemo.bean.CommentConfig;
import com.yiw.circledemo.bean.CommentItem;
import com.yiw.circledemo.bean.FavortItem;
import com.yiw.circledemo.mvp.presenter.CirclePresenter;
import com.yiw.circledemo.mvp.view.BaseView;
import com.yiw.circledemo.utils.CommonUtils;
import com.yiw.circledemo.widgets.DivItemDecoration;
import com.yiw.circledemo.widgets.TitleBar;
import com.yiw.circledemo.widgets.dialog.UpLoadDialog;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 朋友圈
 */
public class MainActivity extends ImageActivity implements BaseView, EasyPermissions.PermissionCallbacks {

    protected static final String TAG = MainActivity.class.getSimpleName();
    private CircleAdapter circleAdapter;
    private LinearLayout llEditComment;
    private EditText editText;
    private ImageView sendIv;
    private CirclePresenter presenter;
    private CommentConfig commentConfig;
    private SuperRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TitleBar titleBar;
    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private UpLoadDialog uploadDialog;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    String videoFile;
    String[] thum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new CirclePresenter(this);

        initView();

        initListener();

        initPermission();

        //实现自动下拉刷新功能
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作
            }
        });
    }

    private void initListener() {
        // 点击朋友圈，隐藏评论布局
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (llEditComment.getVisibility() == View.VISIBLE) {
                    editTextBodyVisibleView(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        // 下拉刷新，加载数据
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // View 中使用 Presenter 调用 Model
                        presenter.loadDataPresenter(TYPE_PULLREFRESH);
                    }
                }, 2000);
            }
        };
        recyclerView.setRefreshListener(refreshListener);

        // 滑动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(MainActivity.this).resumeRequests(); // 继续加载数据
                } else {
                    Glide.with(MainActivity.this).pauseRequests(); // 暂停加载数据
                }
            }
        });

        // 发表评论
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    //发布评论
                    String content = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(MainActivity.this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.addComment(content, commentConfig);
                }
                editTextBodyVisibleView(View.GONE, null);
            }
        });

        // 点击 发布视频
        TextView textView = (TextView) titleBar.addAction(new TitleBar.TextAction("发布视频") {
            @Override
            public void performAction(View view) {
                // 调起 拍摄界面

            }
        });
        textView.setTextColor(getResources().getColor(R.color.white));
    }


    private void initPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
        } else {
            EasyPermissions.requestPermissions(this, "因为功能需要，需要使用相关权限，请允许", 100, perms);
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.recycle();
        }
        super.onDestroy();
    }

    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initView() {

        initTitle();
        uploadDialog = new UpLoadDialog(this);

        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, true));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        circleAdapter = new CircleAdapter(this);
        circleAdapter.setCirclePresenter(presenter); // adapter 使用 presenter 处理 Model
        recyclerView.setAdapter(circleAdapter);

        // 显示隐藏评论框
        llEditComment = (LinearLayout) findViewById(R.id.llEditComment);
        editText = (EditText) findViewById(R.id.circleEt);
        sendIv = (ImageView) findViewById(R.id.sendIv);
    }

    private void initTitle() {
        titleBar = (TitleBar) findViewById(R.id.main_title_bar);
        titleBar.setTitle("朋友圈");
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.title_bg));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (llEditComment != null && llEditComment.getVisibility() == View.VISIBLE) {
                editTextBodyVisibleView(View.GONE, null);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * 删除动态
     */
    @Override
    public void deleteCircleView(String circleId) {
        List<CircleItem> circleItems = circleAdapter.getDatas();
        for (int i = 0; i < circleItems.size(); i++) {
            if (circleId.equals(circleItems.get(i).getId())) {
                circleItems.remove(i);
                circleAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /*
     * 添加关注
     */
    @Override
    public void addFavoriteView(int circlePosition, FavortItem addItem) {
        if (addItem != null) {
            CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
            item.getFavorters().add(addItem);
            circleAdapter.notifyDataSetChanged();
        }
    }

    /*
     * 取消关注
     */
    @Override
    public void deleteFavortView(int circlePosition, String favortId) {
        CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
        List<FavortItem> items = item.getFavorters();
        for (int i = 0; i < items.size(); i++) {
            if (favortId.equals(items.get(i).getId())) {
                items.remove(i);
                circleAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /*
     * 评论
     */
    @Override
    public void addCommentView(int circlePosition, CommentItem addItem) {
        if (addItem != null) {
            CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
            item.getComments().add(addItem);
            circleAdapter.notifyDataSetChanged();
        }
        //清空评论文本
        editText.setText("");
    }

    /*
     * 删除评论
     */
    @Override
    public void feleteCommentView(int circlePosition, String commentId) {
        CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
        List<CommentItem> items = item.getComments();
        for (int i = 0; i < items.size(); i++) {
            if (commentId.equals(items.get(i).getId())) {
                items.remove(i);
                circleAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /*
     * 更新评论框显示隐藏
     */
    @Override
    public void editTextBodyVisibleView(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        llEditComment.setVisibility(visibility);

        if (View.VISIBLE == visibility) {
            editText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput(editText.getContext(), editText);

        } else if (View.GONE == visibility) {
            //隐藏键盘
            CommonUtils.hideSoftInput(editText.getContext(), editText);
        }
    }

    /*
     * 加载数据
     * 与显示相关，所以是View层调用
     */
    @Override
    public void loadDataView(int loadType, List<CircleItem> datas) {
        if (loadType == TYPE_PULLREFRESH) {
            recyclerView.setRefreshing(false);
            circleAdapter.setDatas(datas);
        } else if (loadType == TYPE_UPLOADREFRESH) {
            circleAdapter.getDatas().addAll(datas);
        }
        circleAdapter.notifyDataSetChanged();

        if (circleAdapter.getDatas().size() < 45 + CircleAdapter.HEADVIEW_SIZE) {
            recyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.loadDataPresenter(TYPE_UPLOADREFRESH);
                        }
                    }, 2000);

                }
            }, 1);
        } else {
            recyclerView.removeMoreListener(); // mOnMoreListener = null; 这个有必要？
            recyclerView.hideMoreProgress();
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "您拒绝了相关权限，可能会导致相关功能不可用", Toast.LENGTH_LONG).show();
    }
}
