package com.saloonbooking.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saloonbooking.R;

public class HomeFragment extends Fragment {

    private BookingViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);

        FloatingActionButton fab = view.findViewById(R.id.fab_book_appointment);
        fab.setOnClickListener(v -> showBookingOverlay());

        return view;
    }

    private void showBookingOverlay() {
        BookingDialog dialog = new BookingDialog();
        dialog.show(requireActivity().getSupportFragmentManager(), "BookingDialog");
    }
}