package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.quranprojectdemo.Activities.QuranCenter_Login.INFO_CENTER_LOGIN;

public class Main_center extends AppCompatActivity {

    Toolbar toolbar_center;
    ImageView image_center;
    TextView tv_center_name, tv_center_name_maneger, tv_center_phone, tv_center_count_ring, tv_center_count_student;
    SharedPreferences sp;
    private String centerId;

    @Override
    protected void onStart() {
        super.onStart();
        getInRealTimeUsers();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_center);

        image_center = findViewById(R.id.center_main_image);
        tv_center_name = findViewById(R.id.center_main_tv_name_center);
        tv_center_name_maneger = findViewById(R.id.center_main_tv_name_maneger);
        tv_center_phone = findViewById(R.id.center_main_tv_phone);
        tv_center_count_ring = findViewById(R.id.center_main_tv_count_ring);
        tv_center_count_student = findViewById(R.id.center_main_tv_count_student);
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);

        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            centerId = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            centerId = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");


        }
        Toast.makeText(getBaseContext(), centerId, Toast.LENGTH_SHORT).show();

        toolbar_center = findViewById(R.id.center_main_tool);
        toolbar_center.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuCentreHomeAddGroub:
                        startActivity(new Intent(getBaseContext(), AddNewGroup.class));
                        return true;
//                    case R.id.MenuCentreHomeAddStudent:
//                        startActivity(new Intent(getBaseContext(), AddNewStudent.class));
//                        return true;
                    case R.id.MenuCentreHomeShowInfo:
                        startActivity(new Intent(getBaseContext(), ShowmeMorizationLoops.class));
                        return true;
                    case R.id.MenuCentreHomeRequestsList:
                        startActivity(new Intent(getBaseContext(), JoinRequests.class));
                        return true;
                    case R.id.MenuCenterHomeExit:
                        finish();
                        return true;
                    case R.id.MenuCenterHomeSettings:
                        return true;
                    case R.id.MenuCenterHomeAbout:
                        return true;
                }
                return false;
            }
        });

    /*    FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Log.d("asd","****************"+snapshot1.getValue());

                }
                //الحمدلله
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        TextView_EditFont(tv_center_count_ring, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_count_student, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_name, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_name_maneger, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_phone, "Hacen_Tunisia.ttf");


    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }


    public void getInRealTimeUsers() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerId).child("Center information");
        final DatabaseReference reference1 = reference.getParent().child("groups");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_center_count_ring.setText(snapshot.getChildrenCount() + "عدد الحلقات : ");
                int numStudent = 0;

                for (DataSnapshot d : snapshot.getChildren()) {
                    numStudent +=d.child("student_group").getChildrenCount();

//                    numStudent += d.getChildrenCount();
                }
                tv_center_count_student.setText(numStudent + "عدد طلاب المرز");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                CenterUser value = dataSnapshot.getValue(CenterUser.class);
                tv_center_name.setText(value.getcenterName() + "مركز :  : ");
                tv_center_name_maneger.setText(value.getmanagerName() + "مدير المركز : ");
                tv_center_phone.setText(value.getPhone() + "هاتف : ");

                Toast.makeText(getApplicationContext(), value.getcenterName(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }//جلب البيانات

}

