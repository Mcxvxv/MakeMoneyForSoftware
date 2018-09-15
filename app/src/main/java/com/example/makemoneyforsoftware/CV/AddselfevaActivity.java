package com.example.makemoneyforsoftware.CV;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddselfevaActivity extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselfeva);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = AddselfevaActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        editText=(EditText)findViewById(R.id.auto_text);
        final TextView textView=(TextView) findViewById(R.id.count);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(editText.getText().length()+"/"+300);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onaddselfevaclick(View view){
        switch (view.getId()){
            case R.id.save:
                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                bundle.putString("selfeva",editText.getText().toString());
                intent.putExtras(bundle);
                uploadEduexp();
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    public void uploadEduexp(){
        MyApplication myApplication=(MyApplication)getApplication();
        final CurrentUser currentUser=myApplication.getLoginUser();
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        final String url="http://119.23.13.19:8080/Aliyun/QueryCVServlet";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject result=new JSONObject(jsonObject.getString("result"));
                    if(result.getString("eva").equals("")){
                        String url1="http://119.23.13.19:8080/Aliyun/EnterCVServlet";
                        StringRequest request1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("msg",response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("msg",error.getMessage());
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map=new HashMap<>();
                                map.put("tele",currentUser.getTele());
                                map.put("selfeva",editText.getText().toString());
                                map.put("type","eva");
                                return map;
                            }
                        };
                        requestQueue.add(request1);
                    }else {
                        String url1="http://119.23.13.19:8080/Aliyun/UpdateCVServlet";
                        StringRequest request1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("msg",response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("msg",error.getMessage());
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map=new HashMap<>();
                                map.put("tele",currentUser.getTele());
                                map.put("selfeva",editText.getText().toString());
                                map.put("type","eva");
                                return map;
                            }
                        };
                        requestQueue.add(request1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("type","eva");
                map.put("tele",currentUser.getTele());
                return map;
            }
        };
        requestQueue.add(request);
    }
}
