package com.example.bookingapplication.fragments.approveComments;

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
import com.example.bookingapplication.adapters.ApproveCommentCardsListAdapter;
import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApproveCommentsCardListBinding;
import com.example.bookingapplication.databinding.FragmentCommentCardListBinding;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;
import com.example.bookingapplication.model.CommentCard;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApproveCommentsCardListFragment extends ListFragment {

    private ApproveCommentCardsListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<CommentCard> cards;
    private FragmentApproveCommentsCardListBinding  binding;
    private Long accommodationId;
    private Long hostId;
    private AutoCompleteTextView hostAccOption;
    private AutoCompleteTextView approveReportOption;

    public ApproveCommentsCardListFragment() {
        // Required empty public constructor
    }

    public static ApproveCommentsCardListFragment newInstance(ArrayList<CommentCard> cards){
        ApproveCommentsCardListFragment fragment = new ApproveCommentsCardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentApproveCommentsCardListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            accommodationId = getArguments().getLong("accommodationId");
            hostId = getArguments().getLong("hostId");
        }

        approveReportOption = binding.approveReportOption;

        String[] commentInputArray = getResources().getStringArray(R.array.approve_comments_options);
        ArrayAdapter<String> commentInputAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, commentInputArray);
        approveReportOption.setAdapter(commentInputAdapter);

        approveReportOption.setText(commentInputArray[0], false);
        prepareCommentsApproving();

        approveReportOption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                if (newText.equals("Approve")){
                    prepareCommentsApproving();
                    hostAccOption.setVisibility(View.GONE);
                } else {
                    prepareReportedCommentsAcc();
                    hostAccOption.setVisibility(View.VISIBLE);
                }

            }
        });

        hostAccOption = binding.hostAccOption;
        hostAccOption.setVisibility(View.GONE);
        String[] reportInputArray = getResources().getStringArray(R.array.comment_type_options);
        ArrayAdapter<String> reportInputAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, reportInputArray);
        hostAccOption.setAdapter(reportInputAdapter);

        hostAccOption.setText(reportInputArray[0], false);

        hostAccOption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                if (newText.equals("Accommodation")){
                    prepareReportedCommentsAcc();
                } else {
                    prepareReportedCommentsHost();
                }

            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cards = new ArrayList<>();
        adapter = new ApproveCommentCardsListAdapter(getActivity(), new ArrayList<>(), ApproveCommentsCardListFragment.this);
        setListAdapter(adapter);
    }

    public void prepareCommentsApproving(){

        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsForApproving();
        call.enqueue(new Callback<Collection<CommentCard>>() {
            @Override
            public void onResponse(Call<Collection<CommentCard>> call, Response<Collection<CommentCard>> response) {
                Log.d("Hej", response.body().toString());
                addProducts((ArrayList<CommentCard>) response.body());
            }

            @Override
            public void onFailure(Call<Collection<CommentCard>> call, Throwable t) {
                Log.d("Hej1", t.toString());
            }
        });
    }

    public void prepareReportedCommentsAcc(){

        Log.d("Pozvao", "pozvan");
        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getReportedCommentsAcc();
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

    public void prepareReportedCommentsHost(){

        Log.d("Pozvao", "pozvan");
        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getReportedCommentsHost();
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