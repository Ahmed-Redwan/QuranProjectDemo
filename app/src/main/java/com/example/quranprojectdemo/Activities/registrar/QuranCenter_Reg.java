package com.example.quranprojectdemo.Activities.registrar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.mainActivity.Main_center;
import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.fireBase.GetCenterData;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;


public class QuranCenter_Reg extends AppCompatActivity {

    public static final String INFO_CENTER_REG = "info_reg";
    public static final String ID_CENTER_REG = "id_center_reg";
    EditText et_centerName, et_ManagerName, et_Phone, et_Email, et_Password, et_country, et_city, et_Address;
    TextView tv_newAccount, tv_I_Have_A_A, tv_Login;
    Button btn_CreateNewA;
    ProgressDialog progressDialog;
    RealmDataBaseItems dataBaseItems;
    GetCenterData getCenterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__reg);
        progressDialog = new ProgressDialog(QuranCenter_Reg.this);
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        getCenterData = GetCenterData.getinstance(this);
        def();


        viewFont();


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
//                btn_CreateNewA.setEnabled(false);

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


    private void viewFont() {
        et_centerName.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_ManagerName.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Email.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Password.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Phone.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_country.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_city.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Address.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_newAccount.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
        tv_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_I_Have_A_A.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        btn_CreateNewA.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }

    private void def() {

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

    }

    private void sign_up(final String email, final String password) {

        final CenterUser centerUser = new CenterUser(et_centerName.getText().toString()
                , et_ManagerName.getText().toString()
                , et_Phone.getText().toString()
                , et_Email.getText().toString()
                , et_country.getText().toString()
                , et_city.getText().toString(),
                et_Address.getText().toString()
                , et_Password.getText().toString(),
                null, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String centerId = getCenterData.signUp(email, password, centerUser);
                if (centerId != null) {
                    getCenterData.uploadCenterDataToFireBase(centerUser, centerId);
                    dataBaseItems.copyObjectToDataToRealm(centerUser, CenterUser.class);

                    startActivity(new Intent(getBaseContext(), Main_center.class));
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_Password.setError("تأكد من الإيميل و كلمة المرور.");
                            FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//
                        }
                    });
                }
            }
        }).start();
    }


}
