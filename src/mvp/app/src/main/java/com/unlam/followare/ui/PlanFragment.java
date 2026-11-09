package com.unlam.followare.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.FragmentPlanBinding;
import com.unlam.followare.models.MateriaPlan;

public class PlanFragment extends Fragment {

    private FragmentPlanBinding binding;
    private FollowareRepository repository;
    private PlanAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = FollowareRepository.getInstance(requireContext());
        adapter = new PlanAdapter(requireContext(), this::onMateriaClick);

        binding.rvPlan.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlan.setAdapter(adapter);

        updateList("");

        binding.etSearchPlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateList(String query) {
        if (binding == null) return;
        adapter.setItems(repository.getPlanEstudios(query));
    }

    private void onMateriaClick(MateriaPlan materia) {
        PlanDetailBottomSheetDialog dialog = PlanDetailBottomSheetDialog.newInstance(materia.getCodigo());
        dialog.show(getParentFragmentManager(), "plan_detail_dialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

