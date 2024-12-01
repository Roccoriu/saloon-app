package com.saloonbooking.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saloonbooking.R;
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

            if (validate()){
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

    private boolean validate(){
        if(binding.etUserName.getText().toString().trim().isEmpty() || binding.etUserName.getText() == null){
            showCustomToast(getContext(), "Name is missing");
            binding.etUserName.setError("Name is missing");
            return false;
        }

        if(viewModel.getSelectedDate().getValue() == null){
            showCustomToast(getContext(), "Date is missing");
            return false;
        }

        if(viewModel.getSelectedServices().getValue() == null || viewModel.getSelectedServices().getValue().isEmpty()){
            showCustomToast(getContext(), "Select at least one service");
            return false;
        }

        if(viewModel.getTotalPrice().getValue() == null || viewModel.getTotalPrice().getValue() == 0){
            showCustomToast(getContext(), "Total can't be 0");
            return false;
        }

        return true;
    }

    public void showCustomToast(Context context, String message) {
        var layout = getLayoutInflater().inflate(R.layout.custom_toast, null);

        TextView textView = layout.findViewById(R.id.tv_toast_message);
        textView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
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