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
import com.example.bookingapplication.databinding.FragmentApartmentCardsListBinding;
import com.example.bookingapplication.model.ApartmentCard;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentCardsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentCardsListFragment extends ListFragment {

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

}