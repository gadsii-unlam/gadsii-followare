package com.unlam.followare.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unlam.followare.R;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.FragmentMateriasBinding;
import com.unlam.followare.models.Materia;

public class MateriasFragment extends Fragment implements FollowareRepository.OnDataChangeListener {

    private FragmentMateriasBinding binding;
    private FollowareRepository repository;
    private MateriasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMateriasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = FollowareRepository.getInstance(requireContext());
        repository.addListener(this);

        adapter = new MateriasAdapter(requireContext(), this::abrirDetalle);
        binding.rvMaterias.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMaterias.setAdapter(adapter);

        updateList();
    }

    private void abrirDetalle(Materia materia) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, MateriaDetalleFragment.newInstance(materia.getId()))
                .addToBackStack(null)
                .commit();
    }

    public void updateList() {
        if (binding == null) return;
        adapter.setItems(repository.getMateriasEnCurso());
    }

    @Override
    public void onDataChanged() {
        if (isAdded()) {
            updateList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (repository != null) {
            repository.removeListener(this);
        }
        binding = null;
    }
}

