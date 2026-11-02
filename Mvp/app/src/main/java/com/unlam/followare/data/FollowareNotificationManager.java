package com.unlam.followare.data;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.unlam.followare.MainActivity;
import com.unlam.followare.R;
import com.unlam.followare.models.Novedad;

public class FollowareNotificationManager {

    public static final String CHANNEL_ID = "followare_system_channel";
    public static final String CHANNEL_NAME = "Novedades y Avisos UNLaM";
    public static final String CHANNEL_DESCRIPTION = "Alertas de cátedra, nuevos materiales y fechas de parciales";

    /**
     * Crea el canal de notificación del sistema con prioridad ALTA para que
     * aparezca como heads-up banner flotante del celular y quede en la barra de estado.
     */
    public static void initChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Envía una notificación nativa a la barra del sistema del celular.
     */
    public static void sendSystemNotification(Context context, Novedad novedad) {
        if (context == null || novedad == null) return;

        initChannel(context);

        // Intent que se disparará al tocar la notificación en el celular
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(MainActivity.EXTRA_NOVEDAD_ID, novedad.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode = Math.abs(novedad.getId().hashCode());
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        String titulo = novedad.getMateriaNombre() + ": " + novedad.getTitulo();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_novedades)
                .setContentTitle(titulo)
                .setContentText(novedad.getCuerpo())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(titulo)
                        .bigText(novedad.getCuerpo()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_novedades, "Ver Detalle", pendingIntent);

        // Verificación de permiso en Android 13+ (API 33)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Si el permiso no está otorgado, no se puede publicar al sistema operativo
                return;
            }
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(requestCode, builder.build());
    }
}

