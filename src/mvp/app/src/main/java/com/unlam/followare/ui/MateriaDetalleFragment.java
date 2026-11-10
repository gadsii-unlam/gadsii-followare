package com.unlam.followare.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.unlam.followare.R;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.FragmentMateriaDetalleBinding;
import com.unlam.followare.models.Archivo;
import com.unlam.followare.models.Materia;
import com.unlam.followare.models.Unidad;

/** Contenido de una materia: sus unidades y los archivos de cada una. */
public class MateriaDetalleFragment extends Fragment implements FollowareRepository.OnDataChangeListener {

    private static final String ARG_MATERIA_ID = "materia_id";

    private FragmentMateriaDetalleBinding binding;
    private FollowareRepository repository;
    private String materiaId;

    public static MateriaDetalleFragment newInstance(String materiaId) {
        Bundle args = new Bundle();
        args.putString(ARG_MATERIA_ID, materiaId);
        MateriaDetalleFragment fragment = new MateriaDetalleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMateriaDetalleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        materiaId = requireArguments().getString(ARG_MATERIA_ID);
        repository = FollowareRepository.getInstance(requireContext());
        repository.addListener(this);
        render();
    }

    private void render() {
        if (binding == null) return;
        Materia materia = repository.getMateriaById(materiaId);
        if (materia == null) return;

        binding.tvDetalleNombre.setText(materia.getNombre());
        binding.tvDetalleDocente.setText(materia.getComision() + " • " + materia.getDocente());
        binding.tvDetalleHorario.setText(materia.getHorario());

        binding.containerUnidades.removeAllViews();
        for (Unidad unidad : materia.getUnidades()) {
            binding.containerUnidades.addView(crearTituloUnidad(unidad));
            for (Archivo archivo : unidad.getArchivos()) {
                binding.containerUnidades.addView(crearFilaArchivo(archivo));
            }
        }
    }

    private TextView crearTituloUnidad(Unidad unidad) {
        Context context = requireContext();
        TextView titulo = new TextView(context);
        titulo.setText("Unidad " + unidad.getNumero() + ": " + unidad.getTitulo());
        titulo.setTextColor(context.getColor(R.color.text_primary));
        titulo.setTextSize(15f);
        titulo.setTypeface(null, Typeface.BOLD);
        titulo.setPadding(0, dp(8), 0, dp(4));
        return titulo;
    }

    private LinearLayout crearFilaArchivo(Archivo archivo) {
        Context context = requireContext();
        LinearLayout fila = new LinearLayout(context);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setGravity(Gravity.CENTER_VERTICAL);

        TextView nombre = new TextView(context);
        nombre.setText("• " + archivo.getNombre() + " (" + archivo.getTamanoTexto() + ")");
        nombre.setTextColor(context.getColor(R.color.text_secondary));
        nombre.setTextSize(14f);
        nombre.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        fila.addView(nombre);

        MaterialButton boton = new MaterialButton(context, null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
        boton.setText(archivo.isDescargado() ? R.string.guardado : R.string.descargar);
        boton.setTextSize(11f);
        boton.setCornerRadius(dp(6));
        if (archivo.isDescargado()) {
            boton.setTextColor(context.getColor(R.color.status_aprobada));
        }
        boton.setOnClickListener(v -> {
            boton.setText(R.string.descargando);
            boton.setEnabled(false);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                repository.markArchivoAsDownloaded(archivo.getId());
                boton.setText(R.string.guardado);
                boton.setTextColor(context.getColor(R.color.status_aprobada));
                boton.setEnabled(true);
            }, 300);
        });
        fila.addView(boton);
        return fila;
    }

    private int dp(int valor) {
        return Math.round(valor * getResources().getDisplayMetrics().density);
    }

    @Override
    public void onDataChanged() {
        if (isAdded()) {
            render();
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
