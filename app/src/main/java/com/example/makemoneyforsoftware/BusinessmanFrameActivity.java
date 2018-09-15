package com.example.makemoneyforsoftware;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.makemoneyforsoftware.FrameFragments.AllFragment;
import com.example.makemoneyforsoftware.FrameFragments.BusinessmanMeFragment;
import com.example.makemoneyforsoftware.FrameFragments.HomeFragment;
import com.example.makemoneyforsoftware.FrameFragments.MessageFragment;

import cn.bmob.v3.Bmob;

public class BusinessmanFrameActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private AllFragment allFragment;
    private MessageFragment messageFragment;
    private BusinessmanMeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessman_frame);
        Bmob.initialize(this,"273d85ee821227067102d46670d56565");
        final BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
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
                        }
                        transaction.replace(R.id.fragment_parent,homeFragment);
                        break;
                    case R.id.all:
                        if(allFragment==null){
                            allFragment=new AllFragment();
                        }
                        transaction.replace(R.id.fragment_parent,allFragment);
                        break;
                    case R.id.message:
                        if(messageFragment==null){
                            messageFragment=new MessageFragment();
                        }
                        transaction.replace(R.id.fragment_parent,messageFragment);
                        break;
                    case R.id.me:
                        if(meFragment==null){
                            meFragment=new BusinessmanMeFragment();
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
}
