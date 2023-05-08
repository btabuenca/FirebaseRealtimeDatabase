package org.upm.btb.accelerometerfb;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListaActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener pChildEventListener;

    // variable for Text view.
    private TextView retrieveTV;
    ListView lvListadoFirebase;
    List<String> lRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lvListadoFirebase = findViewById(R.id.listadoFirebase);
        lRow = new ArrayList<>();

        // initializing our object class variable.
        retrieveTV = findViewById(R.id.tvFirebase);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("timestamps");

        // calling add value event listener method for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String sValue="";
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                if(!map.isEmpty()){
                    for (Map.Entry<String, Object> pair : map.entrySet()){
                        //iterate over the pairs
                        sValue = sValue + "[" +pair.getValue()+ "] \n" ;
                    }
                }
                retrieveTV.setText(sValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(ListaActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}