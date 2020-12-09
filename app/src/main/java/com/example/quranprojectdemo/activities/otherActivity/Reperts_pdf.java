package com.example.quranprojectdemo.Activities.otherActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.quranprojectdemo.BuildConfig;
import com.example.quranprojectdemo.models.otherModels.Report;
import com.example.quranprojectdemo.recyclerView.reportRequest.CustomReport;
import com.example.quranprojectdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Reperts_pdf extends AppCompatActivity {

    String pdfs;
    ShimmerRecyclerView rv;
    ArrayList<Report> reports;
    CustomReport customReport;
    private LinearLayout llPdf;
    // private ScrollView llPdf;
    private Bitmap bitmap;
    Button btn;
    SharedPreferences sp ;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reperts_pdf);




        rv = findViewById(R.id.reports_pdf_rv);
        reports = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
        reports.add(new Report(1, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
        reports.add(new Report( 1, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
        reports.add(new Report( 2, 1, 5, 5, 5, "أحمد عبدالغفور"));
        reports.add(new Report(+ 3, 1, 5, 5, 5, "حسن داوود"));
        reports.add(new Report(4, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
        reports.add(new Report(5, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
        reports.add(new Report(6, 1, 5, 5, 5, "أحمد عبدالغفور"));
        reports.add(new Report(7+ 3, 1, 5, 5, 5, "حسن داوود"));
        reports.add(new Report(8, 1, 5, 5, 5, "أحمد علي اليعقوبي"));
        reports.add(new Report(9, 1, 5, 5, 5, "مصطفى محمد الأسطل"));
        reports.add(new Report(10, 1, 5, 5, 5, "أحمد عبدالغفور"));
        reports.add(new Report(11, 1, 5, 5, 5, "حسن داوود"));
        reports.add(new Report(12, 1, 5, 5, 5, "حسن داوود"));
        reports.add(new Report(13, 1, 5, 5, 5, "حسن داوود"));
        reports.add(new Report(14, 1, 5, 5, 5, "حسن داوود"));

//        }

        customReport = new CustomReport(getBaseContext(), reports);
        customReport.notifyDataSetChanged();
        rv.showShimmerAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(customReport);

        llPdf = findViewById(R.id.reports_pdf_linearla);
        btn = findViewById(R.id.reports_btn_createa_report);


        if (ContextCompat.checkSelfPermission(Reperts_pdf.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            String []permissions ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(Reperts_pdf.this,permissions,0);
        }
        else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("size", "********************************************* " + llPdf.getWidth() + "  " + llPdf.getWidth());
                    bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                    createPdf();
                }
            });
        }
    }

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

        try {
            Log.d("filePath_getAbsolut","******************************mustafa");
            File filePath;
            filePath = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdfmustafa.pdf");

            Log.d("file1","*******************************************"+filePath);

            FileOutputStream fileOutputStream =new FileOutputStream(filePath);

            Log.d("filePath_getAbsolut","******************************"+filePath.getAbsolutePath());
            Log.d("fileOutputStream","******************************"+fileOutputStream.toString());
            document.writeTo(fileOutputStream);

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mass","******************************"+e.toString());

            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document

        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF() {

        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdfmustafa.pdf");
            if (file.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //    Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", createImageFile());
                Uri uri = FileProvider.getUriForFile(Reperts_pdf.this, BuildConfig.APPLICATION_ID + ".provider",file);

                //   Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Log.d("file2","*******************************************"+file);

                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Reperts_pdf.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }
    }


    private void openGeneratedPDFs(){

        // String pd=Environment.getExternalStorageDirectory().getAbsolutePath();

        try
        {
            final File file = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdfmustafa.pdf");
            //   final File file = new File("/sdcard/download/somepdf.pdf");

            if (file.exists())
            {


                Intent target = new Intent(Intent.ACTION_VIEW);
                Log.d("file2","*******************************************"+file);
                target.setDataAndType(Uri.fromFile(file),"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open File");
                startActivity(intent);

            }
        }
        catch(ActivityNotFoundException e)
        {
            Toast.makeText(Reperts_pdf.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("size", "********************************************* " + llPdf.getWidth() + "  " + llPdf.getWidth());
                            bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
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

        if (requestCode==1){
            if (resultCode==RESULT_OK){
                try {

                    Uri uri=data.getData();
                    OutputStream outputStream=getContentResolver().openOutputStream(uri);
                    outputStream.write("Hi,welcom to my Android Classroom !!!".getBytes());
                    outputStream.close();
                    Toast.makeText(this, "file nor saved", Toast.LENGTH_SHORT).show();

                }
                catch (IOException e){

                }
            }
        }
        else
            Toast.makeText(this, "file nor saved", Toast.LENGTH_SHORT).show();
    }
    }

