package com.saloonbooking.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.saloonbooking.R;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<Booking> bookings;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void updateBookings(List<Booking> newBookings) {
        this.bookings = newBookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_history, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        var booking = bookings.get(position);
        holder.userNameTextView.setText(booking.getUser());
        holder.selectedDateTextView.setText("Date: " + booking.getDate());
        holder.totalPriceTextView.setText("Total: ₵" + booking.getTotalPrice());
        holder.selectedServicesTextView.setText("Services: " + String.join(", ", booking.getServices()));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView, selectedDateTextView, totalPriceTextView, selectedServicesTextView;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.tv_user_name);
            selectedDateTextView = itemView.findViewById(R.id.tv_selected_date);
            totalPriceTextView = itemView.findViewById(R.id.tv_total_price);
            selectedServicesTextView = itemView.findViewById(R.id.tv_selected_services);
        }
    }
}