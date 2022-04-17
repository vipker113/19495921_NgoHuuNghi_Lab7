package com.example.sqlite_lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    DataUser dataUser;
    Button btnAdd, btnRemove, btnCancel;
    EditText inputName;
    ListView nameLv;
    ArrayList nameList;
    ArrayList idList;
    ArrayAdapter adapter;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataUser = new DataUser( this,"userdb.sqlite", null, 1);

        idList = new ArrayList();
        nameList = new ArrayList();

        inputName = findViewById(R.id.inputName);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        nameLv = findViewById(R.id.nameLv);

        getNameList();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);

        nameLv.setAdapter(adapter);

        // Actionlisener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataUser.addUser(new User(inputName.getText().toString()));
                getNameList();
                adapter.notifyDataSetChanged();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataUser.removeUser((int)idList.get(index));
                getNameList();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Succesful", Toast.LENGTH_SHORT).show();
            }
        });

        nameLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }
        });
    }

    private ArrayList getNameList(){
        idList.clear();
        nameList.clear();
        for (Iterator iterator = dataUser.getAll().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();

            nameList.add(user.getName());
            idList.add(user.getId());
        }
        return nameList;
    }
}