package com.example.bookingapplication.fragments.addedProperties;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.bookingapplication.adapters.AccommodationApprovingListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAddedPropertiesBinding;
import com.example.bookingapplication.model.AccommodationApprovingCard;
import com.example.bookingapplication.model.Card;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddedPropertiesFragment  extends ListFragment {

    public static ArrayList<AccommodationApprovingCard> products = new ArrayList<AccommodationApprovingCard>();
    private AccommodationApprovingListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<AccommodationApprovingCard> mProducts;
    private FragmentAddedPropertiesBinding binding;

    public static AddedPropertiesFragment newInstance(ArrayList<AccommodationApprovingCard> products){
        AddedPropertiesFragment fragment = new AddedPropertiesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentAddedPropertiesBinding.inflate(inflater, container, false);
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
            adapter = new AccommodationApprovingListAdapter(getActivity(), mProducts);
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

                ArrayList<AccommodationApprovingCard> cards = new ArrayList<>();
                for (Card card : response.body()) {
                    String rate;
                    if(card.getAvgRate() == null){
                        rate = "";
                    } else {
                        rate = card.getAvgRate().toString();
                    }
                    AccommodationApprovingCard ac = new AccommodationApprovingCard(1L,card.getTitle(),card.getAddress(), 0,card.getDescription());
//                    cards.add(ac);
                }

                Log.d("Nesto", String.valueOf(cards.size()));
                addProducts(cards);
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("FAIL", "Nesto ne valja");
                Log.d("KARD", call.toString());
            }
        });
    }

    private void addProducts(ArrayList<AccommodationApprovingCard> productss){
        this.adapter.addAll(productss);
    }

}