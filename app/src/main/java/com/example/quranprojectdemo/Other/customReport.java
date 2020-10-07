package com.example.quranprojectdemo.Other;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.R;

import java.util.ArrayList;

public class customReport extends RecyclerView.Adapter<customReport.View_Holder> {
    Context context;
    ArrayList<Report> reports;

    public customReport(Context context, ArrayList<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public customReport.View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_reports, null, false);
        customReport.View_Holder view_holder = new customReport.View_Holder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        Report report = reports.get(position);

        holder.tv_name.setText(report.getName());
        holder.tv_num.setText(report.getNum()+"");
        holder.tv_NumOfDays.setText(report.getNumOfNonAttendanceDays()+"");
        holder.tv_numOfAttendanceDays.setText(report.getNumOfAttendanceDays()+"");
        holder.tv_numOfReviewsPages.setText(report.getNumOfReviewPages()+"");
        holder.tv_numOfSavePages.setText(report.getNumOfSavePages()+"");




    }


    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        TextView tv_num, tv_name, tv_numOfSavePages, tv_numOfReviewsPages, tv_numOfAttendanceDays, tv_NumOfDays;

        public View_Holder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.customRowReports_tv_Name);
            tv_num = itemView.findViewById(R.id.customRowReports_tv_Num);
            tv_numOfSavePages = itemView.findViewById(R.id.customRowReports_tv_numOfSavePages);
            tv_numOfReviewsPages = itemView.findViewById(R.id.customRowReports_tv_numOfReviewPages);
            tv_numOfAttendanceDays = itemView.findViewById(R.id.customRowReports_tv_numOfAttendanceDays);
            tv_NumOfDays = itemView.findViewById(R.id.customRowReports_tv_numOfDays);

            tv_name.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_num.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_numOfSavePages.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_numOfReviewsPages.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_numOfAttendanceDays.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_NumOfDays.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));

        }
    }
}
