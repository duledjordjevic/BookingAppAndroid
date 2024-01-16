package com.example.bookingapplication.fragments.comments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApartmentCardsListBinding;
import com.example.bookingapplication.fragments.home.ApartmentCardsListFragment;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentCardListFragment extends Fragment {

    private CommentCardsListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<CommentCard> cards;
    private FragmentApartmentCardsListBinding binding;
    private Long accommodationId;
    private Long hostId;
    public CommentCardListFragment() {

    }

    public static CommentCardListFragment newInstance(ArrayList<CommentCard> cards){
        CommentCardListFragment fragment = new CommentCardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentApartmentCardsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            accommodationId = getArguments().getLong("accommodationId");
            hostId = getArguments().getLong("hostId");
        }

//        prepareApartmentCardsList();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i("ShopApp", "onCreate Products List Fragment");
        if (getArguments() != null) {
            cards = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new CommentCardsListAdapter(getActivity(), cards);
//            setListAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void prepareCardsList(Map<String, String> queryParams){
//
//        queryParams.put("guestId", SharedPreferencesManager.getUserInfo(getContext()).getId().toString());
//        Log.d("USAO", SharedPreferencesManager.getUserInfo(getContext()).getId().toString());
//        Call<Collection<Reservation>> call = ClientUtils.reservationService.getFilteredReservationsForGuest( queryParams);
//        call.enqueue(new Callback<Collection<Reservation>>() {
//            @Override
//            public void onResponse(Call<Collection<Reservation>> call, Response<Collection<Reservation>> response) {
//                Log.d("USAO", "USAO1");
//                Log.d("Response", String.valueOf(response.code()));
////                Log.d("Response", response.body().toString());
//                ArrayList<ReservationGuestCard> cards = new ArrayList<>();
//                for (Reservation reservation : response.body()) {
//                    ReservationGuestCard card = new ReservationGuestCard(reservation);
//                    cards.add(card);
//                }
//                addProducts(cards);
//            }
//
//            @Override
//            public void onFailure(Call<Collection<Reservation>> call, Throwable t) {
//                Log.d("USAO", "USAO2");
//                Log.d("Fail", t.toString());
//                Log.d("Fail", "Hello");
//            }
//        });
//    }
//    @Override
//    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//    }
//
//    private void prepareApartmentCardsList(){
//        getAccommodations();
//        Log.d("PrintKartica","alooo");
//    }
//
//    private void getAccommodations(){
//        User user = SharedPreferencesManager.getUserInfo(getContext());
//        if(user.getUserRole().equals(UserType.GUEST)){
//            getAccommodationsForGuest(user.getId());
//        }else{
//            getAccommodationsWithoutLike();
//        }
//    }


    private void addProducts(ArrayList<CommentCard> products){
        this.adapter.clear();
        this.adapter.addAll(products);
    }
}