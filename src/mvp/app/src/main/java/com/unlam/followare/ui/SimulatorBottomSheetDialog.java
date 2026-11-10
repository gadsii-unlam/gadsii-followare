package com.unlam.followare.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.DialogSimulatorBinding;

public class SimulatorBottomSheetDialog extends BottomSheetDialogFragment {

    private DialogSimulatorBinding binding;
    private FollowareRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSimulatorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = FollowareRepository.getInstance(requireContext());

        binding.btnSimularAlgebraMaterial.setOnClickListener(v -> {
            repository.dispatchSimulatedPush("nov-alg-material-2");
            Toast.makeText(requireContext(), "Notificación enviada a la barra de tu celular (Álgebra I: Nuevo material)", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        binding.btnSimularAlgebraMensaje.setOnClickListener(v -> {
            repository.dispatchSimulatedPush("nov-alg-mensaje-1");
            Toast.makeText(requireContext(), "Notificación enviada a la barra de tu celular (Cambio de aula)", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        binding.btnSimularQuimicaFecha.setOnClickListener(v -> {
            repository.dispatchSimulatedPush("nov-qui-fecha-1");
            Toast.makeText(requireContext(), "Notificación enviada a la barra de tu celular (Fecha de parcial)", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        binding.btnSimularIntraconsulta.setOnClickListener(v -> {
            repository.dispatchSimulatedPush("nov-intra-finales");
            Toast.makeText(requireContext(), "Notificación enviada a la barra de tu celular (Intraconsulta: Finales)", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        binding.btnResetSession.setOnClickListener(v -> {
            repository.resetSession();
            Toast.makeText(requireContext(), "Sesión de prueba restablecida con éxito.", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

