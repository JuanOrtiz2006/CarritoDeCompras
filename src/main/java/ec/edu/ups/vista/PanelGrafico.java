package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class PanelGrafico extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Renderizado suave
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // === Cielo de atardecer ===
        GradientPaint cielo = new GradientPaint(0, 0, new Color(253, 216, 170),
                0, h, new Color(255, 245, 230));
        g2.setPaint(cielo);
        g2.fillRect(0, 0, w, h);

        // === Colinas suaves al fondo (detrás de árboles) ===
        g2.setColor(new Color(220, 190, 140));
        g2.fillOval(-300, h - 250, w / 2, 180);
        g2.fillOval(w / 4, h - 260, w / 2, 200);
        g2.fillOval(w - 400, h - 240, w / 2, 180);

        // === Lago al frente ===
        g2.setColor(new Color(204, 229, 255));
        int lagoW = w / 2;
        int lagoH = 100;
        int lagoX = (w - lagoW) / 2;
        int lagoY = h - 130;
        g2.fillOval(lagoX, lagoY, lagoW, lagoH);

        // === Árboles múltiples (tipo bosque) ===
        int troncoY = h - 210;
        int espacio = 50;

        for (int i = 0; i < w; i += espacio) {
            int troncoAltura = 70 + (int) (Math.random() * 20);
            int copaAncho = 50 + (int) (Math.random() * 20);
            int copaAlto = 50;

            g2.setColor(new Color(139, 69, 19)); // tronco
            g2.fillRect(i, troncoY + (80 - troncoAltura), 10, troncoAltura);

            g2.setColor(new Color(255, 204, 153)); // copa
            g2.fillOval(i - 20, troncoY - copaAlto / 2, copaAncho, copaAlto);
        }

        // === Banco de parque (delante del lago, centrado) ===
        int bancoW = 130, bancoH = 10;
        int bancoX = (w - bancoW) / 2;
        int bancoY = lagoY - 40;

        g2.setColor(new Color(150, 75, 0));
        g2.fillRect(bancoX, bancoY, bancoW, bancoH);             // asiento
        g2.fillRect(bancoX, bancoY + bancoH, 10, 30);            // pata izquierda
        g2.fillRect(bancoX + bancoW - 10, bancoY + bancoH, 10, 30); // pata derecha
        g2.fillRect(bancoX, bancoY - 15, bancoW, bancoH);        // respaldo
    }
}
