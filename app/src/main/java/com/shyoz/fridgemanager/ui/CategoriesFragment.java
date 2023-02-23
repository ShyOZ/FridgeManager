package com.shyoz.fridgemanager.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.shyoz.fridgemanager.adapter.CategoryAdapter;
import com.shyoz.fridgemanager.data.Category;
import com.shyoz.fridgemanager.data.DataManager;
import com.shyoz.fridgemanager.databinding.FragmentCategoriesBinding;
import com.shyoz.fridgemanager.model.DataModel;

import java.util.Objects;

public class CategoriesFragment extends Fragment {

    private CategoryAdapter adapter;

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

        FragmentCategoriesBinding binding = FragmentCategoriesBinding.inflate(inflater, container, false);

        Context context = binding.categoryList.getContext();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        binding.categoryList.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(DataManager.getInstance()
                        .getCategories(Objects.requireNonNull(dataModel.getStorage().getValue()).getId()), Category.class)
                .build();
        adapter = new CategoryAdapter(options);
        adapter.setClickListener(category -> {
                    dataModel.setCategory(category);
                    Navigation.findNavController(this.requireView()).navigate(CategoriesFragmentDirections.actionToItems());
                }
        );

        adapter.startListening();
        binding.categoryList.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}