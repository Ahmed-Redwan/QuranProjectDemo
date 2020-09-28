package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Center;
import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.CustomGroupRecyclerView;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class JoinRequest1 extends AppCompatActivity {
TextView tv_JoinRequest;
EditText et_City,et_Country;
Button btn_Next;
SearchableSpinner sp_country,sp_city;
ArrayList<String>countries,cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request1);

        tv_JoinRequest=findViewById(R.id.request1_tv_joinRequest);
        et_City=findViewById(R.id.request1_et_City);
        et_Country=findViewById(R.id.request1_et_Country);
        sp_country=findViewById(R.id.request1_sp_Country);
        sp_city=findViewById(R.id.request1_sp_City);
        btn_Next=findViewById(R.id.request1_btn_Next);

        countries=new ArrayList<>();
        cities=new ArrayList<>();





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
                Intent intent=new Intent(getBaseContext(),JoinRequest2.class);
                intent.putExtra("Country",et_Country.getText().toString());
                intent.putExtra("City",et_City.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCountries();
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCities(countries.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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


    public boolean getCountries() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child("Countries");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countries.clear();
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    String country=c.getValue(CenterUser.class).getcountry();
                    Toast.makeText(JoinRequest1.this, country, Toast.LENGTH_SHORT).show();
                    countries.add(country);
                }

                ArrayAdapter countriesarrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, countries);
                sp_country.setAdapter(countriesarrayAdapter);




            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return true;
    }

    public boolean getCities(String Country) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child("Countries").child(Country);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countries.clear();
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    String city=c.getValue(CenterUser.class).getcity();
                    countries.add(city);

                }

                ArrayAdapter citiesarrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, countries);
                sp_city.setAdapter(citiesarrayAdapter);

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return true;
    }


}
