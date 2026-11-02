package com.unlam.followare.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unlam.followare.R;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.ItemNovedadBinding;
import com.unlam.followare.models.Archivo;
import com.unlam.followare.models.Novedad;

import java.util.ArrayList;
import java.util.List;

public class NovedadesAdapter extends RecyclerView.Adapter<NovedadesAdapter.ViewHolder> {

    public interface OnNovedadClickListener {
        void onNovedadClick(Novedad novedad);
    }

    private final Context context;
    private final FollowareRepository repository;
    private final OnNovedadClickListener listener;
    private List<Novedad> items = new ArrayList<>();

    public NovedadesAdapter(Context context, OnNovedadClickListener listener) {
        this.context = context;
        this.repository = FollowareRepository.getInstance(context);
        this.listener = listener;
    }

    public void setItems(List<Novedad> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNovedadBinding binding = ItemNovedadBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Novedad item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNovedadBinding binding;

        ViewHolder(ItemNovedadBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Novedad item) {
            String sub = item.getMateriaNombre() + (item.getComision().isEmpty() ? "" : " • " + item.getComision());
            binding.tvMateriaNombre.setText(sub);
            binding.tvFecha.setText(item.getFecha());
            binding.tvTitulo.setText(item.getTitulo());
            binding.tvCuerpo.setText(item.getCuerpo());

            // Estado leído / no leído
            if (item.isLeida()) {
                binding.viewUnreadDot.setVisibility(View.GONE);
                binding.cardNovedad.setCardBackgroundColor(context.getColor(R.color.read_bg));
                binding.btnToggleLeida.setText(R.string.marcar_no_leida);
            } else {
                binding.viewUnreadDot.setVisibility(View.VISIBLE);
                binding.cardNovedad.setCardBackgroundColor(context.getColor(R.color.unread_bg));
                binding.btnToggleLeida.setText(R.string.marcar_leida);
            }

            // Descarga inline de archivos adjuntos
            Archivo arch = item.getArchivoAdjunto();
            if (arch != null) {
                binding.btnDescargarInline.setVisibility(View.VISIBLE);
                if (arch.isDescargado()) {
                    binding.btnDescargarInline.setText(R.string.guardado);
                    binding.btnDescargarInline.setBackgroundColor(context.getColor(R.color.status_aprobada));
                } else {
                    binding.btnDescargarInline.setText(R.string.descargar);
                    binding.btnDescargarInline.setBackgroundColor(context.getColor(R.color.primary));
                }

                binding.btnDescargarInline.setOnClickListener(v -> {
                    binding.btnDescargarInline.setText(R.string.descargando);
                    binding.btnDescargarInline.setEnabled(false);

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        repository.markArchivoAsDownloaded(arch.getId());
                        binding.btnDescargarInline.setText(R.string.guardado);
                        binding.btnDescargarInline.setBackgroundColor(context.getColor(R.color.status_aprobada));
                        binding.btnDescargarInline.setEnabled(true);
                    }, 300);
                });
            } else {
                binding.btnDescargarInline.setVisibility(View.GONE);
            }

            // Alternar leído / no leído
            binding.btnToggleLeida.setOnClickListener(v -> {
                repository.markNovedadAsRead(item.getId(), !item.isLeida());
            });

            // Clic en la tarjeta abre el detalle (Alternativa B)
            binding.cardNovedad.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onNovedadClick(item);
                }
            });
        }
    }
}

