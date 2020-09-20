package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class QuranCenter_Reg extends AppCompatActivity {

    EditText et_centerName, et_ManagerName, et_Phone, et_Email, et_Password, et_country, et_city, et_Address;
    TextView tv_newAccount, tv_I_Have_A_A, tv_Login;
    Button btn_CreateNewA;
    CenterUser centeruser;
    private FirebaseAuth mAuth;
    SharedPreferences sp;
    int id;
    int count = 0;
    SharedPreferences.Editor editor;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__reg);
        mAuth = FirebaseAuth.getInstance();

        et_centerName = findViewById(R.id.QuranCenter_et_CenterName);
        et_ManagerName = findViewById(R.id.QuranCenter_et_ManagerName);
        et_Email = findViewById(R.id.QuranCenter_et_Email);
        et_Phone = findViewById(R.id.QuranCenter_et_PhoneNum);
        et_country = findViewById(R.id.QuranCenter_et_Country);
        et_city = findViewById(R.id.QuranCenter_et_City);
        et_Address = findViewById(R.id.QuranCenter_et_Address);
        et_Password = findViewById(R.id.QuranCenter_et_Password);
        btn_CreateNewA = findViewById(R.id.QuranCenter_btn_CreateNewAcc);
        tv_Login = findViewById(R.id.QuranCenter_tv_Login);
        tv_newAccount = findViewById(R.id.QuranCenter_tv_newAccount);
        tv_I_Have_A_A = findViewById(R.id.QuranCenter_tv_iHaveAnAccount);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();


        EditText_EditFont(et_centerName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_ManagerName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Password, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_country, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_city, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Address, "Hacen_Tunisia.ttf");

        TextView_EditFont(tv_newAccount, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_Login, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_I_Have_A_A, "Hacen_Tunisia.ttf");

        btn_CreateNewA.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

//        btn_CreateNewA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getBaseContext(), Main_center.class));
//                finish();
//
//            }
//        });

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), QuranCenter_Login.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        sp = getSharedPreferences("Info", MODE_PRIVATE);
        editor = sp.edit();
        btn_CreateNewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_centerName.getText().toString().isEmpty()) {
                    et_centerName.setError("Center name is required.");
                    return;
                } else if (et_ManagerName.getText().toString().isEmpty()) {
                    et_ManagerName.setError("Manager name is required.");
                    return;
                } else if (et_Phone.getText().toString().isEmpty()) {
                    et_Phone.setError("Phone is required.");
                    return;
                } else if (et_Email.getText().toString().isEmpty()) {
                    et_Email.setError("Email is required.");
                    return;
                } else if (et_country.getText().toString().isEmpty()) {
                    et_country.setError("Country is required.");
                    return;
                } else if (et_city.getText().toString().isEmpty()) {
                    et_city.setError("City is required.");
                    return;
                } else if (et_Address.getText().toString().isEmpty()) {
                    et_Address.setError("Address is required.");
                    return;
                } else if (et_Password.getText().toString().isEmpty()) {
                    et_Password.setError("Password is required.");
                    return;
                } else if (et_Password.getText().toString().length() < 8) {
                    et_Password.setError("Password must be more than 8 charachter.");
                    return;
                }
                ++count;
                id = 20200000 + count;


                centeruser = new CenterUser(et_centerName.getText().toString(), et_ManagerName.getText().toString(), et_Phone.getText().toString(), et_Email.getText().toString(), et_country.getText().toString()
                        , et_city.getText().toString(), et_Address.getText().toString(), et_Password.getText().toString(), id);
                editor.putString("id", id + "");
                editor.apply();
                setInRealTimeUsers(et_centerName.getText().toString() + id);

                Toast.makeText(getBaseContext(), id + "", Toast.LENGTH_SHORT).show();

                sign_up(et_Email.getText().toString(), et_Password.getText().toString());
                startActivity(new Intent(getBaseContext(), Main_center.class));

//                setInRealTimeUsers(et_centerName.getText().toString());
                //  startActivity(new Intent(getBaseContext(), Main_center.class).putExtra("CenterName",centeruser.getCenterName()));
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

//    public void create_new_center() {
//
//        centeruser = new CenterUser(et_centerName.getText().toString(), et_ManagerName.getText().toString(), et_Phone.getText().toString(), et_Email.getText().toString(), et_country.getText().toString()
//                , et_city.getText().toString(), et_Address.getText().toString(), et_Password.getText().toString(), 1);
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = rootNode.getReference("Center_users");//create a main_node
//        DatabaseReference c1 = reference.child(et_centerName.getText().toString());//create a new center
//
//        DatabaseReference c1_info = c1.child("center_info");//add info
//        c1_info.setValue(centeruser);//class to add
//
//    }

    private void sign_up(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //   userId=user.getUid();
//                            editor.putString("UID_CENTER", user.getUid())
                            Toast.makeText(getBaseContext(), user.getUid(), Toast.LENGTH_SHORT).show();
                            //         create_new_center();
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(QuranCenter_Reg.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }//للتسجيل


    public void setInRealTimeUsers(String name) {


        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers");

//int id, String name, int age, String address, String email, String phone
        reference.child(name).child("Center information").setValue(centeruser);
        reference.child("Count").setValue(count);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("msg", (String) snapshot.child("Center information").child("Count").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }//اضافة بيانات
}
