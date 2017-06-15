package com.example.autocompletetextview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private AutoCompleteTextView autoCompleteTextView;
    private EditText editText;
    private View progress;
    private View scorll;
    private Button button;
    private UserLoginTask mAuthTask = null;
    private String user;
    private String passage;
    private static final String[] ARR = {"13911115555:hello", "13966667777:world"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        objectLoader();
    }

    private void objectLoader() {
        if (!mayRequestContacts()) {
            return;
        }
         getLoaderManager().initLoader(1, null, this);
    }

    private void initUI() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.ac_local);
        editText = (EditText) findViewById(R.id.et_local);
        button = (Button) findViewById(R.id.btn_local);
        progress = findViewById(R.id.pb_local);
        scorll = findViewById(R.id.sv_local);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        View focus = null;
        boolean cance = false;
        user = autoCompleteTextView.getText().toString();
        passage = editText.getText().toString();
        if (TextUtils.isEmpty(user)) {
            autoCompleteTextView.setError("你所输入的账号为空");
            focus = autoCompleteTextView;
            cance = true;
        }
        if (TextUtils.isEmpty(passage) && passage.length() < 4) {
            editText.setError("你所输入的密码为空");
            focus = editText;
            cance = true;
        }
        if (cance) {
            focus.requestFocus();
        } else {
            showPergress(true);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void)null);
        }



    }

    private void showPergress(final boolean show) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            int time = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progress.animate().setDuration(time).alpha(show?1:0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
            scorll.animate().setDuration(time).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    scorll.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
        } else {
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            scorll.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(scorll, "你没有权限", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, 0);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, 0);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    objectLoader();
                }
            }
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<String> list = new ArrayList<>();
        while (data.moveToNext()) {
            list.add(data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }
        autoCompleteTextViewData(list);
    }

    private void autoCompleteTextViewData(List<String> list) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        autoCompleteTextView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class UserLoginTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                return false;
            }
            for (String arr : ARR) {
                String[] phone = arr.split(":");
                if (phone[0].equals(user)) {
                    if (phone[1].equals(passage)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else {
                editText.setError("输入错误");
                editText.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            showPergress(false);
        }
    }
}
