package com.example.movieradar;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    @PrimaryKey
    public int id;

    public String posterPath;

    public Movie(int id, String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
    }
}
