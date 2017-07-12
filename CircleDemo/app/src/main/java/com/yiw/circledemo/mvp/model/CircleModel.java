package com.yiw.circledemo.mvp.model;

import android.os.AsyncTask;

import com.yiw.circledemo.listener.IDataRequestListener;

/**
 * 
* @ClassName: CircleModel 
* @Description: 因为逻辑简单，这里我就不写model的接口了
 *  全部都只调用一个方法 requestServer();
 */
public class CircleModel {
	
	
	public CircleModel(){
		//
	}

	public void loadDataModel(final IDataRequestListener listener){
		requestServer(listener);
	}
	
	public void deleteCircleModel(final IDataRequestListener listener) {
		requestServer(listener);
	}

	public void addFavortModel(final IDataRequestListener listener) {
		requestServer(listener);
	}

	public void deleteFavortModel(final IDataRequestListener listener) {
		requestServer(listener);
	}

	public void addCommentModel(final IDataRequestListener listener) {
		requestServer(listener);
	}

	public void deleteCommentModel(final IDataRequestListener listener) {
		requestServer(listener);
	}
	
	/**
	 * 
	* 与后台交互, 因为demo是本地数据，不做处理
	 */
	private void requestServer(final IDataRequestListener listener) {
		new AsyncTask<Object, Integer, Object>(){
			@Override
			protected Object doInBackground(Object... params) {
				//和后台交互
				return null;
			}
			
			protected void onPostExecute(Object result) {
				listener.loadSuccess(result);
			};
		}.execute();
	}
	
}
