package com.example.quranprojectdemo.Activities.otherActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.quranprojectdemo.BuildConfig;
import com.example.quranprojectdemo.models.otherModels.Report;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.recyclerView.reportRequest.CustomReport;
import com.example.quranprojectdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Reprts extends AppCompatActivity {

    String pdfs;
    ShimmerRecyclerView rv;
    ArrayList<Report> reports;
    CustomReport customReport;
    private LinearLayout llPdf;
    // private ScrollView llPdf;
    private Bitmap bitmap;
    Button btn;

    RealmDataBaseItems realmDataBaseItems;


    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private String CREATE_PDF = "create_pdf";
    private String ID_CREATE_PDF = "id_create_pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprts);
        realmDataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        rv = findViewById(R.id.reports_rv);
        reports = new ArrayList<>();

        List<Student_Info> studentInfoList = realmDataBaseItems.getAllDataFromRealm(Student_Info.class);
        for (int i = 0; i < studentInfoList.size(); i++) {
            String name = studentInfoList.get(i).getName();
            String id = studentInfoList.get(i).getId_Student();
            String[] nameType = {"id_student", "attendess_student"};
            String[] valeu = {id, "حاضر"};
            String[] valeuNotAtt = {id, "غائب"};
            int countAtt = (int) realmDataBaseItems.getDataWithAndStatement(nameType, valeu, Student_data.class).size();
 
            int notAtt = (int) realmDataBaseItems.getDataWithAndStatement(nameType, valeuNotAtt, Student_data.class).size();

            List<Student_data> dataList = realmDataBaseItems.getDataWithAndStatement(nameType, valeu, Student_data.class);
            int countSavePages = 0, countRevPages = 0;
            for (int j = 0; j < dataList.size(); j++) {

                countSavePages += dataList.get(j).getCounnt_page_save();


                countRevPages += dataList.get(j).getCounnt_page_review();


            }
            reports.add(new Report(i + 1, countAtt, notAtt, countSavePages, countRevPages, name));
        }

        customReport = new CustomReport(getBaseContext(), reports);
        customReport.notifyDataSetChanged();
        rv.showShimmerAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(customReport);

        llPdf = findViewById(R.id.reports_linearla);
        btn = findViewById(R.id.reports_btn_createa_report);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size", " " + llPdf.getWidth() + "  " + llPdf.getWidth());
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                //   createPdf();
            }
        });


//        if (ContextCompat.checkSelfPermission(Reprts.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            ActivityCompat.requestPermissions(Reprts.this, permissions, 0);
//        } else {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = getSharedPreferences(CREATE_PDF, MODE_PRIVATE);
                editor = sp.edit();
                editor.putInt(ID_CREATE_PDF, 1);
                editor.apply();

                Intent intent = new Intent(getBaseContext(), Reperts_pdf.class);
                intent.putExtra("reports", reports);
                startActivity(intent);

//                     bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
//                    createPdf();
            }
        });


//        }


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
//            Log.d("create","******************************"+filePath.getAbsolutePath());
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

//*******************************************************************************************

        // write the document content
      /*  String targetPdf = "pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mass","******************************"+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }*/
        // write the document content


