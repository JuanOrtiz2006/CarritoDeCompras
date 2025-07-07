package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class PanelGrafico extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        // === Cielo (fondo azul claro detrás de todo) ===
        g2.setColor(new Color(173, 216, 230)); // azul claro (light blue)
        g2.fillRect(0, 0, w, h);

        // === Pradera o suelo ===
        g2.setColor(new Color(245, 222, 179)); // beige claro
        g2.fillRect(0, h - 200, w, 200);

        // === Árboles espaciados ===
        int[] posicionesX = {100, 300, 500, 700};
        for (int x : posicionesX) {
            dibujarArbolMinimalista(g2, x, h - 250);
        }

        // === Banco de parque centrado ===
        int bancoW = 130, bancoH = 10;
        int bancoX = (w - bancoW) / 2;
        int bancoY = h - 150;

        g2.setColor(new Color(101, 67, 33)); // marrón medio
        g2.fillRect(bancoX, bancoY, bancoW, bancoH);             // asiento
        g2.fillRect(bancoX, bancoY + bancoH, 10, 30);            // pata izquierda
        g2.fillRect(bancoX + bancoW - 10, bancoY + bancoH, 10, 30); // pata derecha
        g2.fillRect(bancoX, bancoY - 15, bancoW, bancoH);        // respaldo

        // === Pequeño lago ovalado debajo del banco ===
        g2.setColor(new Color(173, 216, 230)); // mismo azul cielo
        int lagoW = 160;
        int lagoH = 40;
        int lagoX = (w - lagoW) / 2;
        int lagoY = bancoY + 40;
        g2.fillOval(lagoX, lagoY, lagoW, lagoH);
    }

    private void dibujarArbolMinimalista(Graphics2D g2, int x, int baseY) {
        // Tronco
        g2.setColor(new Color(139, 69, 19)); // marrón oscuro
        g2.fillRect(x, baseY, 15, 80);

        // Copa del árbol - forma ovalada sencilla
        g2.setColor(new Color(222, 184, 135)); // marrón claro
        g2.fillOval(x - 25, baseY - 50, 60, 50);
    }
}
