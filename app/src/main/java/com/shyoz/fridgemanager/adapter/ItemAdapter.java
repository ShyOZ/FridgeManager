package com.shyoz.fridgemanager.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.shyoz.fridgemanager.data.Item;
import com.shyoz.fridgemanager.databinding.FragmentItemBinding;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ItemAdapter extends FirebaseRecyclerAdapter<Item, ItemAdapter.ViewHolder> {

    private ItemClickListener clickListener;

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model) {
        model.setId(getRef(position).getKey());
        holder.item = model;
        holder.name.setText(model.getDisplayName());
        holder.added.setText(model.getAdded());
        holder.expiration.setText(model.getExpiration());
        String quantity = model.getQuantity() + " " + model.getUnit();
        holder.quantity.setText(quantity);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        private final TextView added;
        private final TextView expiration;
        private final TextView quantity;

        public Item item;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            name = binding.itemDisplayName;
            added = binding.added;
            expiration = binding.expiration;
            quantity = binding.quantity;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + item.toString() + "'";
        }
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClicked(Item item);
    }
}