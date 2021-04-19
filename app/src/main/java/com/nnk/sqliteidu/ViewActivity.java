package com.nnk.sqliteidu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<HashMap<String, String>> al = null;
    ProgressDialog pd;
    String keys[] = {"k1","k2","k3","k4","k5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lv = findViewById(R.id.lv1);


        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait.....");
        readData();

        MyAdapter md = new MyAdapter();
        lv.setAdapter(md);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    Toast.makeText(ViewActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void readData()
    {
        pd.show();
        al = new ArrayList<HashMap<String, String>>();
        Database my = new Database(this);
        SQLiteDatabase db = my.getWritableDatabase();
        Cursor c = db.query(Database.TBNAME,null,null,null,null,null,null);
        if (c.moveToFirst())
        {
            do
            {
                String id = c.getString(0);
                String name = c.getString(1);
                String cno = c.getString(2);
                String email = c.getString(3);
                String password = c.getString(4);

                HashMap<String, String> hm = new HashMap<String, String>();

                hm.put(keys[0],id);
                hm.put(keys[1],name);
                hm.put(keys[2],cno);
                hm.put(keys[3],email);
                hm.put(keys[4],password);
                al.add(hm);
            }while (c.moveToNext());
        }
        else
        {
            Toast.makeText(this, "No Employee Available", Toast.LENGTH_LONG).show();
        }
        pd.dismiss();
    }


    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            HashMap<String, String> hm = al.get(i);
            final String id = hm.get(keys[0]);
            final String name = hm.get(keys[1]);
            final String contact  = hm.get(keys[2]);
            final String email = hm.get(keys[3]);
            final String pass = hm.get(keys[4]);

            LayoutInflater li = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View v = li.inflate(R.layout.employee_list_view,viewGroup,false);


            TextView tv = v.findViewById(R.id.emp_det_tv);
            tv.setText(id);

            TextView tv1 = v.findViewById(R.id.emp_det_tv1);
            tv1.setText(name);

            TextView tv2 = v.findViewById(R.id.emp_det_tv2);
            tv2.setText(contact);

            TextView tv3 = v.findViewById(R.id.emp_det_tv3);
            tv3.setText(email);

            TextView tv4 = v.findViewById(R.id.emp_det_tv4);
            tv4.setText(pass);

            Button b2 = v.findViewById(R.id.emp_det_delete);
            Button b1 = v.findViewById(R.id.emp_det_update);

           b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ViewActivity.this,RegisterActivity.class);
                    startActivity(i);
                    Database db = new Database(ViewActivity.this);
                    boolean update = db.update(id,name,contact,email,pass);
                    if (update){

                        Toast.makeText(ViewActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    new AlertDialog.Builder(ViewActivity.this)
                            .setIcon(R.drawable.cone)
                            .setTitle("Employee Delete")
                            .setMessage("Deleting Employee Conformation")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Database md = new Database(ViewActivity.this);
                                    SQLiteDatabase db = md.getWritableDatabase();
                                    String where = Database.COL3+" = ?";
                                    String args[] = {(contact)};
                                    db.delete(Database.TBNAME,where,args);
                                    Toast.makeText(ViewActivity.this, "Employee Deleted", Toast.LENGTH_SHORT).show();
                                    ViewActivity.this.finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            });

            return v;
        }
    }
}