///                         storage/emulated/0/pdffromlayout.pdf
        //pdfs= Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdffromlayout.pdf";
        //Log.d("pdf","***********************************"+pdfs);

        try {
            //String targetPdf = Environment.getExternalStorageDirectory().getAbsolutePath();
//          "/sdcard/pdfmustafa.pdf"


            Log.d("filePath_getAbsolut", "******************************mustafa");
            File filePath;
            filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdfmustafa.pdf");

            Log.d("file1", "*******************************************" + filePath);

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            Log.d("filePath_getAbsolut", "******************************" + filePath.getAbsolutePath());
            Log.d("fileOutputStream", "******************************" + fileOutputStream.toString());
            document.writeTo(fileOutputStream);

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mass", "******************************" + e.toString());

            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document

        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF() {

        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdfmustafa.pdf");
            if (file.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //    Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", createImageFile());
                Uri uri = FileProvider.getUriForFile(Reprts.this, BuildConfig.APPLICATION_ID + ".provider", file);

                //   Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Log.d("file2", "*******************************************" + file);

                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Reprts.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }
    }


    private void openGeneratedPDFs() {

        // String pd=Environment.getExternalStorageDirectory().getAbsolutePath();

        try {
            final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdfmustafa.pdf");
            //   final File file = new File("/sdcard/download/somepdf.pdf");

            if (file.exists()) {
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//
//            try {
//                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), 1);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(Reprts.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
                //     }

                //*******


//                Intent intent = new Intent("com.adobe.reader");
//                intent.setType("application/pdf");
//                intent.setAction(Intent.ACTION_VIEW);
//                Uri uri = Uri.fromFile(file);
//                intent.setDataAndType(uri, "application/pdf");
//                startActivity(intent);


//            Intent intent=new Intent(Intent.ACTION_VIEW);
//            Uri uri = Uri.fromFile(file);
//            Log.d("uri","******************************"+uri);
//            intent.setDataAndType(uri, "application/pdf");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivity(intent);

                Intent target = new Intent(Intent.ACTION_VIEW);
                Log.d("file2", "*******************************************" + file);
                target.setDataAndType(Uri.fromFile(file), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open File");
                startActivity(intent);
//            try {
//                startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                // Instruct the user to install a PDF reader here, or something
//            }
//            final String download_file_url = getIntent().getStringExtra("url");
//            new Thread(new Runnable() {
//                public void run() {
//                    Uri path = Uri.fromFile(file);
//                    try {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        Uri uri = FileProvider.getUriForFile( file);
//                        intent.setDataAndType(uri, "application/pdf");
//                        startActivity(intent);
//                        finish();
//
//
//                    } catch (ActivityNotFoundException e) {
////                        tv_loading
////                                .setError("PDF Reader application is not installed in your device");
//                    }
//                }
//            }).start();
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Reprts.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
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
                            Log.d("size", "********************************************* " + llPdf.getWidth() + "  " + llPdf.getWidth());
//                            bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                            sp = getSharedPreferences(CREATE_PDF, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putInt(ID_CREATE_PDF, 1);
                            editor.commit();

                            createPdf();
                        }
                    });
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri uri = data.getData();
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    outputStream.write("Hi,welcom to my Android Classroom !!!".getBytes());
                    outputStream.close();
                    Toast.makeText(this, "file nor saved", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {

                }
            }
        } else
            Toast.makeText(this, "file nor saved", Toast.LENGTH_SHORT).show();
    }


}

