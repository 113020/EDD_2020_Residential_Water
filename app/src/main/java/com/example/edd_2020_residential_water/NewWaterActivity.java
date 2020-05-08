package com.example.edd_2020_residential_water;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.edd_2020_residential_water.models.Water;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NewWaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_water);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mGetReference = mDatabase.getReference();

        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()){

                        Object data = dataMap.get(key);

                        try{
                            HashMap<String, Object> wData = (HashMap<String, Object>) data;

                            Water mWater2 = new Water(14,1,2019, 6, 00, 00, 00, (String) wData.get("Fixture"), 40.0, 12,
                                    true, (Double) wData.get("totalVolume"), "regular", 40.0, "Save 20% of water.");
//                            addTextToView(mWater2.getFixture() + " - " + Double.toString(mWater2.getVolumeFlow()));
                        } catch (ClassCastException cce){
                            try{

                                String mString = String.valueOf(dataMap.get(key));
//                                addTextToView(mString);

                            }catch (ClassCastException cce2){

                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
