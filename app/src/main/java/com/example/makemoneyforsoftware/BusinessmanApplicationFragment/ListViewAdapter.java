package com.example.makemoneyforsoftware.BusinessmanApplicationFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewAdapter extends BaseSwipeAdapter{

    private Context mContext;
    private List<Job> mlist;

    public ListViewAdapter(Context mContext, List<Job> list) {
        this.mContext = mContext;
        this.mlist=list;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.delete_listview_item, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "双击", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job job=(Job) getItem(position);
                check(job.getJobid());
//                Toast.makeText(mContext, "点击删除", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
//        TextView t = (TextView)convertView.findViewById(R.id.position);
//        t.setText((position + 1) + ".");

        Job job= (Job) getItem(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.job_type_img);
        TextView title=(TextView)convertView.findViewById(R.id.job_title);
        TextView location=(TextView)convertView.findViewById(R.id.job_location);
        TextView salary=(TextView)convertView.findViewById(R.id.job_salary);
        TextView releasetime=(TextView)convertView.findViewById(R.id.job_release_time);
        if(job.getJobtype().equals("外卖送餐")){
            img.setImageResource(R.drawable.outsell);
        }else if (job.getJobtype().equals("餐饮服务")){
            img.setImageResource(R.drawable.waiter);
        }else if (job.getJobtype().equals("传单调研")){
            img.setImageResource(R.drawable.diaoyan);
        }else if (job.getJobtype().equals("学生家教")){
            img.setImageResource(R.drawable.teacher_desk);
        }else if (job.getJobtype().equals("临时工")){
            img.setImageResource(R.drawable.worker);
        }else {
            img.setImageResource(R.drawable.other);
        }
        title.setText(job.getJobtitle());
        location.setText(job.getJoblocation());
        salary.setText(job.getJobsalary());
        releasetime.setText(job.getJobreleaseday());
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void check(final int jobid){
        String url="http://119.23.13.19:8080/Aliyun/CheckJobisDeleteServlet";
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int result=jsonObject.getInt("result");
                    if(result==1){
                        Toast.makeText(mContext,"该兼职不可删除",Toast.LENGTH_SHORT).show();
                    }else {
                        delete(jobid);
                        Toast.makeText(mContext,"该兼职已成功删除",Toast.LENGTH_SHORT).show();
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
                map.put("jobid",Integer.toString(jobid));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void delete(final int jobid){
        String url="http://119.23.13.19:8080/Aliyun/DeleteJobServlet";
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                map.put("jobid",Integer.toString(jobid));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
