package com.example.makemoneyforsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.makemoneyforsoftware.FrameFragments.AllFragment;
import com.example.makemoneyforsoftware.FrameFragments.HomeFragment;
import com.example.makemoneyforsoftware.FrameFragments.MeFragment;
import com.example.makemoneyforsoftware.FrameFragments.MessageFragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;

public class FrameActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private AllFragment allFragment;
    private MessageFragment messageFragment;
    private MeFragment meFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        Bmob.initialize(this,"273d85ee821227067102d46670d56565");
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        NavagationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        if(homeFragment==null){
                            homeFragment=new HomeFragment();
                            Log.d("msg","new");
                        }
                        transaction.replace(R.id.fragment_parent,homeFragment);
                        break;
                    case R.id.all:
                        if(allFragment==null){
                            allFragment=new AllFragment();
                            Log.d("msg","new");
                        }
                        transaction.replace(R.id.fragment_parent,allFragment);
                        break;
                    case R.id.message:
                        if(messageFragment==null){
                            messageFragment=new MessageFragment();
                            Log.d("msg","new");
                        }
                        transaction.replace(R.id.fragment_parent,messageFragment);
                        break;
                    case R.id.me:
                        if(meFragment==null){
                            meFragment=new MeFragment();
                            Log.d("msg","new");
                        }
                        transaction.replace(R.id.fragment_parent,meFragment);
                        break;
                }
                transaction.commit();
                return true;
            }
        });
        setDefaultFragment();
    }

    public void setDefaultFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        homeFragment=new HomeFragment();
        transaction.replace(R.id.fragment_parent,homeFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        Log.d("msg","frameactivity onresume");
        super.onResume();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            int page=bundle.getInt("page");
            Log.d("msg","page:"+page);
            switch (page){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    if (meFragment == null) {
                        meFragment=new MeFragment();
                    }
                    transaction.replace(R.id.fragment_parent,meFragment);
                    transaction.commit();
                    break;
            }
        }
    }
}
