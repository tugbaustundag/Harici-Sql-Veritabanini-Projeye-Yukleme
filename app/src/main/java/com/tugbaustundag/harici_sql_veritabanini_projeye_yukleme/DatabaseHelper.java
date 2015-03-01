package com.tugbaustundag.harici_sql_veritabanini_projeye_yukleme;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseHelper  extends SQLiteOpenHelper
{
    static String DB_PATH;
    //Veritabanı ismini veriyoruz
    static String DB_NAME = "School";

    SQLiteDatabase myDatabase;

    final Context myContext;

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);

        DB_PATH = context.getFilesDir().getParent() + "/databases/";

        this.myContext = context;
    }
    //Assest dizinine koyduğumuz sql dosyasını, Android projesi içine oluşturma ve kopyalamna işlemi yapıldı..
    public void CreateDataBase()
    {
        boolean dbExists = checkDataBase();

        if (!dbExists)
        {
            this.getReadableDatabase();

            try
            {
                copyDataBase();
            }
            catch (Exception ex)
            {
                Log.w("hata","Veritabanı kopyalanamıyor");
                throw new Error("Veritabanı kopyalanamıyor.");
            }
        }
    }
    //Sqlite veritabanı dosyasını açıp, veritabanımı kontrol ediyoruz
    boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DB_PATH + DB_NAME;

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception ex)
        {
            Log.w("hata","Veritabanı açılamadı");
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;
    }
    ///Assest dizinine koyduğumuz sql dosyasındaki verileri kopyalıyoruz
    void copyDataBase()
    {
        try
        {
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            String outFileName = DB_PATH + DB_NAME;

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];

            int length;

            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();

            myInput.close();
            myOutput.close();
        }
        catch (Exception ex)
        {
            Log.w("hata", "Kopya oluşturma hatası.");
        }
    }
    //Veritabannı açma işlemi yapıldı

    void openDataBase()
    {
        String myPath = DB_PATH + DB_NAME;

        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close()
    {
        if (myDatabase != null && myDatabase.isOpen())
            myDatabase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
