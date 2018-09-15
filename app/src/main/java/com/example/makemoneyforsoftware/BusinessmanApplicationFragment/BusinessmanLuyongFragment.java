package com.example.makemoneyforsoftware.BusinessmanApplicationFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.makemoneyforsoftware.LoginFragmentPackage.CurrentUser;
import com.example.makemoneyforsoftware.MyAnotherStackAdapter;
import com.example.makemoneyforsoftware.MyApplication;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;
import com.loopeer.cardstack.CardStackView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessmanLuyongFragment extends Fragment implements CardStackView.ItemExpendListener{
    private CardStackView mStackView;
    private MyAnotherStackAdapter mTestStackAdapter;
    private LinearLayout mActionButtonContainer;
    private List<Job> list;
    private CurrentUser currentUser;
    private View view;
    private SmartRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("msg","oncreateview");
        view= inflater.inflate(R.layout.businessman_application_baoming_layout,container,false);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh);//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                initList();
            }
        });
        MyApplication myApplication=(MyApplication)getActivity().getApplication();
        currentUser=myApplication.getLoginUser();
        mStackView = (CardStackView) view.findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) view.findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new MyAnotherStackAdapter(getActivity(),2);
        mStackView.setAdapter(mTestStackAdapter);
        initList();
        return view;
    }

    public void initList(){
        String url="http://119.23.13.19:8080/Aliyun/BusinessmanQueryJobServlet";
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    list=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i).getJSONObject("job");
                        Log.d("msg",jsonObject.toString());
                        Job job=new Job();
                        job.setJobtitle(jsonObject.getString("jobtitle"));
                        Log.d("msg","title："+jsonObject.getString("jobtitle"));
                        job.setJobtype(jsonObject.getString("jobtype"));
                        Log.d("msg","type："+jsonObject.getString("jobtype"));
                        job.setJobsalary(jsonObject.getString("jobsalary"));
                        Log.d("msg","salary："+jsonObject.getString("jobsalary"));
                        job.setJobrequirenum(jsonObject.getString("jobrequirenum"));
                        Log.d("msg","peoplenum："+jsonObject.getString("jobrequirenum"));
                        job.setJobrequiresex(jsonObject.getString("jobrequiresex"));
                        Log.d("msg","sex："+jsonObject.getString("jobrequiresex"));
                        job.setJobrequirecycle(jsonObject.getString("jobrequirecycle"));
                        Log.d("msg","cycle："+jsonObject.getString("jobrequirecycle"));
                        job.setJobrequireage(jsonObject.getString("jobrequireage"));
                        Log.d("msg","range："+jsonObject.getString("jobrequireage"));
                        job.setJobdiscrip(jsonObject.getString("jobdiscrip"));
                        Log.d("msg","discrip_text："+jsonObject.getString("jobdiscrip"));
                        job.setJobcontent(jsonObject.getString("jobcontent"));
                        Log.d("msg","content_text："+jsonObject.getString("jobcontent"));
                        job.setJobstartday(jsonObject.getString("jobstartday"));
                        Log.d("msg","startday："+jsonObject.getString("jobstartday"));
                        job.setJobendday(jsonObject.getString("jobendday"));
                        Log.d("msg","endday："+jsonObject.getString("jobendday"));
                        job.setJobworktime(jsonObject.getString("jobworktime"));
                        Log.d("msg","time："+jsonObject.getString("jobworktime"));
                        job.setJoblocation(jsonObject.getString("joblocation"));
                        Log.d("msg","location："+jsonObject.getString("joblocation"));
                        job.setBusinessmantele(jsonObject.getString("businessmantele"));
                        job.setJobreleaseday(jsonObject.getString("jobreleaseday"));
                        job.setJobid(jsonObject.getInt("jobid"));
                        list.add(job);
                    }
                    Log.d("msg","Thread："+Thread.currentThread().getName());
                    mTestStackAdapter.updateData(list);
                    refreshLayout.finishRefresh();
//                    getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////
////                        }
////                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("tele",currentUser.getTele());
                map.put("type","2");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemExpend(boolean expend) {

    }

    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
    }
}
