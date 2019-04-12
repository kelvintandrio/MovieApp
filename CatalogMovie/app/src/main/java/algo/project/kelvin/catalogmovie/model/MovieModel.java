package algo.project.kelvin.catalogmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    private int id;
    private String title_movie;
    private String release_date_movie;

    public MovieModel() {

    }

    public MovieModel(String title_movie, String release_date_movie) {
        this.title_movie = title_movie;
        this.release_date_movie = release_date_movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_movie() {
        return title_movie;
    }

    public void setTitle_movie(String title_movie) {
        this.title_movie = title_movie;
    }

    public String getRelease_date_movie() {
        return release_date_movie;
    }

    public void setRelease_date_movie(String release_date_movie) {
        this.release_date_movie = release_date_movie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title_movie);
        dest.writeString(release_date_movie);
    }

    protected MovieModel(Parcel in) {
        id = in.readInt();
        title_movie = in.readString();
        release_date_movie = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}