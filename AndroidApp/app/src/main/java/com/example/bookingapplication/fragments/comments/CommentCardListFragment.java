package com.example.bookingapplication.fragments.comments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApartmentCardsListBinding;
import com.example.bookingapplication.databinding.FragmentCommentCardListBinding;
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


public class CommentCardListFragment extends ListFragment {

    private CommentCardsListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<CommentCard> cards;
    private FragmentCommentCardListBinding binding;
    private Long accommodationId;
    private Long hostId;
    private AutoCompleteTextView commentTextView;
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

        binding = FragmentCommentCardListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            accommodationId = getArguments().getLong("accommodationId");
            hostId = getArguments().getLong("hostId");
        }

        commentTextView = binding.commentTextView;
        String[] commentInputArray = getResources().getStringArray(R.array.spinner_options);
        ArrayAdapter<String> commentInputAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, commentInputArray);
        commentTextView.setAdapter(commentInputAdapter);
        commentTextView.setText(commentInputArray[0], false);
        prepareCardsAccList();
        commentTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                if (newText.equals("Accommodation")){
                    prepareCardsAccList();
                } else {
                    prepareCardsHostList();
                }

            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cards = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new CommentCardsListAdapter(getActivity(), new ArrayList<>());
            setListAdapter(adapter);
        }
    }

    private void prepareCardsAccList(){

        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsAboutAcc(accommodationId);
        call.enqueue(new Callback<Collection<CommentCard>>() {
            @Override
            public void onResponse(Call<Collection<CommentCard>> call, Response<Collection<CommentCard>> response) {
                Log.d("response", response.body().toString());
                addProducts((ArrayList<CommentCard>) response.body());
            }

            @Override
            public void onFailure(Call<Collection<CommentCard>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void prepareCardsHostList(){

        Log.d("Pozvao", "pozvan");
        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsAboutHost(hostId);
        call.enqueue(new Callback<Collection<CommentCard>>() {
            @Override
            public void onResponse(Call<Collection<CommentCard>> call, Response<Collection<CommentCard>> response) {
                Log.d("response", response.body().toString());
                addProducts((ArrayList<CommentCard>) response.body());
            }

            @Override
            public void onFailure(Call<Collection<CommentCard>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }
    private void addProducts(ArrayList<CommentCard> products){
        this.adapter.clear();
        this.adapter.addAll(products);
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

}