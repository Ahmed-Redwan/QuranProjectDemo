package com.example.quranprojectdemo.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.quranprojectdemo.Other.Report;
import com.example.quranprojectdemo.Other.customReport;
import com.example.quranprojectdemo.R;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Reprts extends AppCompatActivity {

    String pdfs;
    ShimmerRecyclerView rv;
    ArrayList<Report> reports;
    customReport customReport;
    private HorizontalScrollView llPdf;
    private Bitmap bitmap;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprts);

        rv = findViewById(R.id.reports_rv);
        reports = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            reports.add(new Report(i, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
            reports.add(new Report(i + 1, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
            reports.add(new Report(i + 2, 1, 5, 5, 5, "أحمد عبدالغفور"));
            reports.add(new Report(i + 3, 1, 5, 5, 5, "حسن داوود"));
            reports.add(new Report(i, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
            reports.add(new Report(i + 1, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
            reports.add(new Report(i + 2, 1, 5, 5, 5, "أحمد عبدالغفور"));
            reports.add(new Report(i + 3, 1, 5, 5, 5, "حسن داوود"));
            reports.add(new Report(i, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
            reports.add(new Report(i + 1, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
            reports.add(new Report(i + 2, 1, 5, 5, 5, "أحمد عبدالغفور"));
            reports.add(new Report(i + 3, 1, 5, 5, 5, "حسن داوود"));
            reports.add(new Report(i, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
            reports.add(new Report(i + 1, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
            reports.add(new Report(i + 2, 1, 5, 5, 5, "أحمد عبدالغفور"));
            reports.add(new Report(i + 3, 1, 5, 5, 5, "حسن داوود"));
        }
        customReport = new customReport(getBaseContext(), reports);
        customReport.notifyDataSetChanged();
        rv.showShimmerAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(customReport);

        llPdf = findViewById(R.id.reports_linearla);
        btn = findViewById(R.id.reports_btn_createa_report);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("size", " " + llPdf.getWidth() + "  " + llPdf.getWidth());
//                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
//             //   createPdf();
//            }
//        });


        if (ContextCompat.checkSelfPermission(Reprts.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(Reprts.this, permissions, 0);
        } else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("size", "*************** " + llPdf.getWidth() + "  " + llPdf.getWidth());
                    bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                    createPdf();
                }
            });


        }


    }

//    private void createPdfs(){
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        //  Display display = wm.getDefaultDisplay();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        float hight = displaymetrics.heightPixels ;
//        float width = displaymetrics.widthPixels ;
//
//        int convertHighet = (int) hight, convertWidth = (int) width;
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//
//        PdfDocument document = new PdfDocument();
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//
//        Paint paint = new Paint();
//        canvas.drawPaint(paint);
//
//        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, 0, 0 , null);
//        document.finishPage(page);
//
//        // write the document content
//        try {
//        String targetPdf = "/sdcard/pdfmustafa.pdf";
//        File filePath;
//        filePath = new File(targetPdf);
//
//            document.writeTo(new FileOutputStream(filePath));
//
//            Log.d("create","**********"+filePath.getAbsolutePath());
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//        // close the document
//        document.close();
//        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();
//
//        openGeneratedPDF();
//
//    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

//        String fileName = "myFile.pdf";
//        File filePath;
//        filePath = new File(fileName);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//        document.close();
//        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

//*******************************

        // write the document content
      /*  String targetPdf = "pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mass","**********"+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }*/
        // write the document content


///                         storage/emulated/0/pdffromlayout.pdf
        //pdfs= Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdffromlayout.pdf";
        //Log.d("pdf","*************"+pdfs);

        try {
            //String targetPdf = Environment.getExternalStorageDirectory().getAbsolutePath();
//          "/sdcard/pdfmustafa.pdf"


            Log.d("filePath_getAbsolut", "************mustafa");
            File filePath;
            filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdfmustafa.pdf");

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            Log.d("filePath_getAbsolut", "**********" + filePath.getAbsolutePath());
            Log.d("fileOutputStream", "**********" + fileOutputStream.toString());
            document.writeTo(fileOutputStream);

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mass", "**********" + e.toString());

            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document


        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDFs() {
        File file = new File("myFile.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Reprts.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openGeneratedPDF() {

        // String pd=Environment.getExternalStorageDirectory().getAbsolutePath();


        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdfmustafa.pdf");

            if (file.exists()) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(file);
                    Log.d("uri", "**********" + uri);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Reprts.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
                }
            }
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("size", "*************** " + llPdf.getWidth() + "  " + llPdf.getWidth());
                            bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                            createPdf();
                        }
                    });
                }
                return;
        }
    }


}