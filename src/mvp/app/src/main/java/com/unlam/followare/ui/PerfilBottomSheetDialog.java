package com.unlam.followare.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.DialogPerfilBinding;
import com.unlam.followare.models.Usuario;

public class PerfilBottomSheetDialog extends BottomSheetDialogFragment {

    private DialogPerfilBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Usuario usuario = FollowareRepository.getInstance(requireContext()).getUsuario();
        binding.tvPerfilNombre.setText(usuario.getNombre());
        binding.tvPerfilLegajo.setText(usuario.getLegajo());
        binding.tvPerfilEmail.setText(usuario.getEmail());
        binding.tvPerfilCarrera.setText(usuario.getCarrera());
        binding.tvPerfilSede.setText(usuario.getSede());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
