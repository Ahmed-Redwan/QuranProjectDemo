package com.example.quranprojectdemo.activities.registrar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.activities.mainActivity.Main_center;
import com.example.quranprojectdemo.activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;


public class QuranCenter_Reg extends AppCompatActivity {

    public static final String INFO_CENTER_REG = "info_reg";
    public static final String ID_CENTER_REG = "id_center_reg";
    EditText et_centerName, et_ManagerName, et_Phone, et_Email, et_Password, et_country, et_city, et_Address;
    TextView tv_newAccount, tv_I_Have_A_A, tv_Login;
    Button btn_CreateNewA;
    CenterUser centeruser;
    private FirebaseAuth mAuth;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editor;
    RealmDataBaseItems dataBaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__reg);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(QuranCenter_Reg.this);
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());

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

        btn_CreateNewA.setEnabled(true);
        btn_CreateNewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                btn_CreateNewA.setEnabled(false);

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
                } else if (et_Password.getText().toString().length() < 7) {
                    et_Password.setError("Password must be more than 7 charachter.");
                    return;
                }


                sign_up(et_Email.getText().toString(), et_Password.getText().toString());

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


    private void sign_up(final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            centeruser = new CenterUser(et_centerName.getText().toString(), et_ManagerName.getText().toString(),
                                    et_Phone.getText().toString(), et_Email.getText().toString(), et_country.getText().toString()
                                    , et_city.getText().toString(), et_Address.getText().toString(),
                                    et_Password.getText().toString(), mAuth.getUid().toString()
                                    , "00");

                            sp = getSharedPreferences(INFO_CENTER_REG, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putString(ID_CENTER_REG, user.getUid());
                            editor.commit();
//                            sp = getSharedPreferences(QuranCenter_Login.INFO_CENTER_LOGIN, MODE_PRIVATE);
//                            editor = sp.edit();
//                            editor.clear();
//                            editor.commit();
                            FancyToast.makeText(getBaseContext(), "تم إنشاء الحساب بنجاح", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                setInRealTimeUsers(user.getUid());


                            startActivity(new Intent(getBaseContext(), Main_center.class));

                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(getBaseContext(), "فشل في إنشاء الحساب.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            btn_CreateNewA.setEnabled(true);
                            // progressDialog.dismiss();

                        }

                    }
                });


    }//للتسجيل


    public void setInRealTimeUsers(String name)   {
        add_info_center_to_realm();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(name).child("Center information");
        DatabaseReference reference2 = rootNode.getReference("Countries").child(et_country.getText().toString())
                .child(et_city.getText().toString()).child(mAuth.getUid());
        reference2.setValue(centeruser);
        reference.setValue(centeruser);


    }//اضافة بيانات

    public void add_info_center_to_realm()  {

        dataBaseItems.copyObjectToDataToRealm(centeruser,CenterUser.class);

    }//اضافة بيانات


}
