package com.nnk.sqliteidu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.e1);
        e2= findViewById(R.id.e2);
    }

    public void login(View view) {
        String email = e1.getText().toString();
        String pass = e2.getText().toString();
        if (email.isEmpty()){
            e1.setError("Empty");
        }if (pass.isEmpty()){
            e2.setError("Empty");
            e1.requestFocus();
        }else {

            Database db = new Database(this);
            e1.setText("");
            e2.setText("");

            if (db.logincheck(email,pass)) {
                Toast.makeText(this,"Valid",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void register(View view) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void view(View view) {
        Intent i3 = new Intent(this,ViewActivity.class);
        startActivity(i3);
    }
}
