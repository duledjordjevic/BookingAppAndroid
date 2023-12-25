package com.example.bookingapplication.fragments.home;

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

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.ApartmentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApartmentCardsListBinding;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.Card;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentCardsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentCardsListFragment extends ListFragment {

    public static ArrayList<ApartmentCard> products = new ArrayList<ApartmentCard>();
    private ApartmentCardsListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<ApartmentCard> mProducts;
    private FragmentApartmentCardsListBinding binding;

    public static ApartmentCardsListFragment newInstance(ArrayList<ApartmentCard> products){
        ApartmentCardsListFragment fragment = new ApartmentCardsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentApartmentCardsListBinding.inflate(inflater, container, false);
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
            adapter = new ApartmentCardsListAdapter(getActivity(), mProducts);
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
        Call<List<Card>> call = ClientUtils.apartmentService.getAccommodationsCards();
        call.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                Log.d("Response", String.valueOf(response.code()));
                String originalString = response.body().get(0).getImage();
                int stringLength = originalString.length();
                int startIndex = Math.max(0, stringLength - 10); // Određivanje početnog indeksa

                String last100Characters = originalString.substring(startIndex);
                Log.d("Duzina", String.valueOf(stringLength));
                Log.d("Tag", "Zadnjih 100 karaktera: " + last100Characters);


                Log.d("Image", response.body().get(0).getImage());
                ArrayList<ApartmentCard> cards = new ArrayList<>();
                for (Card card : response.body()) {
                    String rate;
                    if(card.getAvgRate() == null){
                        rate = "";
                    } else {
                        rate = card.getAvgRate().toString();
                    }
                    ApartmentCard ac = new ApartmentCard(card.getId(), card.getTitle(), card.getAddress().toString(), rate, card.getImage());
                    Log.d("Slika: ", card.getImage());
                    cards.add(ac);
                }

                Log.d("Nesto", String.valueOf(cards.size()));
                addProducts(cards);
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("Fail", "Nesto ne valja");
                Log.d("kard", call.toString());
            }
        });
    }

    private void addProducts(ArrayList<ApartmentCard> productss){
//        this.products.addAll(productss);
        this.adapter.addAll(productss);
    }


}