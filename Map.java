package xogame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map extends JPanel {
    public static final int MODE_H_V_A = 0;
    public static final int MODE_H_V_H = 1;

    int mode;
    int fieldSizeX;
    int fieldSizeY;
    int winLength;

    int cellWidth;
    int cellHeight;

    boolean isInitialised = false;

    public Map() {
        setBackground(Color.ORANGE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }

    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength) {
        System.out.println(mode + " " + fieldSizeX + " " + fieldSizeY + " " + winLength);
        this.mode = mode;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLength = winLength;

        isInitialised = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInitialised) {
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellHeight = panelHeight / fieldSizeY;
        cellWidth = panelWidth / fieldSizeX;

        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        for (int i = 0; i < Logic.SIZE; i++) {
            for (int j = 0; j < Logic.SIZE; j++) {
                if (Logic.map[j][i] == Logic.DOT_X) {
                    g.setColor(Color.BLUE);
                    g.drawLine(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight);
                    g.drawLine((i + 1) * cellWidth, j * cellHeight,
                            i * cellWidth, (j + 1) * cellHeight);
                }
                if (Logic.map[j][i] == Logic.DOT_O) {
                    g.setColor(Color.GREEN);
                    g.drawOval(i * cellWidth, j * cellHeight,
                            cellWidth, cellHeight);
                }

            }
        }
    }

    void update(MouseEvent e) {
//        System.out.println(e.getX() + " " + e.getY());
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        System.out.println(cellX + " " + cellY);

        Logic.humanTurn(cellX, cellY);
        repaint();
    }
}
