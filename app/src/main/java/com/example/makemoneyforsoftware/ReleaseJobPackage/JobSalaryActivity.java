package com.example.makemoneyforsoftware.ReleaseJobPackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.makemoneyforsoftware.R;

public class JobSalaryActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText salary;
    private Intent intent;
    private Bundle bundle;
    private String unit;
    private View.OnClickListener mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.salary_back:
                    new  AlertDialog.Builder(JobSalaryActivity.this)
                            .setMessage("是否保存编辑?")
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    bundle=new Bundle();
                                    bundle.putString("salary",salary.getText().toString()+"元/"+unit);
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                    break;
                case R.id.salary_save:
                    bundle=new Bundle();
                    bundle.putString("salary",salary.getText().toString()+"元/"+unit);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_salary);
        intent=getIntent();
        spinner=(Spinner)findViewById(R.id.salary_spinner);
        salary=(EditText)findViewById(R.id.salary_edit);
        ImageView back=(ImageView)findViewById(R.id.salary_back);
        Button save=(Button)findViewById(R.id.salary_save);
        back.setOnClickListener(mylistener);
        save.setOnClickListener(mylistener);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
