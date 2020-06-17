package com.kajoba.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kajoba.app.R;

import static androidx.navigation.Navigation.findNavController;

public class HomeFragment extends Fragment {

    private CardView cvSales, cvInventory, cvClients, cvLoans, cvReports, cvBilling, cvBusinessman, cvPartners;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setup views
        setupViews(view);

        cvSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_salesFragment);
            }
        });
        cvInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_inventoryFragment);
            }
        });
        cvClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_clientsFragment);
            }
        });
        cvLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_loansFragment);
            }
        });
        cvReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_reportsFragment);
            }
        });
        cvBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_billingFragment);
            }
        });
        cvBusinessman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_businessmanFragment);
            }
        });
        cvPartners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_navigation_home_to_partnersFragment);
            }
        });

    }

    /**
     * Method for initialize all views
     * @param view
     */
    private void setupViews(View view) {
        cvSales = view.findViewById(R.id.cardViewSales);
        cvInventory = view.findViewById(R.id.cardViewInventory);
        cvClients = view.findViewById(R.id.cardViewClients);
        cvLoans = view.findViewById(R.id.cardViewLoans);
        cvReports = view.findViewById(R.id.cardViewReports);
        cvBilling = view.findViewById(R.id.cardViewBilling);
        cvBusinessman = view.findViewById(R.id.cardViewBusinessman);
        cvPartners = view.findViewById(R.id.cardViewPartners);
    }
}
