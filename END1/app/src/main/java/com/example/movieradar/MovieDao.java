package com.example.movieradar;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insertMovie(Movie movie);

    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie getMovieById(int id);
    @Query("DELETE FROM Movie WHERE id = :id")
    void deleteMovieById(int id);

}