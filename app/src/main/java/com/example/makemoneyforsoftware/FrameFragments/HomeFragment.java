package com.example.makemoneyforsoftware.FrameFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.makemoneyforsoftware.AllJob.JobAdapter;
import com.example.makemoneyforsoftware.AllJob.JobItemActivity;
import com.example.makemoneyforsoftware.AllJob.MyJobListView;
import com.example.makemoneyforsoftware.MyPageAdapter;
import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;
import com.example.makemoneyforsoftware.SearchActivity;
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
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private View view;
    private int count=0;
    private ViewPager viewPager;
    private List<ImageView> imglist;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler=new Handler();
    private List<Job> list;
    private MyJobListView listView;
    private SmartRefreshLayout refreshLayout;
    private TextView location;
    public LocationClient mLocationClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("msg","oncreateview");
        timer=new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(count++);
                    }
                });

            }
        };
        view = inflater.inflate(R.layout.home_layout, container, false);
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        // 如果开发者需要获得当前点的位置信息，此处必须为true
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        // 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        initImage();
        viewPager=view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPageAdapter(getContext(),imglist));
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        Log.d("msg","width："+width);
        Log.d("msg","height："+height);
        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) viewPager.getLayoutParams();
        params.height=width*360/640;
        params.width=width;
        viewPager.setLayoutParams(params);
        timer.schedule(timerTask,1000,2000);
        location=(TextView)view.findViewById(R.id.city);
        ImageView img=(ImageView)view.findViewById(R.id.img);
        LinearLayout search=(LinearLayout)view.findViewById(R.id.search_lin);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.start();
            }
        });
        listView=(MyJobListView) view.findViewById(R.id.all_job_list);
        initList();
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh);//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                initList();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), JobItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("job",list.get(position));
                bundle.putInt("type",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    public void initImage(){
        imglist=new ArrayList<>();
        ImageView img1=new ImageView(getContext());
        img1.setImageResource(R.drawable.picture1);
        ImageView img2=new ImageView(getContext());
        img2.setImageResource(R.drawable.picture2);
        ImageView img3=new ImageView(getContext());
        img3.setImageResource(R.drawable.picture3);
        imglist.add(img1);
        imglist.add(img2);
        imglist.add(img3);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerTask.cancel();
        timer.cancel();
    }

    public void initList(){
        mLocationClient.start();
//        list=new ArrayList<>();
//        String url="http://119.23.13.19:8080/Aliyun/QueryAllJob";
//        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("msg",response);
//                try {
//                    JSONArray jsonArray=new JSONArray(response);
//                    list=new ArrayList<>();
//                    for(int i=0;i<jsonArray.length();i++){
//                        JSONObject jsonObject=jsonArray.getJSONObject(i).getJSONObject("job");
//                        Log.d("msg",jsonObject.toString());
//                        Job job=new Job();
//                        job.setJobtitle(jsonObject.getString("jobtitle"));
//                        Log.d("msg","title："+jsonObject.getString("jobtitle"));
//                        job.setJobtype(jsonObject.getString("jobtype"));
//                        Log.d("msg","type："+jsonObject.getString("jobtype"));
//                        job.setJobsalary(jsonObject.getString("jobsalary"));
//                        Log.d("msg","salary："+jsonObject.getString("jobsalary"));
//                        job.setJobrequirenum(jsonObject.getString("jobrequirenum"));
//                        Log.d("msg","peoplenum："+jsonObject.getString("jobrequirenum"));
//                        job.setJobrequiresex(jsonObject.getString("jobrequiresex"));
//                        Log.d("msg","sex："+jsonObject.getString("jobrequiresex"));
//                        job.setJobrequirecycle(jsonObject.getString("jobrequirecycle"));
//                        Log.d("msg","cycle："+jsonObject.getString("jobrequirecycle"));
//                        job.setJobrequireage(jsonObject.getString("jobrequireage"));
//                        Log.d("msg","range："+jsonObject.getString("jobrequireage"));
//                        job.setJobdiscrip(jsonObject.getString("jobdiscrip"));
//                        Log.d("msg","discrip_text："+jsonObject.getString("jobdiscrip"));
//                        job.setJobcontent(jsonObject.getString("jobcontent"));
//                        Log.d("msg","content_text："+jsonObject.getString("jobcontent"));
//                        job.setJobstartday(jsonObject.getString("jobstartday"));
//                        Log.d("msg","startday："+jsonObject.getString("jobstartday"));
//                        job.setJobendday(jsonObject.getString("jobendday"));
//                        Log.d("msg","endday："+jsonObject.getString("jobendday"));
//                        job.setJobworktime(jsonObject.getString("jobworktime"));
//                        Log.d("msg","time："+jsonObject.getString("jobworktime"));
//                        job.setJoblocation(jsonObject.getString("joblocation"));
//                        Log.d("msg","location："+jsonObject.getString("joblocation"));
//                        job.setBusinessmantele(jsonObject.getString("businessmantele"));
//                        job.setJobreleaseday(jsonObject.getString("jobreleaseday"));
//                        job.setJobid(jsonObject.getInt("jobid"));
//                        list.add(job);
//                    }
//                    Log.d("msg","Thread："+Thread.currentThread().getName());
//                    JobAdapter adapter=new JobAdapter(getActivity(),R.layout.jobitemlayout,list);
//                    listView.setAdapter(adapter);
//                    setListViewHeightBasedOnChildren(listView);
//                    refreshLayout.finishRefresh();
////                    getActivity().runOnUiThread(new Runnable() {
//////                        @Override
//////                        public void run() {
//////
//////                        }
//////                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                map.put("type","all");
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
//            currentPosition.append("纬度:").append(bdLocation.getLatitude()).append("\n");
//            currentPosition.append("经度:").append(bdLocation.getLongitude()).append("\n");
//            currentPosition.append("省:").append(bdLocation.getProvince()).append("\n");
//            currentPosition.append("市:").append(bdLocation.getCity()).append("\n");
//            currentPosition.append("区:").append(bdLocation.getDistrict()).append("\n");
//            currentPosition.append("街道:").append(bdLocation.getStreet()).append("\n");
//            currentPosition.append("镇:").append(bdLocation.getAddrStr()).append("\n");
//            currentPosition.append("定位方式:");
//            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
//                currentPosition.append("GPS");
//            }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
//                currentPosition.append("网络");
//            }
            currentPosition.append(bdLocation.getCity());
            location.setText(currentPosition);
        }
    }
}
