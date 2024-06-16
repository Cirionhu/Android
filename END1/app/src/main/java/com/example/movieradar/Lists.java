package com.example.movieradar;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import com.bumptech.glide.Glide;

public class Lists extends Fragment implements MovieAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private AppDatabase db;
    private MovieDao movieDao;
    private List<Movie> movieList;
    private boolean isZoomed = false;
    private boolean Del_Zoom = false;  // Új boolean változó
    private ImageView fullscreenImageView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_l_ists, container, false);

        recyclerView = view.findViewById(R.id.Mlist);
        fullscreenImageView = view.findViewById(R.id.fullscreenImageView);
        button = view.findViewById(R.id.Zoom_Delete);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "movies.db")
                .allowMainThreadQueries()
                .build();
        movieDao = db.movieDao();
        movieList = movieDao.getAllMovies();

        adapter = new MovieAdapter(requireContext(), movieList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(adapter);

        fullscreenImageView.setOnClickListener(v -> {
            // Zoom out when clicking on the fullscreen image
            fullscreenImageView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);  // Gomb láthatóvá tétele
            isZoomed = false;
        });

        button.setOnClickListener(v -> {
            Del_Zoom = !Del_Zoom;
            if (Del_Zoom) {
                button.setText("Delete");
            } else {
                button.setText("Zoom");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        movieList = movieDao.getAllMovies();
        adapter = new MovieAdapter(requireContext(), movieList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Movie movie = movieList.get(position);

        if (Del_Zoom) {
            // Delete movie
            movieDao.deleteMovieById(movie.id);
            Toast.makeText(requireContext(), "Movie deleted", Toast.LENGTH_SHORT).show();
            refreshData();
        } else {
            // Zoom in or out
            if (isZoomed) {
                fullscreenImageView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);  // Gomb láthatóvá tétele
                isZoomed = false;
            } else {
                recyclerView.setVisibility(View.GONE);
                fullscreenImageView.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);  // Gomb eltüntetése
                String baseUrl = "https://image.tmdb.org/t/p/original";
                Glide.with(requireContext())
                        .load(baseUrl + movie.posterPath)
                        .into(fullscreenImageView);
                isZoomed = true;
            }
        }
    }
}
