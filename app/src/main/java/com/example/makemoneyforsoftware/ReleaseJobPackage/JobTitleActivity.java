package com.example.makemoneyforsoftware.ReleaseJobPackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makemoneyforsoftware.R;

public class JobTitleActivity extends AppCompatActivity {

    private EditText title;
    private Bundle bundle;
    private Intent intent;
    private View.OnClickListener mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.title_back:
                    new AlertDialog.Builder(JobTitleActivity.this)
                            .setMessage("是否保存编辑?")
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    bundle.clear();
                                    bundle.putString("title",title.getText().toString());
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                    break;
                case R.id.title_save:
                    bundle.clear();
                    bundle.putString("title",title.getText().toString());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_title);
        intent=getIntent();
        bundle=intent.getExtras();
        String title_text=bundle.getString("title");
        ImageView back=(ImageView)findViewById(R.id.title_back);
        Button save=(Button)findViewById(R.id.title_save);
        back.setOnClickListener(mylistener);
        save.setOnClickListener(mylistener);
        final TextView num=(TextView)findViewById(R.id.title_num);
        title=(EditText)findViewById(R.id.title_edit);
        title.setText(title_text);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content=title.getText().toString();
                num.setText(content.length()+"/"+"30");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
