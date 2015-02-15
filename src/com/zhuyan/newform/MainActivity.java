package com.zhuyan.newform;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends SherlockActivity implements OnClickListener,OnItemClickListener{

	
	private ListView listView;
	private TextView notifyText;
	private ImageView addOne;
	private ImageView addTwo;
	private Button delBtn;
	private Button recoveryBtn;
	
	private  Map<Integer, String> arrays = new HashMap<Integer, String>();
	private MyAdapter adapter;
	private SharedPreferences sharedPreferences;
	private File contentFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("表格");
		sharedPreferences = getSharedPreferences(SettingShares.NAME, 0);
		
		File file = new File(SettingShares.ROOT);
		if(!file.exists() || !file.isDirectory()){
			file.mkdirs();
		}
		
		init();
	}
	
	/**
	 * 
	 */
	private void init() {
		listView = (ListView) findViewById(R.id.list);
		addOne = (ImageView) findViewById(R.id.add_one);
		addTwo = (ImageView) findViewById(R.id.add_two);
		notifyText = (TextView) findViewById(R.id.notify);
		delBtn = (Button) findViewById(R.id.del_btn);
		recoveryBtn = (Button) findViewById(R.id.recover_btn);
		
		addOne.setClickable(true);
		addTwo.setClickable(true);
		
		addOne.setOnClickListener(this);
		addTwo.setOnClickListener(this);
		delBtn.setOnClickListener(this);
		recoveryBtn.setOnClickListener(this);
		
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, R.id.menu_settings, 1, "设置");
		menu.findItem(R.id.menu_settings).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_settings).setIcon(getResources().getDrawable(android.R.drawable.ic_menu_more));
	
//		menu.add(0, R.id.menu_check_file, 2, "导出");
//		menu.findItem(R.id.menu_check_file).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.findItem(R.id.menu_check_file).setIcon(getResources().getDrawable(android.R.drawable.ic_menu_share));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_settings){
			SettingActivity.redirectToActivity(MainActivity.this);
			return true;
		}else if(item.getItemId() == R.id.menu_check_file){
			ShowFileActivity.redirectToActivity(MainActivity.this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStop() {
//		System.out.println("stop "+contentFile.getName());
		for(int k=0;k<=3;k++){
			System.out.println(";="+k+"   save file");
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(contentFile));
				for(int i=0;i<arrays.size();i++){
					String s = arrays.get(i);
					if( s!= null && s.length() > 0){
						writer.write(s.toString(), 0, s.toString().length());
						writer.newLine();
						writer.flush();
					}
				}
				k=4;
			}catch (Exception e) {
				System.out.println("store file:e"+e);
				e.printStackTrace();
			}finally{
				try {
					if(writer != null){
						writer.flush();
						writer.close();
						writer = null;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		super.onStop();
	};
	
	@Override
	protected void onResume() {
		contentFile = new File(SettingShares.ROOT+"/"+SettingShares.getFileName(sharedPreferences));
		
		if(!contentFile.exists() || !contentFile.isFile()){
			try {
				contentFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		BufferedReader dr = null;
		arrays.clear();
		try {
			dr = new BufferedReader(new FileReader(contentFile)); 
			String key = null;
			int i =0;
			StringBuilder builder = null;
			while (true) {
				key = dr.readLine();
				if(key == null){
					break;
				}
				arrays.put(i, key);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dr != null){
				try {
					dr.close();
					dr = null;
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
//		System.out.println(contentFile.getName()+"   "+arrays.size());
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.add_one:
				doWrong();
				break;
			case R.id.add_two:
				doRight();
				break;
			case R.id.del_btn:
				arrays.remove(arrays.size() - 1);
				break;
			case R.id.recover_btn:
				arrays.clear();
				break;
			default:
				break;
		}
		adapter.notifyDataSetChanged();
		listView.setSelection(adapter.getCount()-1);
	}
	
	private void doWrong() {
		int size = arrays.size();
		String value = arrays.get(size - 1);
		ArrayList<Integer> list = new ArrayList<Integer>();
		String[] strs = value.split(",");
		for(String str:strs){
			list.add(Integer.parseInt(str));
		}
		
		if(list.size() >= 2){
			int key = list.get(0)+list.get(1);
			value = value +"," +key;
		}else{
			value = value + ","+list.get(0);
		}
		arrays.put(size, value);
	}

	private void doRight() {
		int size = arrays.size();
		String value = arrays.get(size - 1);
		ArrayList<Integer> list = new ArrayList<Integer>();
		String[] strs = value.split(",");
		for(String str:strs){
			list.add(Integer.parseInt(str));
		}
		
		if(list.size() > 2){
			StringBuilder sb = new StringBuilder();
			for(int i=2;i<list.size();i++){
				sb.append(list.get(i))
					.append(",");
			}
			value = sb.substring(0, sb.length() - 1).toString();
		}else{
			value = SettingShares.getPatch(sharedPreferences)+"";
		}
		arrays.put(size, value);
	}

	private class MyAdapter extends BaseAdapter{
//		private List<Integer[]> sliptedList = new ArrayList<Integer[]>();
		
		public MyAdapter(){
			initAdapterData();
		}
		
		private void initAdapterData() {
			if(arrays.size() <= 0){
				arrays.put(0, SettingShares.getPatch(sharedPreferences)+"");
			}
			
			int size = arrays.size();
			String value = arrays.get(size - 1);
			ArrayList<Integer> list = new ArrayList<Integer>();
			String[] strs = value.split(",");
			for(String str:strs){
				list.add(Integer.parseInt(str));
			}
			int sum = 0;
			if(list.size() >= 2){
				sum = list.get(0)+list.get(1);
			}else{
				sum = list.get(0);
			}
			notifyText.setText("两个数值和:"+sum);
		}

		@Override
		public void notifyDataSetChanged() {
			initAdapterData();
			super.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return arrays.size();
		}

		@Override
		public Object getItem(int position) {
			return arrays.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout layout = null;
			layout = (LinearLayout)MainActivity.this
					.getLayoutInflater().inflate(R.layout.item, null);
			TextView tv = (TextView) layout.findViewById(R.id.list_item_text);
			tv.setText(arrays.get(position));
			return layout;
		}
		
	}
	
}
