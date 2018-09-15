package com.example.makemoneyforsoftware.UserVolleyUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLR {
    private Context context;
    private RequestQueue requestQueue;
    private Activity one;
    private Activity two;

    public UserLR(Context context,Activity one,Activity two) {
        this.context = context;
        requestQueue= Volley.newRequestQueue(context);
        this.one=one;
        this.two=two;
    }

    public void LoginRequest(final String accountNumber, final String password) {
        //请求地址
        String url = "http://119.23.13.19:8080/Aliyun/LoginServlet";    //注①
        String tag = "Login";    //注②

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = null;  //注③
                    try {
                        jsonObject = (JSONObject) new JSONObject(response).get("params");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String result = jsonObject.getString("Result");  //注④
                    Log.d("msg",result);
                    if (result.equals("success")) {  //注⑤
                        //做自己的登录成功操作，如页面跳转
                        Intent intent=new Intent(one,two.getClass());
                    } else {
                        Toast.makeText(one,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                    Log.e("TAG", e.getMessage(), e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);  //注⑥
                params.put("Password", password);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }

    public void registerUser(final String tele, final String psd){
        String url = "http://119.23.13.19:8080/Aliyun/RegisterServlet";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("result",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("result",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("tele",tele);
                map.put("password",psd);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
