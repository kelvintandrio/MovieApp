package algo.project.kelvin.catalogmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import algo.project.kelvin.catalogmovie.model.MovieModel;

import static algo.project.kelvin.catalogmovie.db.DatabaseContract.TABLE_NAME;
import static algo.project.kelvin.catalogmovie.db.DatabaseContract.MovieColumns.ORIGINAL_TILE;
import static algo.project.kelvin.catalogmovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static android.provider.BaseColumns._ID;

public class MovieHelper {
    private DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieModel> getDataByName(String nama) {
        Cursor cursor = database.query(TABLE_NAME, null, ORIGINAL_TILE + " LIKE ?", new String[]{nama}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        MovieModel movieModel;
        if (cursor.getCount() > 0) {
            do {
                movieModel = new MovieModel();
                movieModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setTitle_movie(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_TILE)));
                movieModel.setRelease_date_movie(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                arrayList.add(movieModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<MovieModel> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        MovieModel movieModel;
        if (cursor.getCount() > 0) {
            do {
                movieModel = new MovieModel();
                movieModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setTitle_movie(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_TILE)));
                movieModel.setRelease_date_movie(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                arrayList.add(movieModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieModel movieModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ORIGINAL_TILE, movieModel.getTitle_movie());
        initialValues.put(RELEASE_DATE, movieModel.getRelease_date_movie());
        return database.insert(TABLE_NAME, null, initialValues);
    }
}
