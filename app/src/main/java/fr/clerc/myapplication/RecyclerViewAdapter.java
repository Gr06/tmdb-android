package fr.clerc.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uwetrottmann.tmdb2.entities.BaseMovie;

import java.util.List;




public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<BaseMovie> movies;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, BaseMovie movie);
    }

    public RecyclerViewAdapter(List<BaseMovie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public void addMovies(List<BaseMovie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feature, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BaseMovie movie = movies.get(position);

        holder.image.setImageResource(R.drawable.metro_logo);
        holder.featureName.setText(Integer.toString(movie.id));
        holder.featureStreet.setText(movie.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, movie);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View itemView;
        final TextView featureName;
        final TextView featureStreet;
        final TextView featureAddress;
        final ImageView image;

        ViewHolder(View view) {
            super(view);
            itemView = view;
            featureName = view.findViewById(R.id.feature_name);
            featureStreet = view.findViewById(R.id.feature_street);
            featureAddress = view.findViewById(R.id.feature_address);
            image = view.findViewById(R.id.feature_type);
        }
    }
}
