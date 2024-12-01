package com.saloonbooking.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saloonbooking.databinding.DialogBookingOverlayBinding;

public class BookingDialog extends DialogFragment {
    private BookingViewModel viewModel;
    private HomeViewModel homeViewModel;
    private DialogBookingOverlayBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogBookingOverlayBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(requireActivity()).get(BookingViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding.rvServices.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvServices.setAdapter(new SelectableServiceAdapter(viewModel.getServices().getValue(), viewModel));

        binding.etUserName.setText(viewModel.getUserName().getValue());
        binding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        binding.btnSelectDate.setOnClickListener(v -> DatePickerHelper.showDatePicker(
                requireContext(),
                date -> viewModel.setSelectedDate(date)
        ));

        viewModel.getTotalPrice().observe(this, totalPrice -> binding.tvTotalPrice.setText("Total: ₵" + totalPrice));

        binding.btnCancel.setOnClickListener(v -> dismiss());

        binding.btnBook.setOnClickListener(v -> {
            var date = viewModel.getSelectedDate().getValue();
            var user = viewModel.getUserName().getValue();
            var totalPrice = viewModel.getTotalPrice().getValue();
            var services = viewModel.getSelectedServices().getValue();

            if (services != null && totalPrice != null && date != null && user != null){
                homeViewModel.addBooking(
                        new Booking(
                                date,
                                user,
                                services.
                                        stream().
                                        map(el -> el != null ? el.getName() : "")
                                        .toArray(String[]::new),
                                totalPrice)
                );

                reset();
                dismiss();
            }
        });


        return new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .create();
    }

    private void reset(){
        binding.etUserName.setText("");
        viewModel.setUserName("");

        viewModel.setSelectedDate(null);

        if (viewModel.getSelectedServices().getValue() != null) {
            viewModel.getSelectedServices().getValue().clear();
        }

        if (binding.rvServices.getAdapter() != null) {
            binding.rvServices.getAdapter().notifyDataSetChanged();
        }

        viewModel.setTotalPrice(0);
        binding.tvTotalPrice.setText("Total: ₵0.00");
    }
}