package com.unlam.followare.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.unlam.followare.R;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.ItemMateriaBinding;
import com.unlam.followare.models.Archivo;
import com.unlam.followare.models.Materia;
import com.unlam.followare.models.Unidad;

import java.util.ArrayList;
import java.util.List;

public class MateriasAdapter extends RecyclerView.Adapter<MateriasAdapter.ViewHolder> {

    private final Context context;
    private final FollowareRepository repository;
    private List<Materia> items = new ArrayList<>();

    public MateriasAdapter(Context context) {
        this.context = context;
        this.repository = FollowareRepository.getInstance(context);
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

            // Desplegar Unidades Didácticas
            binding.containerUnidades.removeAllViews();
            for (Unidad unidad : materia.getUnidades()) {
                TextView tvUnidadTitle = new TextView(context);
                tvUnidadTitle.setText("Unidad " + unidad.getNumero() + ": " + unidad.getTitulo());
                tvUnidadTitle.setTextColor(context.getColor(R.color.text_primary));
                tvUnidadTitle.setTextSize(13f);
                tvUnidadTitle.setTypeface(null, android.graphics.Typeface.BOLD);
                tvUnidadTitle.setPadding(0, 8, 0, 4);
                binding.containerUnidades.addView(tvUnidadTitle);

                for (Archivo arch : unidad.getArchivos()) {
                    LinearLayout row = new LinearLayout(context);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setPadding(0, 4, 0, 4);
                    row.setGravity(android.view.Gravity.CENTER_VERTICAL);

                    TextView tvFileName = new TextView(context);
                    tvFileName.setText("• " + arch.getNombre() + " (" + arch.getTamanoTexto() + ")");
                    tvFileName.setTextColor(context.getColor(R.color.text_secondary));
                    tvFileName.setTextSize(12f);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                    tvFileName.setLayoutParams(lp);
                    row.addView(tvFileName);

                    MaterialButton btnDescarga = new MaterialButton(context, null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
                    btnDescarga.setText(arch.isDescargado() ? R.string.guardado : R.string.descargar);
                    btnDescarga.setTextSize(10f);
                    btnDescarga.setCornerRadius(6);
                    if (arch.isDescargado()) {
                        btnDescarga.setTextColor(context.getColor(R.color.status_aprobada));
                    }

                    btnDescarga.setOnClickListener(v -> {
                        btnDescarga.setText(R.string.descargando);
                        btnDescarga.setEnabled(false);

                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            repository.markArchivoAsDownloaded(arch.getId());
                            btnDescarga.setText(R.string.guardado);
                            btnDescarga.setTextColor(context.getColor(R.color.status_aprobada));
                            btnDescarga.setEnabled(true);
                        }, 300);
                    });

                    row.addView(btnDescarga);
                    binding.containerUnidades.addView(row);
                }
            }
        }
    }
}

