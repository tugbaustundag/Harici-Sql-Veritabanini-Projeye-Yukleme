package com.tugbaustundag.harici_sql_veritabanini_projeye_yukleme;


import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseHelper sınıfımızdaki CreateDataBase methodunu çağırıp , assest dizinine koymuş
        //olduğumuz veritabanını,Android projesinin içinde  oluşturup,verileri kopyalamasını sağladık...
        DatabaseHelper dbHelper=new DatabaseHelper(this);
        try
        {
            dbHelper.CreateDataBase();

        }
        catch (Exception ex)
        {
            Log.w("hata","Veritabanı oluşturulamadı ve kopyalanamadı!");
        }


        //Proje içine kopyalanmış olan veritabanımızdan verileri listview e yazdırdık

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String[] getColumnName={"student_name,student_surname"};
        Cursor imlec=db.query("student", getColumnName, null, null, null, null, null);
        ListView listview=(ListView)findViewById(R.id.listview);
        ArrayList<String> student=new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,student);

        while(imlec.moveToNext()){
            String student_name=imlec.getString(imlec.getColumnIndex("student_name"));
            String student_surname=imlec.getString(imlec.getColumnIndex("student_surname"));
            String name_surname= student_name+ " "+student_surname;
            student.add(name_surname);

        }
        listview.setAdapter(adapter);
        imlec.close();
        db.close();

    }


}
