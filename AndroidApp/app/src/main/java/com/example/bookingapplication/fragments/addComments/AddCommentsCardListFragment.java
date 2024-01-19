package com.example.bookingapplication.fragments.addComments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.AddCommentsCardsListAdapter;
import com.example.bookingapplication.adapters.MyCommentsCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAddCommentsCardListBinding;
import com.example.bookingapplication.databinding.FragmentMyCommentsCardListBinding;
import com.example.bookingapplication.fragments.myComments.MyCommentsCardListFragment;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentsCardListFragment extends ListFragment {

    private AddCommentsCardsListAdapter addCommentsCardsListAdapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Accommodation> cards;
    private FragmentAddCommentsCardListBinding binding;
    public Long guestUserId;
    private AutoCompleteTextView commentTextView;
    public AddCommentsCardListFragment() {

    }

    public Long getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(Long guestUserId) {
        this.guestUserId = guestUserId;
    }

    public static AddCommentsCardListFragment newInstance(ArrayList<Accommodation> cards){
        AddCommentsCardListFragment fragment = new AddCommentsCardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.guestUserId = SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();

        binding = FragmentAddCommentsCardListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        commentTextView = binding.commentTextView;

        String[] commentInputArray = getResources().getStringArray(R.array.comment_type_options);
        ArrayAdapter<String> commentInputAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, commentInputArray);
        commentTextView.setAdapter(commentInputAdapter);

        commentTextView.setText(commentInputArray[0], false);
        prepareAccommodations();

        commentTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                if (newText.equals("Accommodation")){
                    addCommentsCardsListAdapter.setCommentForAcc(true);
                } else {
                    addCommentsCardsListAdapter.setCommentForAcc(false);
                }
                prepareAccommodations();
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cards = new ArrayList<>();
        addCommentsCardsListAdapter = new AddCommentsCardsListAdapter(getActivity(), new ArrayList<>(),
                AddCommentsCardListFragment.this);
        setListAdapter(addCommentsCardsListAdapter);
    }

    public void prepareAccommodations(){
        Call<Collection<Accommodation>> call = ClientUtils.commentsService.getAccommodationsForComment(this.guestUserId);
        call.enqueue(new Callback<Collection<Accommodation>>() {
            @Override
            public void onResponse(Call<Collection<Accommodation>> call, Response<Collection<Accommodation>> response) {
                Log.d("VRATIO", response.body().toString());
                addAccommodations((ArrayList<Accommodation>) response.body());
            }

            @Override
            public void onFailure(Call<Collection<Accommodation>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void addAccommodations(ArrayList<Accommodation> accommodations){
        this.addCommentsCardsListAdapter.clear();
        this.addCommentsCardsListAdapter.addAll(accommodations);
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