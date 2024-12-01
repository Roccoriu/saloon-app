package com.saloonbooking.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saloonbooking.R;

import java.util.List;

public class SelectableServiceAdapter extends RecyclerView.Adapter<SelectableServiceAdapter.ServiceViewHolder> {
    private final List<SelectableService> services;
    private final BookingViewModel viewModel;

    public SelectableServiceAdapter(List<SelectableService> services, BookingViewModel viewModel) {
        this.services = services;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectable_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        SelectableService service = services.get(position);
        holder.serviceNamePrice.setText(service.getName() + " - ₵" + service.getPrice());

        // Get the current list of selected services
        List<SelectableService> selectedServices = viewModel.getSelectedServices().getValue();
        boolean isSelected = selectedServices != null && selectedServices.contains(service);

        holder.serviceCheckBox.setChecked(isSelected);

        holder.serviceCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleServiceSelection(service); // Call the correct method
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        CheckBox serviceCheckBox;
        TextView serviceNamePrice;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceCheckBox = itemView.findViewById(R.id.cb_service);
            serviceNamePrice = itemView.findViewById(R.id.tv_service_name_price);
        }
    }
}