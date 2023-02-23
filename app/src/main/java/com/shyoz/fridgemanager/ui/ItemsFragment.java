package com.shyoz.fridgemanager.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.shyoz.fridgemanager.adapter.ItemAdapter;
import com.shyoz.fridgemanager.data.DataManager;
import com.shyoz.fridgemanager.data.Item;
import com.shyoz.fridgemanager.databinding.FragmentItemsBinding;
import com.shyoz.fridgemanager.model.DataModel;

import java.util.Objects;

public class ItemsFragment extends Fragment {

    ItemAdapter adapter;

    private DataModel dataModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentItemsBinding binding = FragmentItemsBinding.inflate(inflater, container, false);

        Context context = binding.itemList.getContext();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        binding.itemList.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(DataManager.getInstance()
                        .getItems(Objects.requireNonNull(dataModel.getCategory().getValue()).getId()), Item.class)
                .build();

        adapter = new ItemAdapter(options);
        adapter.setClickListener(item -> {
            Log.d("ItemsFragment", "Item clicked: " + item.getDisplayName());
        });

        adapter.startListening();
        binding.itemList.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}