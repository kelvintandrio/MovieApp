package algo.project.kelvin.catalogmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static algo.project.kelvin.catalogmovie.db.DatabaseContract.TABLE_NAME;
import static algo.project.kelvin.catalogmovie.db.DatabaseContract.MovieColumns.ORIGINAL_TILE;
import static algo.project.kelvin.catalogmovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static android.provider.BaseColumns._ID;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmahasiswa";
    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_MAHASISWA = "create table " + TABLE_NAME +
            " (" + _ID + " integer primary key autoincrement, " +
            ORIGINAL_TILE + " text not null, " +
            RELEASE_DATE + " text not null);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
