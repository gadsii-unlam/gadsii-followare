package com.unlam.followare.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.unlam.followare.R;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.DialogPlanDetailBinding;
import com.unlam.followare.databinding.ItemCorrelativaRowBinding;
import com.unlam.followare.models.EstadoMateria;
import com.unlam.followare.models.MateriaPlan;

import java.util.List;

public class PlanDetailBottomSheetDialog extends BottomSheetDialogFragment {

    private static final String ARG_CODIGO = "arg_codigo";

    private DialogPlanDetailBinding binding;
    private FollowareRepository repository;
    private String codigo;

    public static PlanDetailBottomSheetDialog newInstance(String codigo) {
        PlanDetailBottomSheetDialog fragment = new PlanDetailBottomSheetDialog();
        Bundle args = new Bundle();
        args.putString(ARG_CODIGO, codigo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codigo = getArguments().getString(ARG_CODIGO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogPlanDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = FollowareRepository.getInstance(requireContext());

        binding.btnCloseDialog.setOnClickListener(v -> dismiss());
        binding.btnCerrarPlanDetail.setOnClickListener(v -> dismiss());

        renderMateria(codigo);
    }

    private void renderMateria(String cod) {
        if (binding == null || cod == null) return;

        MateriaPlan target = repository.getMateriaPlanByCodigo(cod);
        if (target == null) {
            dismiss();
            return;
        }

        this.codigo = cod;
        Context ctx = requireContext();

        // Header
        binding.tvDetailCodigo.setText(target.getCodigo() + " • " + target.getAnio() + "° Año (" + target.getCuatrimestre() + "° Cuatrimestre)");
        binding.tvDetailNombre.setText(target.getNombre());

        // Badge de estado de la materia seleccionada
        setupEstadoBadge(binding.tvDetailEstadoBadge, target.getEstado(), target.getNotaFinal());

        // SECCIÓN 1: Materias de las que depende (Correlativas previas requeridas)
        List<MateriaPlan> previas = repository.getMateriasPrevias(target);
        binding.layoutPreviasContainer.removeAllViews();

        if (previas.isEmpty()) {
            binding.tvNoPrevias.setVisibility(View.VISIBLE);
            binding.layoutPreviasContainer.setVisibility(View.GONE);
        } else {
            binding.tvNoPrevias.setVisibility(View.GONE);
            binding.layoutPreviasContainer.setVisibility(View.VISIBLE);

            LayoutInflater inflater = LayoutInflater.from(ctx);
            for (MateriaPlan previa : previas) {
                ItemCorrelativaRowBinding rowBinding = ItemCorrelativaRowBinding.inflate(inflater, binding.layoutPreviasContainer, false);
                bindRow(rowBinding, previa, true);
                binding.layoutPreviasContainer.addView(rowBinding.getRoot());
            }
        }

        // SECCIÓN 2: Materias que dependen de esta (Correlativas posteriores)
        List<MateriaPlan> posteriores = repository.getMateriasPosteriores(target);
        binding.layoutPosterioresContainer.removeAllViews();

        if (posteriores.isEmpty()) {
            binding.tvNoPosteriores.setVisibility(View.VISIBLE);
            binding.layoutPosterioresContainer.setVisibility(View.GONE);
        } else {
            binding.tvNoPosteriores.setVisibility(View.GONE);
            binding.layoutPosterioresContainer.setVisibility(View.VISIBLE);

            LayoutInflater inflater = LayoutInflater.from(ctx);
            for (MateriaPlan posterior : posteriores) {
                ItemCorrelativaRowBinding rowBinding = ItemCorrelativaRowBinding.inflate(inflater, binding.layoutPosterioresContainer, false);
                bindRow(rowBinding, posterior, false);
                binding.layoutPosterioresContainer.addView(rowBinding.getRoot());
            }
        }
    }

    private void bindRow(ItemCorrelativaRowBinding rowBinding, MateriaPlan materia, boolean isPrevia) {
        Context ctx = requireContext();
        rowBinding.tvCorrelativaCodigo.setText(materia.getCodigo() + " • " + materia.getAnio() + "° Año (" + materia.getCuatrimestre() + "° C)");
        rowBinding.tvCorrelativaNombre.setText(materia.getNombre());

        setupEstadoBadge(rowBinding.tvCorrelativaEstado, materia.getEstado(), materia.getNotaFinal());

        // Icono según estado
        switch (materia.getEstado()) {
            case APROBADA:
                rowBinding.ivCorrelativaIcon.setImageResource(R.drawable.ic_check_circle);
                rowBinding.ivCorrelativaIcon.setColorFilter(ctx.getColor(R.color.status_aprobada));
                break;
            case CURSANDO:
                rowBinding.ivCorrelativaIcon.setImageResource(R.drawable.ic_materias);
                rowBinding.ivCorrelativaIcon.setColorFilter(ctx.getColor(R.color.status_cursando));
                break;
            case HABILITADA:
                rowBinding.ivCorrelativaIcon.setImageResource(R.drawable.ic_arrow_forward);
                rowBinding.ivCorrelativaIcon.setColorFilter(ctx.getColor(R.color.status_habilitada));
                break;
            case BLOQUEADA:
            default:
                rowBinding.ivCorrelativaIcon.setImageResource(R.drawable.ic_lock);
                rowBinding.ivCorrelativaIcon.setColorFilter(ctx.getColor(R.color.status_bloqueada));
                break;
        }

        // Al tocar una materia de la lista se profundiza en el árbol de correlatividades
        rowBinding.cardCorrelativaRow.setOnClickListener(v -> renderMateria(materia.getCodigo()));
    }

    private void setupEstadoBadge(android.widget.TextView badge, EstadoMateria estado, Integer notaFinal) {
        Context ctx = requireContext();
        switch (estado) {
            case APROBADA:
                badge.setText(notaFinal != null ? "Aprobada (Nota: " + notaFinal + ")" : "Aprobada");
                badge.setTextColor(ctx.getColor(R.color.status_aprobada));
                badge.getBackground().setTint(ctx.getColor(R.color.status_aprobada_bg));
                break;
            case CURSANDO:
                badge.setText("Cursando actualmente");
                badge.setTextColor(ctx.getColor(R.color.status_cursando));
                badge.getBackground().setTint(ctx.getColor(R.color.status_cursando_bg));
                break;
            case HABILITADA:
                badge.setText("Habilitada para cursar");
                badge.setTextColor(ctx.getColor(R.color.status_habilitada));
                badge.getBackground().setTint(ctx.getColor(R.color.status_habilitada_bg));
                break;
            case BLOQUEADA:
            default:
                badge.setText("Bloqueada por correlativas");
                badge.setTextColor(ctx.getColor(R.color.status_bloqueada));
                badge.getBackground().setTint(ctx.getColor(R.color.status_bloqueada_bg));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

