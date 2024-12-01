package com.saloonbooking.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saloonbooking.R;
import com.saloonbooking.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private BookingViewModel viewModel;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding.fabBookAppointment.setOnClickListener(v -> showBookingOverlay());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var recyclerView = binding.rvBookings;
        var adapter = new BookingAdapter(new ArrayList<>());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        homeViewModel.getBookingHistory().observe(getViewLifecycleOwner(), adapter::updateBookings);
    }

    private void showBookingOverlay() {
        var dialog = new BookingDialog();
        dialog.show(requireActivity().getSupportFragmentManager(), "BookingDialog");
    }
}