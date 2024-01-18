package com.example.bookingapplication.fragments.myComments;

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
import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.adapters.MyCommentsCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentCommentCardListBinding;
import com.example.bookingapplication.databinding.FragmentMyCommentsCardListBinding;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCommentsCardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCommentsCardListFragment extends ListFragment {

    private MyCommentsCardsListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<CommentCard> cards;
    private FragmentMyCommentsCardListBinding binding;
    private Long guestUserId;
    private AutoCompleteTextView commentTextView;
    public MyCommentsCardListFragment() {

    }

    public static MyCommentsCardListFragment newInstance(ArrayList<CommentCard> cards){
        MyCommentsCardListFragment fragment = new MyCommentsCardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.guestUserId = SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();

        binding = FragmentMyCommentsCardListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        commentTextView = binding.commentTextView;

        String[] commentInputArray = getResources().getStringArray(R.array.comment_type_options);
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
        cards = new ArrayList<>();
        adapter = new MyCommentsCardsListAdapter(getActivity(), new ArrayList<>(),
                MyCommentsCardListFragment.this);
        setListAdapter(adapter);
    }

    public void prepareCardsAccList(){

        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsAboutAccForGuest(this.guestUserId);
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

    public void prepareCardsHostList(){

        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsAboutHostForGuest(this.guestUserId);
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
        for (CommentCard commentCard: products) {
            Log.d("PRIMIO", commentCard.getId().toString());
        }
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