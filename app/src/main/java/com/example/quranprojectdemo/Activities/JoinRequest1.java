package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class JoinRequest1 extends AppCompatActivity {
TextView tv_JoinRequest;
EditText et_City,et_Country;
Button btn_Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request1);

        tv_JoinRequest=findViewById(R.id.request1_tv_joinRequest);
        et_City=findViewById(R.id.request1_et_City);
        et_Country=findViewById(R.id.request1_et_Country);
        btn_Next=findViewById(R.id.request1_btn_Next);


        TextView_EditFont(tv_JoinRequest,"Hacen_Tunisia_Bold.ttf");
        EditText_EditFont(et_City,"Hacen_Tunisia.ttf");
        EditText_EditFont(et_Country,"Hacen_Tunisia.ttf");

        btn_Next.setTypeface(Typeface.createFromAsset(getAssets(),"Hacen_Tunisia.ttf"));

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_Country.getText().toString().isEmpty()){
                    et_Country.setError("يجب ادخال الدولة");
                    return;
                }else if (et_City.getText().toString().isEmpty()){
                    et_City.setError("يجب إدخال المدينة");
                    return;
                }
                startActivity(new Intent(getBaseContext(),JoinRequest2.class));
                finish();
            }
        });
    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }
    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

}
