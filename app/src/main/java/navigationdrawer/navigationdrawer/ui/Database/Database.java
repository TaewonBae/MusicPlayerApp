package navigationdrawer.navigationdrawer.ui.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import navigationdrawer.navigationdrawer.ui.search.Taenie;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "Taenie_music.db";
    private static final int DB_VER = 1;
    public static ArrayList<byte[]> musicList = new ArrayList<>();
    public static Context context2;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
        context2 = context;
    }

    //Function get all Music List
    public List<Taenie> getTaenies() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Make sure all is column name in your Table
        String[] sqlSelect = {"Rank", "Singer", "Title", "Image", "Mp3"};
        //Make sure this is your table name
        String tableName = "Taenie";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<Taenie> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Taenie taenie = new Taenie();
                taenie.setRank(cursor.getInt(cursor.getColumnIndex("Rank")));
                taenie.setSinger(cursor.getString(cursor.getColumnIndex("Singer")));
                taenie.setSongtitle(cursor.getString(cursor.getColumnIndex("Title")));
                taenie.setAlbum_cover(cursor.getBlob(cursor.getColumnIndex("Image")));
                musicList.add(cursor.getBlob(cursor.getColumnIndex("Mp3")));

                result.add(taenie);

            } while (cursor.moveToNext());
        }
        return result;

    }

    //Function get all Music Singer List
    public List<String> getNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Make sure all is column name in your Table
        String[] sqlSelect = {"Singer"};
        //Make sure this is your table name
        String tableName = "Taenie";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Singer")));
            } while (cursor.moveToNext());
        }
        return result;//레코드가10개이기 대문에 size가 10이 될때 빠져나와서 result값으로 10을 리턴
    }

    public List<Taenie> getTaenieByName(String singer) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Make sure all is column name in your Table
        String[] sqlSelect = {"Rank", "Singer", "Title", "Image", "Mp3"};
        //Make sure this is your table name
        String tableName = "Taenie";

        qb.setTables(tableName);

        //If you want to get extract name, just change
        //Cursor cursor = qb.query(db, sqlSelect, "Singer LIKE?", new String[]{Singer}, null, null, null);

        //This will like query : Select * from Taenie where Name LIKE %pattern%
        Cursor cursor = qb.query(db, sqlSelect, "Singer LIKE?", new String[]{"%" + singer + "%"}, null, null, null);
        List<Taenie> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Taenie taenie = new Taenie();
                taenie.setRank(cursor.getInt(cursor.getColumnIndex("Rank")));
                taenie.setSinger(cursor.getString(cursor.getColumnIndex("Singer")));
                taenie.setSongtitle(cursor.getString(cursor.getColumnIndex("Title")));
                taenie.setAlbum_cover(cursor.getBlob(cursor.getColumnIndex("Image")));
                musicList.add(cursor.getBlob(cursor.getColumnIndex("Mp3")));

                result.add(taenie);
            } while (cursor.moveToNext());
        }
        return result;
    }
}

