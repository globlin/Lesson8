package xogame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class StartNewGameWindow extends JFrame {
    private final GameWindow gameWindow;

    private static final int WIN_HEIGHT = 230;
    private static final int WIN_WIDTH = 350;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final int MIN_WIN_LEN = 3;
    private static final int MAX_WIN_LEN = 10;
    private static final String STR_WIN_LEN = "Winning Length: ";
    private static final String STR_FIELD_SIZE = "Field Size: ";

    private JRadioButton jrbHumanVsAi = new JRadioButton("Human vs ai", true);
    private JRadioButton jrbHumanVsHuman = new JRadioButton("Human vs Human", true);
    private ButtonGroup gameMode = new ButtonGroup();

    private JSlider slFieldSize;
    private JSlider slWinLength;

    public StartNewGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setTitle("Game options");
        setVisible(false);
        setSize(WIN_WIDTH, WIN_HEIGHT);

        Rectangle gameWindowsBounds = gameWindow.getBounds();
        int posX = (int) gameWindowsBounds.getCenterX() - WIN_WIDTH / 2;
        int posY = (int) gameWindowsBounds.getCenterY() - WIN_HEIGHT / 2;
        setLocation(posX, posY);

        setLayout(new GridLayout(10, 1));
        addGameControlMode();
        addGameControlFieldWinLength();

        JButton btnStartGame = new JButton("Start a game");
        add(btnStartGame);
        btnStartGame.addActionListener(e -> {
            btnStartGameClick();
        });


    }

    private void btnStartGameClick() {
        int gameMode;
        if (jrbHumanVsAi.isSelected()) {
            gameMode = Map.MODE_H_V_A;
        } else {
            gameMode = Map.MODE_H_V_H;
        }
        int fieldSize = slFieldSize.getValue();
        int winLength = slWinLength.getValue();

        Logic.DOTS_TO_WIN = winLength;
        Logic.SIZE = fieldSize;
        Logic.initMap();
        Logic.gameFinished = false;

        gameWindow.startNewGame(gameMode, fieldSize, fieldSize, winLength);

        setVisible(false);
    }

    private void addGameControlMode() {
        add(new JLabel("Choose game mode"));
        gameMode.add(jrbHumanVsAi);
        gameMode.add(jrbHumanVsHuman);
        add(jrbHumanVsAi);
        add(jrbHumanVsHuman);
    }

    private void addGameControlFieldWinLength() {
        add(new JLabel("Choose field size"));
        JLabel lblFieldSize = new JLabel(STR_FIELD_SIZE + MIN_FIELD_SIZE);
        add(lblFieldSize);
        slFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        add(slFieldSize);
        slFieldSize.addChangeListener(e -> {
            int currentFielSize = slFieldSize.getValue();
            slWinLength.setMaximum(currentFielSize);
            lblFieldSize.setText(STR_FIELD_SIZE + currentFielSize);
        });

        add(new JLabel("Choose winning length"));
        JLabel lblWinLen = new JLabel(STR_WIN_LEN + MIN_WIN_LEN);
        add(lblWinLen);

        slWinLength = new JSlider(MIN_WIN_LEN, MAX_WIN_LEN, MIN_WIN_LEN);
        add(slWinLength);
        slWinLength.addChangeListener(e -> {
            int currentWinLen = slWinLength.getValue();
            lblWinLen.setText(STR_WIN_LEN + currentWinLen);
        });


    }
}
