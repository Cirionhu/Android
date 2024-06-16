package com.example.movieradar;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Home extends Fragment {

    //
    interface MovieRequest {
        @GET("3/discover/movie")
        Call<MovieResponse> getMovies(
                @Query("api_key") String apiKey,
                @Query("page") int page,
                @Query("with_genres") String gens,
                @Query("primary_release_year") String year
        );
    }

    private TextView Title;
    private TextView Plot;
    private TextView Date_M;
    private ImageView Poster;
    private int resould_count = 0;
    private int page_count = 1;

    private SharedViewModel viewModel;
    private List<Integer> selectedGenres;
    private String selectedGen;
    private String selectedYear;

    private AppDatabase db;
    private MovieDao movieDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Room adatbázis inicializálása
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "movies.db")
                .allowMainThreadQueries()
                .build();
        movieDao = db.movieDao();

        Button like = view.findViewById(R.id.Like);
        Button next = view.findViewById(R.id.Next);
        Title = view.findViewById(R.id.TestHome);
        Plot = view.findViewById(R.id.Plot);
        Date_M = view.findViewById(R.id.Date);
        Poster = view.findViewById(R.id.Poster);
        NextMovie();

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getSelectedGenres().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> genres) {
                selectedGenres = genres;
                selectedGen = selectedGenres.toString().substring(1, selectedGenres.toString().length() - 1);
                NextMovie();
            }
        });

        viewModel.getSelectedYear().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String year) {
                selectedYear = year;
                NextMovie();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextMovie();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Az aktuális film mentése az adatbázisba

                // Következő film megjelenítése
                LikeMovie();
            }
        });

        return view;
    }

    private void NextMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieRequest movieApi = retrofit.create(MovieRequest.class);
        Call<MovieResponse> listCall = movieApi.getMovies("da731e2507284961a1e0fe66b7824496", page_count, selectedGen, selectedYear);

        listCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    List<Movies> moviesList = movieResponse.getResults();
                    if (resould_count < 20) {
                        Log.d("NetworkSuccess", "Sikeres API hívás");

                        if(MovieId(moviesList.get(resould_count).getId())==false) {
                            Title.setText(moviesList.get(resould_count).getTitle());
                            Plot.setText(moviesList.get(resould_count).getOverview());
                            Date_M.setText(moviesList.get(resould_count).getRelease_date());


                            String baseUrl = "https://image.tmdb.org/t/p/original";
                            String backdropPath = moviesList.get(resould_count).getPoster_path();
                            Glide.with(requireContext())
                                    .load(baseUrl + backdropPath)
                                    .centerCrop()
                                    .into(Poster);
                        }else
                        {
                            resould_count++;
                            NextMovie();
                        }
                        resould_count++;
                    } else {
                        resould_count = 0;
                        page_count++;
                    }
                } else {
                    Log.e("NetworkError", "Hiba a válaszban");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("NetworkError", "Hálózati hiba történt: " + t.getMessage());
                t.printStackTrace();
                Title.setText(t.getMessage());
            }
        });

    }

    private void LikeMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieRequest movieApi = retrofit.create(MovieRequest.class);
        Call<MovieResponse> listCall = movieApi.getMovies("da731e2507284961a1e0fe66b7824496", page_count, selectedGen, selectedYear);

        listCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    List<Movies> moviesList = movieResponse.getResults();
                    if (resould_count < 20) {
                        Log.d("NetworkSuccess", "Sikeres API hívás");

                        if(saveCurrentMovie(moviesList.get(resould_count).getId(),moviesList.get(resould_count).getPoster_path())==false) {
                            Title.setText(moviesList.get(resould_count).getTitle());
                            Plot.setText(moviesList.get(resould_count).getOverview());
                            Date_M.setText(moviesList.get(resould_count).getRelease_date());


                            String baseUrl = "https://image.tmdb.org/t/p/original";
                            String backdropPath = moviesList.get(resould_count).getPoster_path();
                            Glide.with(requireContext())
                                    .load(baseUrl + backdropPath)
                                    .centerCrop()
                                    .into(Poster);
                        }else
                        {
                            resould_count++;
                            LikeMovie();
                        }
                        resould_count++;
                    } else {
                        resould_count = 0;
                        page_count++;
                    }
                } else {
                    Log.e("NetworkError", "Hiba a válaszban");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("NetworkError", "Hálózati hiba történt: " + t.getMessage());
                t.printStackTrace();
                Title.setText(t.getMessage());
            }
        });
    }
    
    private boolean MovieId(int movieId) {
        List<Movie> allMovies = movieDao.getAllMovies();
        boolean Same=false;
        for(int i=0; i<allMovies.size();i++)
        {
            if(allMovies.get(i).id == movieId)
            {
                Same=true;

            }
        }

        return Same;

    }

    private boolean saveCurrentMovie(int movieId, String posterPath) {

        List<Movie> allMovies = movieDao.getAllMovies();
        boolean Same=false;
        for(int i=0; i<allMovies.size();i++)
        {
            if(allMovies.get(i).id == movieId)
            {
               Same=true;

            }
        }
        if(Same == false)
        {
            Movie movie = new Movie(movieId, posterPath);
            movieDao.insertMovie(movie);
            Toast.makeText(requireContext(), "Movie saved", Toast.LENGTH_SHORT).show();

        }
        return Same;


    }
}
