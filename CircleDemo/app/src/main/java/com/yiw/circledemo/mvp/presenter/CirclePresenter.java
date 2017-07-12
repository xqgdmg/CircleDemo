package com.yiw.circledemo.mvp.presenter;

import android.view.View;

import com.yiw.circledemo.bean.CircleItem;
import com.yiw.circledemo.bean.CommentConfig;
import com.yiw.circledemo.bean.CommentItem;
import com.yiw.circledemo.bean.FavortItem;
import com.yiw.circledemo.mvp.manager.ViewAndPresenter;
import com.yiw.circledemo.mvp.modle.CircleModel;
import com.yiw.circledemo.listener.IDataRequestListener;
import com.yiw.circledemo.utils.DatasUtil;

import java.util.List;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter implements ViewAndPresenter.Presenter{
	private CircleModel circleModel;
	private ViewAndPresenter.View view;

	public CirclePresenter(ViewAndPresenter.View view){
		circleModel = new CircleModel();
		this.view = view;
	}

	public void loadDataPresenter(int loadType){

        List<CircleItem> datas = DatasUtil.createCircleDatas();
        if(view!=null){
            view.loadDataView(loadType, datas); // 调用 View 的方法，具体方法由 view 实现
        }
	}


	/**
	 * 
	* @Title: deleteCirclePresenter
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCirclePresenter(final String circleId){
		circleModel.deleteCircleModel(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.deleteCircleView(circleId);
                }
			}
		});
	}
	/**
	 * 
	* @Title: addFavortPresenter
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavortPresenter(final int circlePosition){
		circleModel.addFavortModel(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				FavortItem item = DatasUtil.createCurUserFavortItem();
                if(view !=null ){
                    view.addFavoriteView(circlePosition, item);
                }

			}
		});
	}
	/**
	 * 
	* @Title: deleteFavortPresenter
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavortPresenter(final int circlePosition, final String favortId){
		circleModel.deleteFavortModel(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view !=null ){
                    view.deleteFavortView(circlePosition, favortId);
                }
			}
		});
	}
	
	/**
	 * 
	* @Title: addCommentModel
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		circleModel.addCommentModel(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem newItem = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					newItem = DatasUtil.createPublicComment(content);
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					newItem = DatasUtil.createReplyComment(config.replyUser, content);
				}
                if(view!=null){
                    view.addCommentView(config.circlePosition, newItem);
                }
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteCommentPresenter
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCommentPresenter(final int circlePosition, final String commentId){
		circleModel.deleteCommentModel(new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.feleteCommentView(circlePosition, commentId);
                }
			}
			
		});
	}

	/**
	 *
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
        if(view != null){
            view.editTextBodyVisibleView(View.VISIBLE, commentConfig);
        }
	}


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}
