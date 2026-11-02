package com.unlam.followare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.unlam.followare.data.FollowareRepository;
import com.unlam.followare.models.EstadoMateria;
import com.unlam.followare.models.MateriaPlan;
import com.unlam.followare.models.Novedad;
import com.unlam.followare.models.TipoNovedad;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class FollowareRepositoryTest {

    private FollowareRepository repository;

    @Before
    public void setUp() {
        // En tests unitarios pasamos null para Context (usa datos en memoria sin SharedPreferences)
        repository = FollowareRepository.getInstance(null);
        repository.loadData();
    }

    @Test
    public void testGetAllNovedades_ordenadasCronologicamente() {
        List<Novedad> list = repository.getAllNovedades();
        assertTrue("Debe contener al menos 4 novedades", list.size() >= 4);

        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue("Las novedades deben ordenarse descendentes por timestamp",
                    list.get(i).getTimestamp() >= list.get(i + 1).getTimestamp());
        }
    }

    @Test
    public void testGetNovedadesByFilter_materialesYFechas() {
        List<Novedad> materiales = repository.getNovedadesByFilter(TipoNovedad.MATERIAL);
        assertFalse("Debe haber novedades de tipo material", materiales.isEmpty());
        for (Novedad n : materiales) {
            assertEquals(TipoNovedad.MATERIAL, n.getTipo());
        }

        List<Novedad> fechas = repository.getNovedadesByFilter(TipoNovedad.FECHA);
        assertFalse("Debe haber novedades de tipo fecha", fechas.isEmpty());
        for (Novedad n : fechas) {
            assertEquals(TipoNovedad.FECHA, n.getTipo());
        }
    }

    @Test
    public void testMarkNovedadAsRead_actualizaContadores() {
        int initialUnread = repository.getUnreadCount();
        String novId = "nov-alg-material-2";

        repository.markNovedadAsRead(novId, true);
        assertTrue(repository.getNovedadById(novId).isLeida());
        assertEquals(initialUnread - 1, repository.getUnreadCount());

        repository.markNovedadAsRead(novId, false);
        assertFalse(repository.getNovedadById(novId).isLeida());
        assertEquals(initialUnread, repository.getUnreadCount());
    }

    @Test
    public void testMarkArchivoAsDownloaded_marcaArchivoYLeeNovedad() {
        String archId = "arch-alg-3";
        repository.markArchivoAsDownloaded(archId);

        Novedad nov = repository.getNovedadById("nov-alg-material-2");
        assertNotNull(nov.getArchivoAdjunto());
        assertTrue(nov.getArchivoAdjunto().isDescargado());
        assertTrue(nov.isLeida());
    }

    @Test
    public void testGetPlanEstudios_buscadorYEstados() {
        List<MateriaPlan> all = repository.getPlanEstudios(null);
        assertEquals(13, all.size());

        List<MateriaPlan> filtered = repository.getPlanEstudios("Álgebra");
        assertEquals(1, filtered.size());
        assertEquals("Álgebra I", filtered.get(0).getNombre());
        assertEquals(EstadoMateria.CURSANDO, filtered.get(0).getEstado());

        List<MateriaPlan> filteredBloq = repository.getPlanEstudios("Análisis Matemático II");
        assertEquals(1, filteredBloq.size());
        assertEquals(EstadoMateria.BLOQUEADA, filteredBloq.get(0).getEstado());
        assertFalse(filteredBloq.get(0).getCorrelativasFaltantes().isEmpty());
    }

    @Test
    public void testCorrelativas_previasYPosteriores() {
        // INF101: materia inicial
        MateriaPlan inf101 = repository.getMateriaPlanByCodigo("INF101");
        assertNotNull(inf101);
        List<MateriaPlan> previasInf101 = repository.getMateriasPrevias(inf101);
        assertTrue("Materia inicial no debe tener previas", previasInf101.isEmpty());

        List<MateriaPlan> postInf101 = repository.getMateriasPosteriores(inf101);
        assertEquals("INF101 debe destrabar Arquitectura (INF201) y Algoritmos (INF202)", 2, postInf101.size());

        // INF202: Algoritmos y Programación
        MateriaPlan inf202 = repository.getMateriaPlanByCodigo("INF202");
        assertNotNull(inf202);
        List<MateriaPlan> previasInf202 = repository.getMateriasPrevias(inf202);
        assertEquals("INF202 depende de 2 materias previas (MAT101 e INF101)", 2, previasInf202.size());

        List<MateriaPlan> postInf202 = repository.getMateriasPosteriores(inf202);
        assertEquals("INF202 destraba Estructuras de Datos e INF301", 2, postInf202.size());

        // INF303: Sistemas Operativos (final de la cadena en este árbol)
        MateriaPlan inf303 = repository.getMateriaPlanByCodigo("INF303");
        assertNotNull(inf303);
        List<MateriaPlan> postInf303 = repository.getMateriasPosteriores(inf303);
        assertTrue("INF303 no traba otras materias en este recorte", postInf303.isEmpty());
    }

    @Test
    public void testDispatchSimulatedPush_notificaAListeners() {
        final boolean[] called = {false};
        FollowareRepository.OnPushNotificationListener listener = nov -> {
            assertEquals("nov-alg-material-2", nov.getId());
            assertFalse("Al simular push debe marcarse como no leída", nov.isLeida());
            called[0] = true;
        };

        repository.addPushListener(listener);
        repository.dispatchSimulatedPush("nov-alg-material-2");

        assertTrue("El listener debe haber sido invocado", called[0]);
        // Debe aparecer en la primera posición de la lista cronológica
        assertEquals("nov-alg-material-2", repository.getAllNovedades().get(0).getId());
        repository.removePushListener(listener);
    }
}

