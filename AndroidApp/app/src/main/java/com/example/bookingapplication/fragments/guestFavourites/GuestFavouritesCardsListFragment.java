package com.example.bookingapplication.fragments.guestFavourites;

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
import com.example.bookingapplication.adapters.ApartmentCardsListAdapter;
import com.example.bookingapplication.adapters.FavouritesAccListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApartmentCardsListBinding;
import com.example.bookingapplication.databinding.FragmentGuestFavouritesCardsListBinding;
import com.example.bookingapplication.fragments.home.ApartmentCardsListFragment;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestFavouritesCardsListFragment extends ListFragment {

    public static ArrayList<ApartmentCard> products = new ArrayList<ApartmentCard>();
    private FavouritesAccListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<ApartmentCard> mProducts;
    private FragmentGuestFavouritesCardsListBinding binding;

    public static GuestFavouritesCardsListFragment newInstance(ArrayList<ApartmentCard> products){
        GuestFavouritesCardsListFragment fragment = new GuestFavouritesCardsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentGuestFavouritesCardsListBinding.inflate(inflater, container, false);
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
            adapter = new FavouritesAccListAdapter(getActivity(), mProducts);
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

    private void prepareApartmentCardsList(){
        ProgressBar loadingProgressBar = getActivity().findViewById(R.id.loadingPanelGuestFavourites);
        loadingProgressBar.setVisibility(View.VISIBLE);

        Long id = SharedPreferencesManager.getUserInfo(getContext()).getId();
        Call<List<Card>> call = ClientUtils.guestService.getFavouritesAccommodations(id);
        call.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                loadingProgressBar.setVisibility(View.GONE);
                Log.d("Odgovor",String.valueOf(response.code()));

                ArrayList<ApartmentCard> cards = new ArrayList<>();
                for (Card card : response.body()) {
                    String rate;
                    if(card.getAvgRate() == null){
                        rate = "";
                    } else {
                        rate = card.getAvgRate().toString();
                    }
                    ApartmentCard ac = new ApartmentCard(card.getId(), card.getTitle(), card.getAddress().toString(), rate, card.getImage());

                    cards.add(ac);

                }

                addProducts(cards);
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);

            }
        });
    }

    private void addProducts(ArrayList<ApartmentCard> productss){
        this.adapter.clear();
        this.adapter.addAll(productss);
    }
}