package algo.project.kelvin.catalogmovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import algo.project.kelvin.catalogmovie.R;
import algo.project.kelvin.catalogmovie.model.MovieModel;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private ArrayList<MovieModel> listMovie = new ArrayList<>();
    private Context mcontext;

    public MovieAdapter() {

    }

    public MovieAdapter(Context context, ArrayList<MovieModel> mlistMovie) {
        this.mcontext = context;
        this.listMovie = mlistMovie;
    }

    public void setData(ArrayList<MovieModel> listMovie) {
        if (listMovie.size() > 0) {
            this.listMovie.clear();
        }
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_row, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.textViewNim.setText(listMovie.get(position).getTitle_movie());
        holder.textViewNama.setText(listMovie.get(position).getRelease_date_movie());
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private TextView textViewNim;
        private TextView textViewNama;

        MovieHolder(@NonNull View itemView) {
            super(itemView);
            textViewNim = itemView.findViewById(R.id.txt_title);
            textViewNama = itemView.findViewById(R.id.txt_date);
        }
    }
}