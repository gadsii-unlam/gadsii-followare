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
import com.unlam.followare.databinding.FragmentNovedadesBinding;
import com.unlam.followare.models.Novedad;
import com.unlam.followare.models.TipoNovedad;

import java.util.List;

public class NovedadesFragment extends Fragment implements FollowareRepository.OnDataChangeListener {

    private FragmentNovedadesBinding binding;
    private FollowareRepository repository;
    private NovedadesAdapter adapter;
    private TipoNovedad currentFilter = null; // null = Todas

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNovedadesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = FollowareRepository.getInstance(requireContext());
        repository.addListener(this);

        adapter = new NovedadesAdapter(requireContext(), this::openDetail);
        binding.rvNovedades.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNovedades.setAdapter(adapter);

        setupFilterChips();
        updateList();
    }

    private void setupFilterChips() {
        binding.chipGroupFiltros.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty() || checkedIds.contains(R.id.chipTodas)) {
                currentFilter = null;
            } else if (checkedIds.contains(R.id.chipMensajes)) {
                currentFilter = TipoNovedad.MENSAJE;
            } else if (checkedIds.contains(R.id.chipMateriales)) {
                currentFilter = TipoNovedad.MATERIAL;
            } else if (checkedIds.contains(R.id.chipFechas)) {
                currentFilter = TipoNovedad.FECHA;
            } else if (checkedIds.contains(R.id.chipIntraconsulta)) {
                currentFilter = TipoNovedad.INSTITUCIONAL;
            }
            updateList();
        });
    }

    public void updateList() {
        if (binding == null) return;
        List<Novedad> list = repository.getNovedadesByFilter(currentFilter);
        adapter.setItems(list);

        if (list.isEmpty()) {
            binding.tvEmptyNovedades.setVisibility(View.VISIBLE);
        } else {
            binding.tvEmptyNovedades.setVisibility(View.GONE);
        }
    }

    public void openDetail(Novedad novedad) {
        DetailBottomSheetDialog dialog = DetailBottomSheetDialog.newInstance(novedad.getId());
        dialog.show(getParentFragmentManager(), "detail_dialog");
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

