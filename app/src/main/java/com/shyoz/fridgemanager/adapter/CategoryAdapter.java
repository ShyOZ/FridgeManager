package com.shyoz.fridgemanager.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.shyoz.fridgemanager.data.Category;
import com.shyoz.fridgemanager.databinding.FragmentCategoryBinding;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Category}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.ViewHolder> {

    private CategoryClickListener clickListener;
    private CategoryLongClickListener longClickListener;

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Category model) {
        model.setId(getRef(position).getKey());
        holder.item = model;

        holder.text.setText(model.getDisplayName());

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCategoryClick(model);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onCategoryLongClick(model);
            }
            return true;
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView text;
        public Category item;

        public ViewHolder(FragmentCategoryBinding binding) {
            super(binding.getRoot());
            text = binding.categoryName;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + item.toString() + "'";
        }
    }

    public void setClickListener(CategoryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(CategoryLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface CategoryClickListener {
        void onCategoryClick(Category category);
    }

    public interface CategoryLongClickListener {
        void onCategoryLongClick(Category category);
    }
}