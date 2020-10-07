package com.example.quranprojectdemo.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.quranprojectdemo.Other.Report;
import com.example.quranprojectdemo.Other.customReport;
import com.example.quranprojectdemo.R;

import java.util.ArrayList;

public class Reprts extends AppCompatActivity {

    ShimmerRecyclerView rv;
    ArrayList<Report> reports;
    customReport customReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprts);

        rv = findViewById(R.id.reports_rv);
        reports = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            reports.add(new Report(i, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
            reports.add(new Report(i+1, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
            reports.add(new Report(i+2, 1, 5, 5, 5, "أحمد عبدالغفور"));
            reports.add(new Report(i+3, 1, 5, 5, 5, "حسن داوود"));
        }
        customReport = new customReport(getBaseContext(), reports);
        customReport.notifyDataSetChanged();
        rv.showShimmerAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(customReport);


    }
}