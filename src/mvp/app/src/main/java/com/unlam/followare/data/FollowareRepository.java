package com.unlam.followare.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.unlam.followare.models.Archivo;
import com.unlam.followare.models.Materia;
import com.unlam.followare.models.MateriaPlan;
import com.unlam.followare.models.Novedad;
import com.unlam.followare.models.TipoNovedad;
import com.unlam.followare.models.Unidad;
import com.unlam.followare.models.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FollowareRepository {

    private static final String PREF_NAME = "followare_prefs";
    private static final String KEY_READ_NEWS = "read_news_ids";
    private static final String KEY_DOWNLOADED_FILES = "downloaded_files_ids";

    private static FollowareRepository instance;
    private final Context context;

    private List<Materia> materias;
    private List<Novedad> novedades;
    private List<MateriaPlan> planEstudios;
    private final List<OnDataChangeListener> listeners = new ArrayList<>();
    private final List<OnPushNotificationListener> pushListeners = new ArrayList<>();

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    public interface OnPushNotificationListener {
        void onPushNotification(Novedad novedad);
    }

    public static synchronized FollowareRepository getInstance(Context context) {
        if (instance == null) {
            instance = new FollowareRepository(context != null ? context.getApplicationContext() : null);
        }
        return instance;
    }

    private FollowareRepository(Context context) {
        this.context = context;
        loadData();
    }

    public void loadData() {
        this.materias = SeedData.getInitialMaterias();
        this.novedades = SeedData.getInitialNovedades();
        this.planEstudios = SeedData.getInitialPlanEstudios();

        // Aplicar estado persistido en SharedPreferences si existe
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Set<String> readIds = prefs.getStringSet(KEY_READ_NEWS, new HashSet<>());
            Set<String> downloadedIds = prefs.getStringSet(KEY_DOWNLOADED_FILES, new HashSet<>());

            for (Novedad nov : novedades) {
                if (readIds.contains(nov.getId())) {
                    nov.setLeida(true);
                }
                if (nov.getArchivoAdjunto() != null && downloadedIds.contains(nov.getArchivoAdjunto().getId())) {
                    nov.getArchivoAdjunto().setDescargado(true);
                }
            }

            for (Materia mat : materias) {
                for (Unidad u : mat.getUnidades()) {
                    for (Archivo a : u.getArchivos()) {
                        if (downloadedIds.contains(a.getId())) {
                            a.setDescargado(true);
                        }
                    }
                }
            }
        }
    }

    public List<Novedad> getAllNovedades() {
        List<Novedad> list = new ArrayList<>(novedades);
        Collections.sort(list, (a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
        return list;
    }

    public List<Novedad> getNovedadesByFilter(TipoNovedad filter) {
        if (filter == null) return getAllNovedades();
        List<Novedad> result = new ArrayList<>();
        for (Novedad nov : getAllNovedades()) {
            if (nov.getTipo() == filter) {
                result.add(nov);
            }
        }
        return result;
    }

    public Novedad getNovedadById(String id) {
        for (Novedad nov : novedades) {
            if (nov.getId().equals(id)) return nov;
        }
        return null;
    }

    public void markNovedadAsRead(String id, boolean read) {
        Novedad nov = getNovedadById(id);
        if (nov != null) {
            nov.setLeida(read);
            saveReadState();
            notifyDataChanged();
        }
    }

    public void markArchivoAsDownloaded(String archivoId) {
        for (Novedad nov : novedades) {
            if (nov.getArchivoAdjunto() != null && nov.getArchivoAdjunto().getId().equals(archivoId)) {
                nov.getArchivoAdjunto().setDescargado(true);
                nov.setLeida(true); // Al descargar también se marca como leída
            }
        }
        for (Materia mat : materias) {
            for (Unidad u : mat.getUnidades()) {
                for (Archivo a : u.getArchivos()) {
                    if (a.getId().equals(archivoId)) {
                        a.setDescargado(true);
                    }
                }
            }
        }
        saveDownloadedState();
        saveReadState();
        notifyDataChanged();
    }

    public Usuario getUsuario() {
        return SeedData.getUsuario();
    }

    public int getUnreadCount() {
        int count = 0;
        for (Novedad nov : novedades) {
            if (!nov.isLeida()) count++;
        }
        return count;
    }

    public int getUnreadCountByMateria(String materiaId) {
        int count = 0;
        for (Novedad nov : novedades) {
            if (nov.getMateriaId().equals(materiaId) && !nov.isLeida()) {
                count++;
            }
        }
        return count;
    }

    public List<Materia> getMateriasEnCurso() {
        return new ArrayList<>(materias);
    }

    public Materia getMateriaById(String id) {
        for (Materia m : materias) {
            if (m.getId().equals(id)) return m;
        }
        return null;
    }

    public List<MateriaPlan> getPlanEstudios(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(planEstudios);
        }
        String q = query.toLowerCase().trim();
        List<MateriaPlan> filtered = new ArrayList<>();
        for (MateriaPlan mp : planEstudios) {
            if (mp.getNombre().toLowerCase().contains(q) || mp.getCodigo().toLowerCase().contains(q)) {
                filtered.add(mp);
            }
        }
        return filtered;
    }

    public MateriaPlan getMateriaPlanByCodigo(String codigo) {
        if (codigo == null) return null;
        for (MateriaPlan mp : planEstudios) {
            if (mp.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return mp;
            }
        }
        return null;
    }

    /**
     * Devuelve las materias de las cuales depende esta materia para desbloquearse (correlativas previas requeridas).
     */
    public List<MateriaPlan> getMateriasPrevias(MateriaPlan target) {
        List<MateriaPlan> previas = new ArrayList<>();
        if (target == null || target.getCorrelativasRequeridas() == null) return previas;
        for (String cod : target.getCorrelativasRequeridas()) {
            MateriaPlan p = getMateriaPlanByCodigo(cod);
            if (p != null) {
                previas.add(p);
            }
        }
        return previas;
    }

    /**
     * Devuelve las materias que dependen de esta materia para desbloquearse (correlativas posteriores que destraba).
     */
    public List<MateriaPlan> getMateriasPosteriores(MateriaPlan target) {
        List<MateriaPlan> posteriores = new ArrayList<>();
        if (target == null) return posteriores;
        String codTarget = target.getCodigo();
        for (MateriaPlan p : planEstudios) {
            if (p.getCorrelativasRequeridas() != null && p.getCorrelativasRequeridas().contains(codTarget)) {
                posteriores.add(p);
            }
        }
        return posteriores;
    }

    public void resetSession() {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            prefs.edit().clear().apply();
        }
        loadData();
        notifyDataChanged();
    }

    public void addListener(OnDataChangeListener listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public void removeListener(OnDataChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyDataChanged() {
        for (OnDataChangeListener l : listeners) {
            l.onDataChanged();
        }
    }

    public void addPushListener(OnPushNotificationListener listener) {
        if (!pushListeners.contains(listener)) pushListeners.add(listener);
    }

    public void removePushListener(OnPushNotificationListener listener) {
        pushListeners.remove(listener);
    }

    public void dispatchSimulatedPush(String novedadId) {
        Novedad nov = getNovedadById(novedadId);
        if (nov != null) {
            nov.setTimestamp(System.currentTimeMillis());
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
            nov.setFecha("hoy " + sdf.format(new java.util.Date()));
            nov.setLeida(false);
            saveReadState();
            notifyDataChanged();

            for (OnPushNotificationListener l : pushListeners) {
                l.onPushNotification(nov);
            }
        }
    }

    private void saveReadState() {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> readIds = new HashSet<>();
        for (Novedad nov : novedades) {
            if (nov.isLeida()) readIds.add(nov.getId());
        }
        prefs.edit().putStringSet(KEY_READ_NEWS, readIds).apply();
    }

    private void saveDownloadedState() {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> downloadedIds = new HashSet<>();
        for (Materia mat : materias) {
            for (Unidad u : mat.getUnidades()) {
                for (Archivo a : u.getArchivos()) {
                    if (a.isDescargado()) downloadedIds.add(a.getId());
                }
            }
        }
        prefs.edit().putStringSet(KEY_DOWNLOADED_FILES, downloadedIds).apply();
    }
}
