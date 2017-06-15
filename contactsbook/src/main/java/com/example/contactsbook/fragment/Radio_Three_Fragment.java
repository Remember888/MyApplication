package com.example.contactsbook.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.contactsbook.Adatper.Radio_One_Adapter;
import com.example.contactsbook.R;
import com.example.contactsbook.entry.Calllog;
import com.example.contactsbook.manager.ContactsManager;
import com.example.contactsbook.manager.SoundManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Radio_Three_Fragment extends BaseFragment {

    private RelativeLayout view;
    private View v;
    private ListView listView;
    private List<Calllog> list;
    private static String[] number = new String[] {"1","2","3","4","5","6","7","8","9","0","*","#"};
    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.radio_three_frag,container,false);
        view = (RelativeLayout) v.findViewById(R.id.actionbar_radio03);
        setPerisstion();
        titleUI();
        initListView();

        return v;
    }
   //设置权限
    private void setPerisstion() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},100);
        }else {
            initKeyBoard();
        }
    }

    //初始化键盘上的数字
    private void initKeyBoard() {
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.rl_radio03);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels / 3;
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
        for (int i = 0; i < number.length; i++) {
            final TextView text = new TextView(getActivity());
            text.setGravity(Gravity.CENTER);
            text.setId((i + 1));
            text.setText(number[i]);
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            if (i % 3 != 0) {
                params.addRule(RelativeLayout.RIGHT_OF, i);
            }
            if (i >= 3) {
                params.addRule(RelativeLayout.BELOW,(i - 2));
            }
            layout.addView(text,params);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = tv_title.getText().toString();
                    if ("拨打电话".equals(title)) {
                        tv_title.setText(text.getText().toString());
                    } else if (title.length() == 3 || title.length() == 8) {
                        tv_title.append("-");
                        tv_title.append(text.getText().toString());
                    } else {
                        tv_title.append(text.getText().toString());
                    }
                    Log.d("df", text.getId()+"");
                }
            });
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initKeyBoard();
                }

        }
    }

    //初始化ListVIew和拨号按钮
    private void initListView() {
        listView = (ListView) v.findViewById(R.id.lv_radio03);
        list = ContactsManager.getPhotoByCalllog(getActivity());
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_radio03);
        Radio_One_Adapter adapter = new Radio_One_Adapter(getActivity(), list);
        listView.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.setSoundPool(getActivity(), R.raw.a);
                String number = tv_title.getText().toString();
                Uri uri = Uri.parse("tel: "+number);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void titleUI() {
        iniFragmentTitle(R.mipmap.ic_add_icon,"拨打电话",R.mipmap.ic_backspace,view);
        ImageView iv_Left = (ImageView) view.findViewById(R.id.iv1_title);
        ImageView iv_Right = (ImageView) view.findViewById(R.id.iv2_title);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 13) {
                    s.delete(13, s.length());
                }
            }
        });
        iv_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = tv_title.getText().toString();
                if (title.equals("拨打电话")) {
                    return;
                } else if (title.length() == 1) {
                    tv_title.setText("拨打电话");
                } else if (title.length() == 5 || title.length() == 10) {
                    tv_title.setText(title.substring(0, title.length() - 2));
                } else {
                    tv_title.setText(title.substring(0,title.length() - 1));
                }
            }
        });
    }
}
