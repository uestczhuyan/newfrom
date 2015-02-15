package com.zhuyan.newform;

/**
 * CopyRight   2013 ZhuYan
 * @auther BLUE
 *
 * All right reserved
 *
 * Created on 2014-11-9 ÏÂÎç9:52:20
 * 
 */



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * @author zy
 *
 * Create on 2014-11-9  ÏÂÎç9:52:20
 */
public class ShowFileActivity extends SherlockActivity{

	public static final void redirectToActivity(Context context){
		Intent intent = new Intent(context,ShowFileActivity.class);
		context.startActivity(intent);
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class FileTask extends AsyncTask<String, Void, Void>{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
