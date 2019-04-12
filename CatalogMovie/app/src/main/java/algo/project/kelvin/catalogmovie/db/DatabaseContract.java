package algo.project.kelvin.catalogmovie.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "table_movie";
    public static final class MovieColumns implements BaseColumns {
        static String ORIGINAL_TILE = "original_title";
        static String RELEASE_DATE = "release_date";
    }
}
