package com.example.movieradar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Search extends Fragment {

    private CheckBox checkBoxAction, checkBoxAdventure, checkBoxAnimation, checkBoxComedy, checkBoxCrime,
            checkBoxDrama, checkBoxFamily, checkBoxFantasy, checkBoxHorror, checkBoxRomance;
    private Spinner yearSpinner;
    private Button submitButton;

    private final int[] genreIds = {
            28, 12, 16, 35, 80,
            18, 10751, 14, 27, 10749
    };

    private SharedViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize CheckBoxes
        checkBoxAction = view.findViewById(R.id.checkBoxAction);
        checkBoxAdventure = view.findViewById(R.id.checkBoxAdventure);
        checkBoxAnimation = view.findViewById(R.id.checkBoxAnimation);
        checkBoxComedy = view.findViewById(R.id.checkBoxComedy);
        checkBoxCrime = view.findViewById(R.id.checkBoxCrime);
        checkBoxDrama = view.findViewById(R.id.checkBoxDrama);
        checkBoxFamily = view.findViewById(R.id.checkBoxFamily);
        checkBoxFantasy = view.findViewById(R.id.checkBoxFantasy);
        checkBoxHorror = view.findViewById(R.id.checkBoxHorror);
        checkBoxRomance = view.findViewById(R.id.checkBoxRomance);

        // Initialize Spinner
        yearSpinner = view.findViewById(R.id.yearSpinner);
        populateYearSpinner();

        // Initialize Button
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();

            }
        });

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    private void populateYearSpinner() {
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("");
        for (int i = currentYear; i >= 1950; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
    }

    private void handleSubmit() {
        List<Integer> selectedGenres = new ArrayList<>();

        if (checkBoxAction.isChecked()) selectedGenres.add(genreIds[0]);
        if (checkBoxAdventure.isChecked()) selectedGenres.add(genreIds[1]);
        if (checkBoxAnimation.isChecked()) selectedGenres.add(genreIds[2]);
        if (checkBoxComedy.isChecked()) selectedGenres.add(genreIds[3]);
        if (checkBoxCrime.isChecked()) selectedGenres.add(genreIds[4]);
        if (checkBoxDrama.isChecked()) selectedGenres.add(genreIds[5]);
        if (checkBoxFamily.isChecked()) selectedGenres.add(genreIds[6]);
        if (checkBoxFantasy.isChecked()) selectedGenres.add(genreIds[7]);
        if (checkBoxHorror.isChecked()) selectedGenres.add(genreIds[8]);
        if (checkBoxRomance.isChecked()) selectedGenres.add(genreIds[9]);

        int[] genreArray = new int[selectedGenres.size()];
        for (int i = 0; i < selectedGenres.size(); i++) {
            genreArray[i] = selectedGenres.get(i);
        }

        String selectedYear = yearSpinner.getSelectedItem().toString();

        viewModel.setSelectedGenres(selectedGenres);
        viewModel.setSelectedYear(selectedYear);

        Toast.makeText(getContext(), "Go to Home page" + selectedYear, Toast.LENGTH_LONG).show();


    }
}


