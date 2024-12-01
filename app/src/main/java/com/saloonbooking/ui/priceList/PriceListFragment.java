package com.saloonbooking.ui.priceList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saloonbooking.R;
import com.saloonbooking.databinding.FragmentPricelistBinding;

import java.util.List;

public class PriceListFragment extends Fragment {
    private FragmentPricelistBinding binding;
    private PriceListViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPricelistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PriceListViewModel.class);

        var recyclerView = binding.rvServices;
        var adapter = new ServiceAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getServices().observe(getViewLifecycleOwner(), adapter::setServices);
    }
}