package com.example.quranprojectdemo.activities.otherActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quranprojectdemo.R;

import static android.Manifest.permission.CALL_PHONE;

public class AboutApp extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
    ImageView call1, call2, call3, gmail1, gmail2, gmail3, face1, face2, face3, git1, git2, git3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        definitionItems();
        fonts();

        checkPermission("", 1);


        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_A_Call("+970593270727");
            }
        });
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_A_Call("+97059");
            }
        });
        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_A_Call("+970594114029");
            }
        });

        gmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail_msg("@gmail.com");
            }
        });
        gmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail_msg("@gmail.com");
            }
        });
        gmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail_msg("ahmed4114029@gmail.com");
            }
        });
        face1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenWebSite("https://www.facebook.com/mustafa.m.m2000");
            }
        });
        face2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenWebSite("https://www.facebook.com/AhmedAliALYacoubi/");
            }
        });
        face3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenWebSite("https://www.facebook.com/ahmed.abdelghafoor.58/");
            }
        });
        git1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenWebSite("https://github.com/Mustafa-Mohammed2");
            }
        });
        git2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenWebSite("https://github.com/ahmadAliYousif");
            }
        });
        git3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenWebSite("https://github.com/Ahmed-Redwan");
            }
        });

    }


    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    public void make_A_Call(String Number) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + Number));
        startActivity(i);
    }

    public void gmail_msg(String Email) {
        final Intent intent = new Intent(Intent.ACTION_VIEW)
                .setType("plain/text")
                .setData(Uri.parse(Email))
                .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        startActivity(intent);
    }

    private void OpenWebSite(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void definitionItems() {

        tv1 = findViewById(R.id.aboutApp_tv_about);
        tv2 = findViewById(R.id.aboutApp_tv_AppIdea);
        tv3 = findViewById(R.id.aboutApp_tv_AppIdeaText);
        tv4 = findViewById(R.id.aboutApp_tv_FirstName);
        tv5 = findViewById(R.id.aboutApp_tv_skill1);
        tv6 = findViewById(R.id.aboutApp_tv_text);
        tv7 = findViewById(R.id.aboutApp_tv_secondName);
        tv8 = findViewById(R.id.aboutApp_tv_skill2);
        tv9 = findViewById(R.id.aboutApp_tv_ThirdName);
        tv10 = findViewById(R.id.aboutApp_tv_skill3);

        call1 = findViewById(R.id.aboutApp_iv_call1);
        call2 = findViewById(R.id.aboutApp_iv_call2);
        call3 = findViewById(R.id.aboutApp_iv_call3);
        gmail1 = findViewById(R.id.aboutApp_iv_gmail1);
        gmail2 = findViewById(R.id.aboutApp_iv_gmail2);
        gmail3 = findViewById(R.id.aboutApp_iv_gmail3);
        face1 = findViewById(R.id.aboutApp_iv_facebook1);
        face2 = findViewById(R.id.aboutApp_iv_facebook2);
        face3 = findViewById(R.id.aboutApp_iv_facebook3);
        git1 = findViewById(R.id.aboutApp_iv_github1);
        git2 = findViewById(R.id.aboutApp_iv_github2);
        git3 = findViewById(R.id.aboutApp_iv_github3);

    }

    private void fonts() {

        TextView_EditFont(tv1, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv2, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv3, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv4, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv5, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv6, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv7, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv8, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv9, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv10, "Hacen_Tunisia.ttf");
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(AboutApp.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            AboutApp.this,
                            new String[]{CALL_PHONE},
                            requestCode);
        } else {
            Toast
                    .makeText(AboutApp.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
