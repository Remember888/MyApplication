package com.example.day15;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.Toast;

/**访问联系人信息并呈现在listview上
 * 1)view的初始化
 * 2)permission的声明及运行配置
 * 3)Contacts数据的初始化(访问contentprovider)及页面的更新
 * */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final int REQUEST_CODE_01=100;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.初始化view
        setListView();
        //2.检测权限
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!=
                PackageManager.PERMISSION_GRANTED){
            //申请授权
            requestPermissions(new String[]{
                    Manifest.permission.READ_CONTACTS},REQUEST_CODE_01);

        }else{
        //3.加载数据
            loadAsyncContacts();
        }

    }
    /**初始化listview*/
    private void setListView(){
        //1.listview
        ListView listView= (ListView)
                findViewById(R.id.listId);
        //2.adapter
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,cursor,
                new String[]{Phone.NUMBER, Phone.DISPLAY_NAME},
                new int[]{android.R.id.text1,android.R.id.text2},
        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER); //注册观察者对象
        //3.setAdapter
        listView.setAdapter(adapter);
        //4.add listener
        listView.setOnItemClickListener(this);
    }
    /**处理权限请求结果*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if(requestCode==REQUEST_CODE_01){
          if (grantResults.length > 0
                  && grantResults[0] ==
                  PackageManager.PERMISSION_GRANTED){
              loadAsyncContacts();
          }
      };
    }
    /**处理数据加载过程*/
    private void loadAsyncContacts(){
        //主线程加载
   /*     cursor=getContentResolver().
                query(Phone.CONTENT_URI,null,null,null,null);
        //交换cursor对象，刷新页面
        adapter.swapCursor(cursor);*/

        //异步加载(借助CursorLoader对象实现)
        int id=1;//loader对象的唯一标识id
        Bundle args=new Bundle();//此对象一般用于封装数据，将数据传递给CursorLoader
        args.putParcelable("uriKey",Phone.CONTENT_URI);
        LoaderManager.LoaderCallbacks<Cursor> callback=this;

        //初始化Loader对象，此时会执行callback接口中的onCreateLoader方法
        getSupportLoaderManager().initLoader(id,args,callback);

    }
    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        Cursor cursor= (Cursor) parent.getItemAtPosition(position);
        String number=cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
        Toast.makeText(this, ""+number, Toast.LENGTH_SHORT).show();
    }

    /**此方法在初始化loader时执行，loader对象底层借助异步任务加载数据*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==1){
            //return new CursorLoader(this,Phone.CONTENT_URI,null,null,null,null);
            Uri uri= (Uri) args.getParcelable("uriKey");
            return new CursorLoader(this,uri,null,null,null,null);
        }//else if(id==2)
        return null;
    }
    /**数据加载完成时执行*/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    /**当cursorloader重置或activity销毁时会默认执行此方法*/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
       adapter.swapCursor(null);
    }
}
