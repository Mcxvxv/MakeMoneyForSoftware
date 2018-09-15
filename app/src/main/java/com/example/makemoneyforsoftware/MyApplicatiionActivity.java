package com.example.makemoneyforsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.makemoneyforsoftware.LoginFragmentPackage.MyFragmentAdapter;
import com.example.makemoneyforsoftware.MyApplicationFragments.BaoMingFragment;
import com.example.makemoneyforsoftware.MyApplicationFragments.DaoGangFragment;
import com.example.makemoneyforsoftware.MyApplicationFragments.JieSuanFragment;
import com.example.makemoneyforsoftware.MyApplicationFragments.LuYongFragment;

import java.util.ArrayList;
import java.util.List;

public class MyApplicatiionActivity extends AppCompatActivity {

    private List<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applicatiion);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("已报名"));
        tabLayout.addTab(tabLayout.newTab().setText("已录用"));
        tabLayout.addTab(tabLayout.newTab().setText("已到岗"));
        tabLayout.addTab(tabLayout.newTab().setText("已结算"));
        list=new ArrayList<>();
        list.add(new BaoMingFragment());
        list.add(new LuYongFragment());
        list.add(new DaoGangFragment());
        list.add(new JieSuanFragment());
        String[] title=new String[]{"已报名","已录用","已到岗","已结算"};
        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager(),list,title);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        int page=intent.getExtras().getInt("page");
        viewPager.setCurrentItem(page);
    }

}
