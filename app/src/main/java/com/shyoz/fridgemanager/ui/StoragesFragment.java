package com.shyoz.fridgemanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shyoz.fridgemanager.R;
import com.shyoz.fridgemanager.databinding.FragmentStoragesBinding;
import com.shyoz.fridgemanager.model.DataModel;


public class StoragesFragment extends Fragment {

    private DataModel dataModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_storages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentStoragesBinding binding = FragmentStoragesBinding.bind(view);

//        binding.categoriesButton.setOnClickListener(v ->
//        {
//            Navigation.findNavController(v).navigate(StoragesFragmentDirections.actionToCategories());
//        });
    }
}