//<?xml version="1.0" encoding="utf-8"?>
//
//<LinearLayout
//    xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    tools:context=".Activities.Reprts"
//    android:orientation="vertical"
//    android:gravity="center_horizontal"
//    android:id="@+id/reports_linearla">
//
//    <TextView
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:id="@+id/reports_tv_title"
//        android:text="التقرير الشهري"
//        android:textColor="@color/colorPrimaryDark"
//        android:textSize="@dimen/_35ssp"
//        android:textStyle="bold"
//        android:layout_marginTop="@dimen/_10sdp"/>
//
//
//    <com.toptoche.searchablespinnerlibrary.SearchableSpinner
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:id="@+id/reports_sp"
//        app:hintText="select a month."
//        android:layout_margin="@dimen/_20sdp"
//        />
//    <Button
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:id="@+id/reports_btn_createa_report"
//        android:text="create pdf."/>
//    <HorizontalScrollView
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content">
//<LinearLayout
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:orientation="vertical">
//
//        <LinearLayout
//            android:layout_width="wrap_content"
//            android:layout_height="wrap_content">
//
//            <TextView
//                android:layout_width="@dimen/_22sdp"
//                android:gravity="center"
//                android:layout_height="wrap_content"
//                android:id="@+id/Reports_tv_Num"
//                android:text="م"
//                android:textSize="@dimen/_10ssp"
//                android:background="@color/colorPrimaryDark"
//                android:layout_marginStart="@dimen/_2sdp"
//                android:layout_marginEnd="@dimen/_1sdp"
//                android:layout_marginTop="@dimen/_2sdp"
//                android:layout_marginBottom="@dimen/_2sdp"
//                android:padding="@dimen/_5sdp"
//                android:textColor="@android:color/white"
//                />
//            <TextView
//                android:layout_width="@dimen/_113sdp"
//                android:gravity="center"
//                android:layout_height="wrap_content"
//                android:id="@+id/Reports_tv_Name"
//                android:text="الإسم"
//                android:textSize="@dimen/_10ssp"
//                android:background="@color/colorPrimaryDark"
//                android:layout_marginStart="@dimen/_1sdp"
//                android:layout_marginEnd="@dimen/_1sdp"
//                android:layout_marginTop="@dimen/_2sdp"
//                android:layout_marginBottom="@dimen/_2sdp"
//                android:padding="@dimen/_5sdp"
//                android:textColor="@android:color/white"
//                />
//            <TextView
//                android:layout_width="@dimen/_55sdp"
//                android:gravity="center"
//                android:layout_height="wrap_content"
//                android:id="@+id/Reports_tv_numOfAttendanceDays"
//                android:text="أيام الحضور"
//                android:textSize="@dimen/_10ssp"
//                android:background="@color/colorPrimaryDark"
//                android:layout_marginStart="@dimen/_1sdp"
//                android:layout_marginEnd="@dimen/_1sdp"
//                android:layout_marginTop="@dimen/_2sdp"
//                android:layout_marginBottom="@dimen/_2sdp"
//                android:padding="@dimen/_5sdp"
//                android:textColor="@android:color/white"
//                />
//            <TextView
//                android:layout_width="@dimen/_49sdp"
//                android:gravity="center"
//                android:layout_height="wrap_content"
//                android:id="@+id/Reports_tv_numOfDays"
//                android:text="أيام الغياب"
//                android:textSize="@dimen/_10ssp"
//                android:background="@color/colorPrimaryDark"
//                android:layout_marginStart="@dimen/_1sdp"
//                android:layout_marginEnd="@dimen/_1sdp"
//                android:layout_marginTop="@dimen/_2sdp"
//                android:layout_marginBottom="@dimen/_2sdp"
//                android:padding="@dimen/_5sdp"
//                android:textColor="@android:color/white"
//                />
//            <TextView
//                android:layout_width="@dimen/_65sdp"
//                android:gravity="center"
//                android:layout_height="wrap_content"
//                android:id="@+id/Reports_tv_numOfSavePages"
//                android:text="صفحات الحفظ"
//                android:textSize="@dimen/_10ssp"
//                android:background="@color/colorPrimaryDark"
//                android:layout_marginStart="@dimen/_1sdp"
//                android:layout_marginEnd="@dimen/_1sdp"
//                android:layout_marginTop="@dimen/_2sdp"
//                android:layout_marginBottom="@dimen/_2sdp"
//                android:padding="@dimen/_5sdp"
//                android:textColor="@android:color/white"
//                />
//            <TextView
//                android:layout_width="@dimen/_73sdp"
//                android:gravity="center"
//                android:layout_height="wrap_content"
//                android:id="@+id/Reports_tv_numOfReviewPages"
//                android:text="صفحات المراجعة"
//                android:textSize="@dimen/_10ssp"
//                android:background="@color/colorPrimaryDark"
//                android:layout_marginStart="@dimen/_1sdp"
//                android:layout_marginEnd="@dimen/_1sdp"
//                android:layout_marginTop="@dimen/_2sdp"
//                android:layout_marginBottom="@dimen/_2sdp"
//                android:padding="@dimen/_5sdp"
//                android:textColor="@android:color/white"
//                />
//
//
//        </LinearLayout>
//        <com.cooltechworks.views.shimmer.ShimmerRecyclerView
//            android:layout_width="wrap_content"
//            android:layout_height="match_parent"
//            android:id="@+id/reports_rv"
//            tools:listitem="@layout/custom_row_reports"
//            app:shimmer_demo_child_count="10"
//            app:shimmer_demo_grid_child_count="2"
//            app:shimmer_demo_layout="@layout/custom_row_reports"
//            app:shimmer_demo_layout_manager_type="grid"
//            app:shimmer_demo_angle="20"
//            />
//</LinearLayout>
//
//    </HorizontalScrollView>
//
//
//
//
//
//</LinearLayout>