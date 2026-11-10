package com.unlam.followare.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.ItemMateriaBinding;
import com.unlam.followare.models.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriasAdapter extends RecyclerView.Adapter<MateriasAdapter.ViewHolder> {

    public interface OnMateriaClickListener {
        void onMateriaClick(Materia materia);
    }

    private final FollowareRepository repository;
    private final OnMateriaClickListener listener;
    private List<Materia> items = new ArrayList<>();

    public MateriasAdapter(Context context, OnMateriaClickListener listener) {
        this.repository = FollowareRepository.getInstance(context);
        this.listener = listener;
    }

    public void setItems(List<Materia> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMateriaBinding binding = ItemMateriaBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMateriaBinding binding;

        ViewHolder(ItemMateriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Materia materia) {
            binding.tvMateriaTitle.setText(materia.getNombre());
            binding.tvMateriaDocente.setText(materia.getComision() + " • " + materia.getDocente());
            binding.tvMateriaHorario.setText(materia.getHorario());

            int unread = repository.getUnreadCountByMateria(materia.getId());
            if (unread > 0) {
                binding.tvMateriaBadge.setVisibility(View.VISIBLE);
                binding.tvMateriaBadge.setText(unread + " sin leer");
            } else {
                binding.tvMateriaBadge.setVisibility(View.GONE);
            }

            binding.getRoot().setOnClickListener(v -> listener.onMateriaClick(materia));
        }
    }
}
