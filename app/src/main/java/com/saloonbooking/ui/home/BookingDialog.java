package com.saloonbooking.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saloonbooking.R;

public class BookingDialog extends DialogFragment {
    private BookingViewModel viewModel;
    private HomeViewModel homeViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_booking_overlay, null, false);

        viewModel = new ViewModelProvider(requireActivity()).get(BookingViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        EditText userNameInput = view.findViewById(R.id.et_user_name);
        ImageButton selectDateButton = view.findViewById(R.id.btn_select_date);
        TextView totalPriceText = view.findViewById(R.id.tv_total_price);
        RecyclerView servicesRecyclerView = view.findViewById(R.id.rv_services);

        // Set up RecyclerView for services
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        var adapter = new SelectableServiceAdapter(viewModel.getServices().getValue(), viewModel);
        servicesRecyclerView.setAdapter(adapter);

        // Observe user input
        userNameInput.setText(viewModel.getUserName().getValue());
        userNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action required
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action required
            }
        });

        // Set up date picker
        selectDateButton.setOnClickListener(v -> DatePickerHelper.showDatePicker(
                requireContext(),
                date -> viewModel.setSelectedDate(date)
        ));

        // Observe total price updates
        viewModel.getTotalPrice().observe(this, totalPrice -> totalPriceText.setText("Total: ₵" + totalPrice));

        // Set up action buttons
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());

        // TODO: remove the values from the inputs and the view model for the next click
        view.findViewById(R.id.btn_book).setOnClickListener(v -> {
            var date = viewModel.getSelectedDate().getValue();
            var user = viewModel.getUserName().getValue();
            var services = viewModel.getSelectedServices().getValue().stream().map(el -> el.getName()).toArray(String[]::new);
            var totalPrice = viewModel.getTotalPrice().getValue();

            homeViewModel.addBooking(new Booking(date, user,services, totalPrice));

            dismiss();
        });

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();
    }
}