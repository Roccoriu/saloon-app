package com.saloonbooking.ui.priceList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saloonbooking.R;
import com.saloonbooking.ui.priceList.PriceListViewModel.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private final List<Service> services = new ArrayList<>();

    public void setServices(List<Service> services) {
        this.services.clear();
        this.services.addAll(services);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = services.get(position);
        holder.serviceName.setText(service.name);
        holder.servicePrice.setText(service.price);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        final TextView serviceName;
        final TextView servicePrice;

        ServiceViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.tv_service_name);
            servicePrice = itemView.findViewById(R.id.tv_service_price);
        }
    }
}