package com.unlam.followare.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.unlam.followare.R;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.DialogDetailBinding;
import com.unlam.followare.models.Archivo;
import com.unlam.followare.models.Novedad;

public class DetailBottomSheetDialog extends BottomSheetDialogFragment {

    private static final String ARG_NOVEDAD_ID = "novedad_id";
    private DialogDetailBinding binding;
    private FollowareRepository repository;
    private String novedadId;

    public static DetailBottomSheetDialog newInstance(String novedadId) {
        DetailBottomSheetDialog fragment = new DetailBottomSheetDialog();
        Bundle args = new Bundle();
        args.putString(ARG_NOVEDAD_ID, novedadId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = FollowareRepository.getInstance(requireContext());
        if (getArguments() != null) {
            novedadId = getArguments().getString(ARG_NOVEDAD_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Novedad nov = repository.getNovedadById(novedadId);
        if (nov == null) {
            dismiss();
            return;
        }

        // Auto-marcar como leída al abrir el detalle
        repository.markNovedadAsRead(nov.getId(), true);

        binding.tvDetailSubtitle.setText(nov.getMateriaNombre() + (nov.getComision().isEmpty() ? "" : " • " + nov.getComision()));
        binding.tvDetailTitle.setText(nov.getTitulo());
        binding.tvDetailFecha.setText("Publicado: " + nov.getFecha());
        binding.tvDetailBody.setText(nov.getCuerpo());

        binding.btnCloseDetail.setOnClickListener(v -> dismiss());

        Archivo arch = nov.getArchivoAdjunto();
        if (arch != null) {
            binding.cardAttachment.setVisibility(View.VISIBLE);
            binding.tvFileName.setText(arch.getNombre());
            binding.tvFileSize.setText(arch.getTamanoTexto() + " • Formato " + arch.getExtension().toUpperCase());

            if (arch.isDescargado()) {
                binding.btnDownloadDetail.setText(R.string.guardado);
                binding.btnDownloadDetail.setBackgroundColor(requireContext().getColor(R.color.status_aprobada));
            } else {
                binding.btnDownloadDetail.setText(R.string.descargar);
                binding.btnDownloadDetail.setBackgroundColor(requireContext().getColor(R.color.primary));
            }

            binding.btnDownloadDetail.setOnClickListener(v -> {
                binding.btnDownloadDetail.setText(R.string.descargando);
                binding.btnDownloadDetail.setEnabled(false);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (isAdded()) {
                        repository.markArchivoAsDownloaded(arch.getId());
                        binding.btnDownloadDetail.setText(R.string.guardado);
                        binding.btnDownloadDetail.setBackgroundColor(requireContext().getColor(R.color.status_aprobada));
                        binding.btnDownloadDetail.setEnabled(true);
                    }
                }, 350);
            });
        } else {
            binding.cardAttachment.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

