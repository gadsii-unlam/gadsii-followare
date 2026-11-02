package com.unlam.followare.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unlam.followare.R;
import com.unlam.followare.databinding.ItemPlanBinding;
import com.unlam.followare.models.MateriaPlan;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    public interface OnPlanClickListener {
        void onPlanClick(MateriaPlan plan);
    }

    private final Context context;
    private final OnPlanClickListener listener;
    private List<MateriaPlan> items = new ArrayList<>();

    public PlanAdapter(Context context, OnPlanClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setItems(List<MateriaPlan> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlanBinding binding = ItemPlanBinding.inflate(
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
        private final ItemPlanBinding binding;

        ViewHolder(ItemPlanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MateriaPlan plan) {
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onPlanClick(plan);
            });

            binding.tvPlanCodigo.setText(plan.getCodigo() + " • " + plan.getAnio() + "° Año (" + plan.getCuatrimestre() + "° C)");
            binding.tvPlanNombre.setText(plan.getNombre());

            switch (plan.getEstado()) {
                case APROBADA:
                    binding.tvPlanEstadoBadge.setText(plan.getNotaFinal() != null ? "Aprobada (" + plan.getNotaFinal() + ")" : "Aprobada");
                    binding.tvPlanEstadoBadge.setTextColor(context.getColor(R.color.status_aprobada));
                    binding.tvPlanEstadoBadge.getBackground().setTint(context.getColor(R.color.primary_light));
                    binding.tvPlanCorrelativas.setVisibility(View.GONE);
                    break;
                case CURSANDO:
                    binding.tvPlanEstadoBadge.setText("Cursando");
                    binding.tvPlanEstadoBadge.setTextColor(context.getColor(R.color.status_cursando));
                    binding.tvPlanEstadoBadge.getBackground().setTint(context.getColor(R.color.primary_light));
                    binding.tvPlanCorrelativas.setVisibility(View.GONE);
                    break;
                case HABILITADA:
                    binding.tvPlanEstadoBadge.setText("Habilitada");
                    binding.tvPlanEstadoBadge.setTextColor(context.getColor(R.color.status_habilitada));
                    binding.tvPlanEstadoBadge.getBackground().setTint(context.getColor(R.color.status_habilitada_bg));
                    binding.tvPlanCorrelativas.setVisibility(View.GONE);
                    break;
                case BLOQUEADA:
                    binding.tvPlanEstadoBadge.setText("Bloqueada");
                    binding.tvPlanEstadoBadge.setTextColor(context.getColor(R.color.status_bloqueada));
                    binding.tvPlanEstadoBadge.getBackground().setTint(context.getColor(R.color.border_subtle));

                    if (!plan.getCorrelativasFaltantes().isEmpty()) {
                        binding.tvPlanCorrelativas.setVisibility(View.VISIBLE);
                        binding.tvPlanCorrelativas.setText("Falta: " + String.join(", ", plan.getCorrelativasFaltantes()));
                    } else {
                        binding.tvPlanCorrelativas.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }
}

