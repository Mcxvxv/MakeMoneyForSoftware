package com.example.makemoneyforsoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.makemoneyforsoftware.CV.CvItem;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardstackActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {

    public static Integer[] TEST_DATAS = new Integer[]{

            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };
    private CardStackView mStackView;
    private LinearLayout mActionButtonContainer;
    private MyStackAdapter mTestStackAdapter;

    private List<CvItem> list;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardstack);

        list=new ArrayList<>();
        CvItem item1=new CvItem();
        item1.setColor(R.color.color_1);
        item1.setType(1);
        item1.setTitle("添加教育经历");
        item1.setSchool("武汉科技大学");
        item1.setXueli("本科");
        item1.setStartday("2016年9月");
        item1.setEndday("2020年6月");
        item1.setMajor("软件工程");
        list.add(item1);
        CvItem item2=new CvItem();
        item2.setType(2);
        item2.setColor(R.color.color_2);
        item2.setTitle("添加工作经历");
        item2.setCompany("东方瑞通");
        item2.setStartday("2016年9月");
        item2.setEndday("2020年6月");
        item2.setMsg("担任项目经理");
        list.add(item2);
        CvItem item3=new CvItem();
        item3.setColor(R.color.color_3);
        item3.setType(3);
        item3.setTitle("添加自我评价");
        item3.setMsg("自我评价");
        list.add(item3);
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new MyStackAdapter(this);
        mTestStackAdapter.updateData(list);
        mStackView.setAdapter(mTestStackAdapter);





//        new Handler().postDelayed(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
//                    }
//                }
//                , 200
//        );
    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down:
                mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down_stack:
                mStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(mStackView));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onPreClick(View view) {
        mStackView.pre();
    }



    public void onNextClick(View view) {
        mStackView.next();
    }


    @Override

    public void onItemExpend(boolean expend) {
        mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);

    }
}
