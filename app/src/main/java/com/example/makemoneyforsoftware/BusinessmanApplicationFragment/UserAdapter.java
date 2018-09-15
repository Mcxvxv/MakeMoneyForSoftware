package com.example.makemoneyforsoftware.BusinessmanApplicationFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.makemoneyforsoftware.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends ArrayAdapter<User> {
    private int type;
    private int resourceId;
    private User user;
    public UserAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<User> objects,int type) {
        super(context, textViewResourceId, objects);
        this.resourceId=textViewResourceId;
        this.type=type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        user=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        CircleImageView img=(CircleImageView)view.findViewById(R.id.head);
        TextView name=(TextView)view.findViewById(R.id.name);
        TextView tele=(TextView)view.findViewById(R.id.tele);
        final TextView mail=(TextView)view.findViewById(R.id.mail);
        Glide.with(view).load(user.getUrl()).into(img);
        name.setText("姓名:"+user.getName());
        tele.setText("联系电话:"+user.getTele());
        mail.setText("联系邮箱:"+user.getMail());
        LinearLayout janli=(LinearLayout)view.findViewById(R.id.jianli_button);

        switch (type){
            case 1:
                janli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), UserCVPreviewActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("user",user);
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 2:
                janli.removeAllViews();
                TextView confirm=new TextView(getContext());
                confirm.setText("职员到岗请点击确认");
                confirm.setTextColor(Color.WHITE);
                janli.addView(confirm);
                ImageView arrow=new ImageView(getContext());
                arrow.setImageResource(R.drawable.right_arrow_gray_16);
                janli.addView(arrow);
                janli.setGravity(Gravity.CENTER_VERTICAL);
                janli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Update();
                        Toast.makeText(getContext(),"更改职员工作状态成功",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:
                janli.removeAllViews();
                TextView account=new TextView(getContext());
                account.setText("结算工资请点击");
                account.setTextColor(Color.WHITE);
                janli.addView(account);
                ImageView arrow1=new ImageView(getContext());
                arrow1.setImageResource(R.drawable.right_arrow_gray_16);
                janli.addView(arrow1);
                janli.setGravity(Gravity.CENTER_VERTICAL);
                janli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Update();
                        Toast.makeText(getContext(),"更改职员工作状态成功",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 4:
                janli.removeAllViews();
                TextView account1=new TextView(getContext());
                account1.setText("已结算");
                account1.setTextColor(Color.WHITE);
                janli.addView(account1);
                break;
        }

        return view;
    }

    public void Update(){
        RequestQueue queue= Volley.newRequestQueue(getContext());
        final String url="http://119.23.13.19:8080/Aliyun/UpdateUserJobTypeServlet";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                map.put("tele",user.getTele());
                map.put("type",Integer.toString(type+1));
                map.put("jobid",Integer.toString(user.getJobid()));
                return map;
            }
        };
        queue.add(request);
    }
}
