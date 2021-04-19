package com.nnk.sqliteidu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4= findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
    }

    public void save(View view) {
        String Id = et1.getText().toString();
        String name = et2.getText().toString();
        String contact = et3.getText().toString();
        String email = et4.getText().toString();
        String pass = et5.getText().toString();

        Database db = new Database(this);

        boolean res = db.insert(Id,name,contact,email,pass);
        if (res){

            Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show();

            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");
             
        }
    }
    public void back(View view) {
        Intent i1 = new Intent(this,MainActivity.class);
        startActivity(i1);
    }
}
