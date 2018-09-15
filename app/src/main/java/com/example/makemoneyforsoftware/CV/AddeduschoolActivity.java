package com.example.makemoneyforsoftware.CV;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makemoneyforsoftware.R;


public class AddeduschoolActivity extends AppCompatActivity {
    private EditText schooltext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addeduschool);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = AddeduschoolActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        schooltext=(EditText)findViewById(R.id.school);
    }

    public void onaddschoolclick(View view){
        switch (view.getId()){
            case R.id.save:
                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                if(schooltext.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"请输入学校",Toast.LENGTH_SHORT).show();
                }else {
                    bundle.putString("school",schooltext.getText().toString());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
