package com.example.makemoneyforsoftware.AllJob;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.makemoneyforsoftware.R;
import com.example.makemoneyforsoftware.ReleaseJobPackage.Job;

import java.util.List;

public class JobAdapter extends ArrayAdapter<Job> {
    private int resourceId;

    public JobAdapter(@NonNull Context context, int resource, @NonNull List<Job> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Job job = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView img = (ImageView) view.findViewById(R.id.job_type_img);
        TextView title = (TextView) view.findViewById(R.id.job_title);
        TextView location = (TextView) view.findViewById(R.id.job_location);
        TextView salary = (TextView) view.findViewById(R.id.job_salary);
        TextView releasetime = (TextView) view.findViewById(R.id.job_release_time);
        if (job.getJobtype().equals("外卖送餐")) {
            img.setImageResource(R.drawable.outsell);
        } else if (job.getJobtype().equals("餐饮服务")) {
            img.setImageResource(R.drawable.waiter);
        } else if (job.getJobtype().equals("传单调研")) {
            img.setImageResource(R.drawable.diaoyan);
        } else if (job.getJobtype().equals("学生家教")) {
            img.setImageResource(R.drawable.teacher_desk);
        } else if (job.getJobtype().equals("临时工")) {
            img.setImageResource(R.drawable.worker);
        } else {
            img.setImageResource(R.drawable.other);
        }
        title.setText(job.getJobtitle());
        location.setText(job.getJoblocation());
        salary.setText(job.getJobsalary());
        releasetime.setText(job.getJobreleaseday());
        return view;

    }

}
