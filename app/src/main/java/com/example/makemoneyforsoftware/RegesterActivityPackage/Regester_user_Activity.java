package com.example.makemoneyforsoftware.RegesterActivityPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.makemoneyforsoftware.MainActivity;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.Users.JobSeeker;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Regester_user_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester_user_);
        Bmob.initialize(this,"273d85ee821227067102d46670d56565");
        final EditText tele=(EditText)findViewById(R.id.tele);
        final EditText password=(EditText)findViewById(R.id.password);
        final EditText confirm_password=(EditText)findViewById(R.id.confirm_password);
        Button regester_button=(Button)findViewById(R.id.regester_button);
        regester_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tele_number=tele.getText().toString();
                String psd=password.getText().toString();
                String cfm_psd=confirm_password.getText().toString();
                if(psd.equals("")){
                    Toast.makeText(Regester_user_Activity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(!cfm_psd.equals(psd)){
                    Toast.makeText(Regester_user_Activity.this,"请重新确认密码",Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(tele_number,psd);
                    Intent intent=new Intent(Regester_user_Activity.this, MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("tele",tele_number);
                    bundle.putInt("page",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void registerUser(final String tele, final String psd){
        JobSeeker jobSeeker=new JobSeeker();
        jobSeeker.setTele(tele);
        jobSeeker.setPsd(psd);
        jobSeeker.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(Regester_user_Activity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("msg",e.getMessage());
                }
            }
        });
    }
}
