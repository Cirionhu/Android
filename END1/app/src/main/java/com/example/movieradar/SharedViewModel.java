package com.example.movieradar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Integer>> selectedGenres = new MutableLiveData<>();
    private final MutableLiveData<String> selectedYear = new MutableLiveData<>();

    public void setSelectedGenres(List<Integer> genres) {
        selectedGenres.setValue(genres);
    }

    public LiveData<List<Integer>> getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedYear(String year) {
        selectedYear.setValue(year);
    }

    public LiveData<String> getSelectedYear() {
        return selectedYear;
    }
}
