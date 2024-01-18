package com.example.bookingapplication.fragments.notificationForGuest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.NotificationForGuestAdapter;
import com.example.bookingapplication.adapters.NotificationForHostAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentNotificationForGuestCardListBinding;
import com.example.bookingapplication.databinding.FragmentNotificationForHostCardListBinding;
import com.example.bookingapplication.fragments.notificationForHost.NotificationForHostCardListFragment;
import com.example.bookingapplication.model.NotificationForGuest;
import com.example.bookingapplication.model.NotificationForHost;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationForGuestCardListFragment extends ListFragment {

    public static ArrayList<NotificationForHost> products = new ArrayList<NotificationForHost>();
    private NotificationForGuestAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<NotificationForGuest> mProducts;
    private FragmentNotificationForGuestCardListBinding binding;

    public static NotificationForGuestCardListFragment newInstance(ArrayList<NotificationForGuest> products){
        NotificationForGuestCardListFragment fragment = new NotificationForGuestCardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentNotificationForGuestCardListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareApartmentCardsList();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");
        if (getArguments() != null) {
            mProducts = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new NotificationForGuestAdapter(getActivity(), mProducts,this);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    public void prepareApartmentCardsList(){
        ProgressBar loadingProgressBar = getActivity().findViewById(R.id.loadingPanelNotificationHost);
        loadingProgressBar.setVisibility(View.VISIBLE);

        Long id = SharedPreferencesManager.getUserInfo(getContext()).getId();

        Call<List<NotificationForGuest>> call = ClientUtils.notificationService.getNotificationsForGuest(id);
        call.enqueue(new Callback<List<NotificationForGuest>>() {
            @Override
            public void onResponse(Call<List<NotificationForGuest>> call, Response<List<NotificationForGuest>> response) {
                loadingProgressBar.setVisibility(View.GONE);

                Log.i("ShopApp", String.valueOf(response.code()));
                ArrayList<NotificationForGuest> cards = new ArrayList<>(response.body());

                addProducts(cards);
            }

            @Override
            public void onFailure(Call<List<NotificationForGuest>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                Log.e("ShopApp", "Failed to fetch notifications", t);
            }
        });
    }

    private void addProducts(ArrayList<NotificationForGuest> products){
        this.adapter.clear();
        this.adapter.addAll(products);
    }
}