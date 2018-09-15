package com.example.makemoneyforsoftware.ReleaseJobPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.makemoneyforsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class JobTypeActivity extends AppCompatActivity {

    private List<JobType> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_type);
        initList();
        final ListView listView=(ListView)findViewById(R.id.type_list);
        JobTypeAdapter adapter=new JobTypeAdapter(JobTypeActivity.this,R.layout.jobtypeitemlayout,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                bundle.putString("type",list.get(position).getText());
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initList(){
        list=new ArrayList<>();
        JobType jobType1=new JobType(R.drawable.outsell,"外卖送餐");
        list.add(jobType1);
        JobType jobType2=new JobType(R.drawable.waiter,"餐饮服务");
        list.add(jobType2);
        JobType jobType3=new JobType(R.drawable.diaoyan,"传单调研");
        list.add(jobType3);
        JobType jobType4=new JobType(R.drawable.teacher_desk,"学生家教");
        list.add(jobType4);
        JobType jobType5=new JobType(R.drawable.worker,"临时工");
        list.add(jobType5);
        JobType jobType6=new JobType(R.drawable.other,"其他");
        list.add(jobType6);

    }
}
