package com.example.bookingapplication.fragments.reportedUsers;

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
import com.example.bookingapplication.adapters.ReportedUserListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentReportedUsersCardsListBinding;
import com.example.bookingapplication.model.UserBlock;
import com.example.bookingapplication.model.UserBlockCard;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedUsersCardsListFragment extends ListFragment {

    public static ArrayList<UserBlockCard> products = new ArrayList<UserBlockCard>();
    private ReportedUserListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<UserBlockCard> mProducts;
    private FragmentReportedUsersCardsListBinding binding;

    public static ReportedUsersCardsListFragment newInstance(ArrayList<UserBlockCard> products){
        ReportedUsersCardsListFragment fragment = new ReportedUsersCardsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentReportedUsersCardsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareApartmentCardsList();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i("ShopApp", "onCreate Products List Fragment");
        if (getArguments() != null) {
            mProducts = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReportedUserListAdapter(getActivity(), mProducts,this);
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
        ProgressBar loadingProgressBar = getActivity().findViewById(R.id.loadingPanelReportedUsers);
        loadingProgressBar.setVisibility(View.VISIBLE);

        Call<List<UserBlock>> call = ClientUtils.userService.getReportedUsers();
        call.enqueue(new Callback<List<UserBlock>>() {
            @Override
            public void onResponse(Call<List<UserBlock>> call, Response<List<UserBlock>> response) {
                loadingProgressBar.setVisibility(View.GONE);
                Log.d("Odgovor",String.valueOf(response.code()));

                ArrayList<UserBlockCard> cards = new ArrayList<>();
                Log.d("Kartice","Konzaa");
                for (UserBlock card : response.body()) {

                    UserBlockCard ac = new UserBlockCard(card);

                    cards.add(ac);

                }

                addProducts(cards);
            }

            @Override
            public void onFailure(Call<List<UserBlock>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void addProducts(ArrayList<UserBlockCard> productss){
        this.adapter.clear();
        this.adapter.addAll(productss);
    }
}