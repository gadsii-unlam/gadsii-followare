package com.unlam.followare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.unlam.followare.data.FollowareNotificationManager;
import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.databinding.ActivityMainBinding;
import com.unlam.followare.models.Novedad;
import com.unlam.followare.ui.DetailBottomSheetDialog;
import com.unlam.followare.ui.MateriasFragment;
import com.unlam.followare.ui.NovedadesFragment;
import com.unlam.followare.ui.PlanFragment;
import com.unlam.followare.ui.SimulatorBottomSheetDialog;

public class MainActivity extends AppCompatActivity implements FollowareRepository.OnDataChangeListener, FollowareRepository.OnPushNotificationListener {

    public static final String EXTRA_NOVEDAD_ID = "extra_novedad_id";

    private ActivityMainBinding binding;
    private FollowareRepository repository;

    private final Fragment novedadesFragment = new NovedadesFragment();
    private final Fragment materiasFragment = new MateriasFragment();
    private final Fragment planFragment = new PlanFragment();

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, "Permiso de notificaciones no otorgado. Habilítalo en Ajustes para ver las alertas en el celular.", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializar canal de notificaciones nativo de Android
        FollowareNotificationManager.initChannel(this);
        checkNotificationPermission();

        repository = FollowareRepository.getInstance(this);
        repository.addListener(this);
        repository.addPushListener(this);

        setupNavigation();
        setupSimulatorFab();

        // Cargar vista inicial: Novedades (Home en Alternativa B)
        loadFragment(novedadesFragment);
        updateUnreadBadges();

        // Manejo de Deep Link desde la barra de notificaciones del celular
        handleIntentDeepLink(getIntent());
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntentDeepLink(intent);
    }

    /**
     * Flujo Crítico de Deep Link:
     * Al tocar la notificación nativa en la barra del celular, se abre directamente
     * el panel de detalle de la novedad sobre la pantalla sin pasos intermedios.
     */
    private void handleIntentDeepLink(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_NOVEDAD_ID)) {
            String novedadId = intent.getStringExtra(EXTRA_NOVEDAD_ID);
            if (novedadId != null && !novedadId.isEmpty()) {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_novedades);
                DetailBottomSheetDialog dialog = DetailBottomSheetDialog.newInstance(novedadId);
                dialog.show(getSupportFragmentManager(), "detail_dialog");
                intent.removeExtra(EXTRA_NOVEDAD_ID);
            }
        }
    }

    private void setupNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_novedades) {
                loadFragment(novedadesFragment);
                return true;
            } else if (itemId == R.id.nav_materias) {
                loadFragment(materiasFragment);
                return true;
            } else if (itemId == R.id.nav_plan) {
                loadFragment(planFragment);
                return true;
            }
            return false;
        });
    }

    private void setupSimulatorFab() {
        binding.fabSimulator.setOnClickListener(v -> {
            SimulatorBottomSheetDialog dialog = new SimulatorBottomSheetDialog();
            dialog.show(getSupportFragmentManager(), "simulator_dialog");
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void updateUnreadBadges() {
        int unread = repository.getUnreadCount();
        BadgeDrawable badge = binding.bottomNavigation.getOrCreateBadge(R.id.nav_novedades);
        if (unread > 0) {
            badge.setVisible(true);
            badge.setNumber(unread);
            binding.topAppBar.setSubtitle(getString(R.string.tag_unlam) + " • " + unread + " novedades sin leer");
        } else {
            badge.setVisible(false);
            binding.topAppBar.setSubtitle(getString(R.string.tag_unlam) + " • Al día");
        }
    }

    @Override
    public void onDataChanged() {
        runOnUiThread(this::updateUnreadBadges);
    }

    /**
     * Al recibir una notificación (por ejemplo simulada por el usuario),
     * se dispara como Notificación del Sistema Operativo en el Celular
     * (aparece en la barra de notificaciones superior de Android).
     */
    @Override
    public void onPushNotification(Novedad novedad) {
        runOnUiThread(() -> FollowareNotificationManager.sendSystemNotification(this, novedad));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repository != null) {
            repository.removeListener(this);
            repository.removePushListener(this);
        }
    }
}

