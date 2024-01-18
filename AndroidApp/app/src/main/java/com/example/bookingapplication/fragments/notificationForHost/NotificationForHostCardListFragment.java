package com.example.bookingapplication.fragments.notificationForHost;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.NotificationForHostAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentNotificationForHostCardListBinding;
import com.example.bookingapplication.model.NotificationForHost;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationForHostCardListFragment extends ListFragment {

    public static ArrayList<NotificationForHost> products = new ArrayList<NotificationForHost>();
    private NotificationForHostAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<NotificationForHost> mProducts;
    private FragmentNotificationForHostCardListBinding binding;

    public static NotificationForHostCardListFragment newInstance(ArrayList<NotificationForHost> products){
        NotificationForHostCardListFragment fragment = new NotificationForHostCardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentNotificationForHostCardListBinding.inflate(inflater, container, false);
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
            adapter = new NotificationForHostAdapter(getActivity(), mProducts,this);
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

        Call<List<NotificationForHost>> call = ClientUtils.notificationService.getNotificationsForHost(id);
        call.enqueue(new Callback<List<NotificationForHost>>() {
            @Override
            public void onResponse(Call<List<NotificationForHost>> call, Response<List<NotificationForHost>> response) {
                loadingProgressBar.setVisibility(View.GONE);

                Log.i("ShopApp", String.valueOf(response.code()));
                ArrayList<NotificationForHost> cards = new ArrayList<>(response.body());


                addProducts(cards);

            }

            @Override
            public void onFailure(Call<List<NotificationForHost>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                Log.e("ShopApp", "Failed to fetch notifications", t);
            }
        });
    }

    private void addProducts(ArrayList<NotificationForHost> products){
        this.adapter.clear();
        this.adapter.addAll(products);
    }
}