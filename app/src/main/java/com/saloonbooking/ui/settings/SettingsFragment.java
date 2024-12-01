package com.saloonbooking.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.saloonbooking.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var view = FragmentSettingsBinding.inflate(inflater, container, false);

        // Set App Version dynamically
        TextView appVersionTextView = view.tvAppVersion;
        appVersionTextView.setText("Version: 0.1.0");

        // Set up actions for buttons
        var termsButton = view.btnTermsConditions;
        var privacyButton = view.btnPrivacyPolicy;

        termsButton.setOnClickListener(v -> openUrl("https://example.com/terms"));
        privacyButton.setOnClickListener(v -> openUrl("https://example.com/privacy"));

        return view.getRoot();
    }

    private void openUrl(String url) {
        var intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}