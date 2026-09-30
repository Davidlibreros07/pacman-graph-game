package org.icesi.implementacionjuegopacman.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.icesi.implementacionjuegopacman.structures.ListaAdyacencia;
import org.icesi.implementacionjuegopacman.structures.MatrizAdyacencia;
import org.icesi.implementacionjuegopacman.Algoritmos.BFS;
import org.icesi.implementacionjuegopacman.Algoritmos.DFS;
import org.icesi.implementacionjuegopacman.Algoritmos.Dijkstra;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.image.Image;



public class GameViewController {

    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private final int CELL_SIZE = 50;

    private GrafoInterfaz<String> gridGraph;

    private int pacmanFila = 5;
    private int pacmanCol = 5;

    private List<Ghost> ghosts = new ArrayList<>();

    private List<Thread> ghostThreads = new ArrayList<>();

    private int powerFila = 5;
    private int powerCol = 5;
    private boolean hasPower = false;
    private boolean gameOver = false;

    private final int objetivoRow = 0;
    private final int objetivoCol = 0;
    private Thread powerSpawnThread;
    private boolean running = false;
    private Image pacmanImage;
    private Image fantasmaVerde;
    private Image fantasmaRojo;
    private Image fantasmaNaranja;



    @FXML
    private Canvas gameCanvas;

    @FXML
    private Button startButton;

    @FXML
    private Button switchGraphButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private ImageView menuImage;

    @FXML
    private Button manualButton;


    private int score = 0;


    private boolean usingMatrixGraph = true;

    @FXML
    public void initialize() {
        menuImage.setImage(new Image(getClass().getResourceAsStream("/org/icesi/implementacionjuegopacman/menu_logo2.png")));
        menuImage.setVisible(true);
    }


    @FXML
    public void onStartGame() {


        menuImage.setVisible(false);

        score = 0;
        pacmanImage = new Image(getClass().getResourceAsStream("/org/icesi/implementacionjuegopacman/pacman.png"));

        scoreLabel.setText("Puntaje: 0");

        //OCULTAMOS LOS BOTONES
        startButton.setVisible(false);
        switchGraphButton.setVisible(false);
        exitButton.setVisible(true);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gc.fillText("¡Juego iniciado!", 100, 100);
        powerFila = (int) (Math.random() * FILAS);
        powerCol = (int) (Math.random() * COLUMNAS);

        dibujarCuadriculaGrafo(gc);
        crearGrafo();
        ghosts.clear();
        ghosts.add(new Ghost(0, FILAS - 1, COLUMNAS - 1)); // Fantasma 0 - BFS
        ghosts.add(new Ghost(1, 0, COLUMNAS - 1));         // Fantasma 1 - DFS
        ghosts.add(new Ghost(2, 9, 0));                    // Fantasma 2 - Dijkstra hacia bola negra
        drawPacMan(gc);
        fantasmaVerde = new Image(getClass().getResourceAsStream("/org/icesi/implementacionjuegopacman/fantasma_verde.png"));
        fantasmaRojo = new Image(getClass().getResourceAsStream("/org/icesi/implementacionjuegopacman/fantasma_rojo.png"));
        fantasmaNaranja = new Image(getClass().getResourceAsStream("/org/icesi/implementacionjuegopacman/fantasma_naranja.png"));

        drawGhosts(gc);
        startGhostThreads();
        startPowerSpawner();



    }

    @FXML
    public void onSwitchGraph() {
        usingMatrixGraph = !usingMatrixGraph;
        String modo = usingMatrixGraph ? "Matriz de Adyacencia" : "Lista de Adyacencia";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modo de Grafo Cambiado");
        alert.setHeaderText(null);
        alert.setContentText("Ahora estás usando: " + modo);
        alert.showAndWait();
    }

    private void dibujarCuadriculaGrafo(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());


        for (int row = 0; row < FILAS; row++) {
            for (int col = 0; col < COLUMNAS; col++) {
                double x = col * CELL_SIZE;
                double y = row * CELL_SIZE;

                gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
        drawPower(gc);
    }

    private void crearGrafo() {
        gridGraph = usingMatrixGraph
                ? new MatrizAdyacencia<>()
                : new ListaAdyacencia<>();

        for (int row = 0; row < FILAS; row++) {
            for (int col = 0; col < COLUMNAS; col++) {
                String current = cellId(row, col);
                gridGraph.addVertice(current);

                // Arista hacia arriba
                if (row > 0) {
                    String up = cellId(row - 1, col);

                    // Menor peso si estamos cerca de la fila del objetivo
                    int weight = (row == objetivoRow) ? 1 : 10;

                    gridGraph.addArista(current, up, weight);
                }

                // Arista hacia la izquierda
                if (col > 0) {
                    String left = cellId(row, col - 1);

                    // Menor peso si estamos en la misma fila del objetivo
                    int weight = (row == objetivoRow) ? 1 : 10;

                    gridGraph.addArista(current, left, weight);
                }
            }
        }
    }

    private String cellId(int row, int col) {
        return row + "," + col;
    }

    private void drawPacMan(GraphicsContext gc) {
        double x = pacmanCol * CELL_SIZE;
        double y = pacmanFila * CELL_SIZE;
        gc.drawImage(pacmanImage, x, y, CELL_SIZE, CELL_SIZE);
    }

    public void manejarTeclas(KeyCode code) {
        int newRow = pacmanFila;
        int newCol = pacmanCol;

        switch (code) {
            case W -> newRow--;
            case S -> newRow++;
            case A -> newCol--;
            case D -> newCol++;
            default -> { return; }
        }


        if (newRow < 0 || newRow >= FILAS || newCol < 0 || newCol >= COLUMNAS) return;

        String current = cellId(pacmanFila, pacmanCol);
        String target = cellId(newRow, newCol);


        if (gridGraph.areConnected(current, target)) {
            pacmanFila = newRow;
            pacmanCol = newCol;

            GraphicsContext gc = gameCanvas.getGraphicsContext2D();
            dibujarCuadriculaGrafo(gc);
            drawPacMan(gc);
            drawGhosts(gc);
            drawBlackTarget(gc);
            checkCollisions();
        }
        if (pacmanFila == powerFila && pacmanCol == powerCol && !hasPower) {
            activatePowerMode();
        }
    }

    private void drawGhosts(GraphicsContext gc) {
        for (Ghost g : ghosts) {
            if (!g.alive || g.inRespawn) continue;

            double x = g.col * CELL_SIZE;
            double y = g.row * CELL_SIZE;

            Image img;
            if (g.index == 0) {
                img = fantasmaVerde;
            } else if (g.index == 1) {
                img = fantasmaRojo;
            } else {
                img = fantasmaNaranja;
            }

            gc.drawImage(img, x, y, CELL_SIZE, CELL_SIZE);
        }

    }


    private void startGhostThreads() {
        ghostThreads.clear();

        for (int i = 0; i < ghosts.size(); i++) {
            final int index = i;

            Thread ghostThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1000);

                        Ghost ghost = ghosts.get(index);
                        if (!ghost.alive || ghost.inRespawn) continue;

                        String origin = cellId(ghost.row, ghost.col);
                        String target = cellId(pacmanFila, pacmanCol);

                        // Fantasma 0: BFS
                        if (index == 0) {
                            List<String> path = BFS.getPath(gridGraph, origin, target);
                            if (path.size() >= 2) {
                                int[] nextPos = parseCellId(path.get(1));
                                ghost.row = nextPos[0];
                                ghost.col = nextPos[1];
                            }

                            // Fantasma 1: DFS
                        } else if (index == 1) {
                            if (ghost.currentPath == null || ghost.pathIndex >= ghost.currentPath.size()) {
                                ghost.currentPath = DFS.dfs(gridGraph, origin);
                                ghost.pathIndex = 1;
                            }

                            if (ghost.currentPath != null && ghost.pathIndex < ghost.currentPath.size()) {
                                String next = ghost.currentPath.get(ghost.pathIndex++);
                                int[] newPos = parseCellId(next);
                                ghost.row = newPos[0];
                                ghost.col = newPos[1];
                            }

                            // Fantasma 2 DIJKSTRA HASTA LA BOLA NEGRA
                        } else {
                            String origen = cellId(ghost.row, ghost.col);
                            String destino = cellId(objetivoRow, objetivoCol);
                            List<String> path = Dijkstra.getPath(gridGraph, origen, destino);

                            if (path.size() >= 2) {
                                int[] next = parseCellId(path.get(1));
                                ghost.row = next[0];
                                ghost.col = next[1];
                            }

                            // Verificar si llegó al objetivo
                            if (ghost.row == objetivoRow && ghost.col == objetivoCol) {
                                gameOver = true;
                                Platform.runLater(() -> {
                                    showGameOver("¡El super fantasma alcanzó la bola blanca. ¡Perdiste!");
                                });
                            }
                        }

                        // Redibujar
                        Platform.runLater(() -> {
                            GraphicsContext gc = gameCanvas.getGraphicsContext2D();
                            dibujarCuadriculaGrafo(gc);
                            drawPower(gc);
                            drawPacMan(gc);
                            drawGhosts(gc);
                            drawBlackTarget(gc); //BOLA AGREGADA
                            checkCollisions();
                        });
                    }
                } catch (InterruptedException ignored) {}
            });

            ghostThread.setDaemon(true);
            ghostThread.start();
            ghostThreads.add(ghostThread);
        }
    }

    private int[] parseCellId(String id) {
        String[] parts = id.split(",");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private void checkCollisions() {
        if (gameOver) return;

        for (Ghost g : ghosts) {
            if (!g.alive || g.inRespawn) continue;

            if (g.row == pacmanFila && g.col == pacmanCol) {
                if (hasPower) {
                    score += 500;
                    scoreLabel.setText("Puntaje: " + score);
                    System.out.println("¡Fantasma comido!");
                    if (score >= 10000) {
                        gameOver = true;
                        Platform.runLater(() -> showVictory());
                        return;
                    }
                    g.alive = false;
                    g.inRespawn = true;

                    new Thread(() -> {
                        try {
                            Thread.sleep(5000);

                            Platform.runLater(() -> {
                                respawnGhost(g);
                                GraphicsContext gc = gameCanvas.getGraphicsContext2D();
                                dibujarCuadriculaGrafo(gc);
                                drawPacMan(gc);
                                drawGhosts(gc);
                                drawBlackTarget(gc);
                            });
                        } catch (InterruptedException ignored) {}
                    }).start();

                    return;
                } else {
                    gameOver = true;
                    showGameOver("¡Pac-Man fue atrapado por un fantasma!");
                    break;
                }
            }
        }


    }

    private void showGameOver(String mensaje) {
        gameOver = true;
        exitButton.setVisible(true);
        stopGhostThreads();
        score = 0;
        scoreLabel.setText("Puntaje: 0");


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();


            pacmanFila = 5;
            pacmanCol = 5;

            int FilaBola = (int) (Math.random() * 10);
            int ColumnaBola = (int) (Math.random() * 10);
            powerFila = FilaBola;
            powerCol = ColumnaBola;
            gameOver = false;//el juego se reinicia


        ghosts.clear();
        ghosts.add(new Ghost(0, FILAS - 1, COLUMNAS - 1)); // Fantasma 0 - BFS
        ghosts.add(new Ghost(1, 0, COLUMNAS - 1));         // Fantasma 1 - DFS
        ghosts.add(new Ghost(2, 9, 0));                    // Fantasma 2 - Dijkstra hacia bola negra


        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
            dibujarCuadriculaGrafo(gc);
            drawPacMan(gc);
            drawGhosts(gc);


            startGhostThreads();
        startPowerSpawner();



    }

    private void stopGhostThreads() {
        running = false;
        if (powerSpawnThread != null) {
            powerSpawnThread.interrupt();
        }

        for (Thread t : ghostThreads) {
            t.interrupt();
        }
        ghostThreads.clear();
    }

    private void drawPower(GraphicsContext gc) {
        Color color = hasPower ? Color.BLACK : Color.BLUE;
        gc.setFill(color);
        gc.fillOval(powerCol * CELL_SIZE + CELL_SIZE * 0.3,
                powerFila * CELL_SIZE + CELL_SIZE * 0.3,
                CELL_SIZE * 0.4,
                CELL_SIZE * 0.4);
    }

    private void activatePowerMode() {
        hasPower = true;
        System.out.println("¡Pac-Man está en modo poder!");

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                hasPower = false;

                // Generar nueva posición de la bolita INMEDIATAMENTE
                powerFila = (int) (Math.random() * FILAS);
                powerCol = (int) (Math.random() * COLUMNAS);

                Platform.runLater(() -> {
                    GraphicsContext gc = gameCanvas.getGraphicsContext2D();
                    drawPower(gc); // redibuja solo la nueva bolita
                });

                System.out.println("Modo poder desactivado");
            } catch (InterruptedException ignored) {}
        }).start();
    }

    private static class Ghost {
        int row;
        int col;
        boolean alive;
        boolean inRespawn;
        int index;

        List<String> currentPath = null;
        int pathIndex = 0;

        Ghost(int index, int row, int col) {
            this.index = index;
            this.row = row;
            this.col = col;
            this.alive = true;
            this.inRespawn = false;
        }
    }

    private boolean isGraphWeighted() {
        return gridGraph instanceof ListaAdyacencia;
    }

    private void drawBlackTarget(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.WHITE);
        double x = objetivoCol * CELL_SIZE + CELL_SIZE * 0.3;
        double y = objetivoRow * CELL_SIZE + CELL_SIZE * 0.3;
        gc.fillOval(x, y, CELL_SIZE * 0.4, CELL_SIZE * 0.4);
    }

    private void respawnGhost(Ghost ghost) {

        ghost.alive = true;
        ghost.inRespawn = false;
        ghost.currentPath = null;
        ghost.pathIndex = 0;

        if (ghost.index == 0) {
            ghost.row = FILAS - 1;
            ghost.col = COLUMNAS - 1;
        } else if (ghost.index == 1) {
            ghost.row = 0;
            ghost.col = COLUMNAS - 1;
        } else if (ghost.index == 2) {
            ghost.row = 9;
            ghost.col = 0;
        }
    }

    @FXML
    public void onExit() {
        menuImage.setVisible(true);

        running = false;
        stopGhostThreads();


        // Limpiar estado del juego
        pacmanFila = 5;
        pacmanCol = 5;
        ghosts.clear();
        hasPower = false;
        gameOver = false;

        scoreLabel.setText("Puntaje: 0");


        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());





        startButton.setVisible(true);
        switchGraphButton.setVisible(true);
        exitButton.setVisible(false);
    }

    private void startPowerSpawner() {
        running = true;

        powerSpawnThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(5000);
                    if(!hasPower) {

                        powerFila = (int) (Math.random() * FILAS);
                        powerCol = (int) (Math.random() * COLUMNAS);
                    }

                    Platform.runLater(() -> {
                        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
                        dibujarCuadriculaGrafo(gc);
                        drawPacMan(gc);
                        drawGhosts(gc);
                        drawBlackTarget(gc);
                    });
                } catch (InterruptedException ignored) {}
            }
        });

        powerSpawnThread.setDaemon(true);
        powerSpawnThread.start();
    }

    @FXML
    public void mostrarManual() {
        Alert manual = new Alert(Alert.AlertType.INFORMATION);
        manual.setTitle("Manual de Usuario");
        manual.setHeaderText("Cómo jugar Pac-Man con Grafos");
        manual.setContentText("""
        Para mover a pac man usa los siguientes controles:
        - W: Mover arriba
        - A: Mover izquierda
        - S: Mover abajo
        - D: Mover derecha

        Objetivo:
        - Come bolitas de poder (azules)
        - Si comes una, puedes eliminar fantasmas temporalmente (comertelos)
        - Evita a los fantasmas (rojo, púrpura, naranja)
        - Si el fantasma especial (fantasma naranja) llega a la bola blanca (esquina), pierdes
        - Si llegas a 10000 puntos has ganado el juego

        Cambiar modo:
        - Usa el botón "Cambiar Modo" para alternar entre grafo por matriz y lista

        ¡Presiona 'Iniciar Juego' para comenzar!
        """);
        manual.showAndWait();
    }


    private void showVictory() {
        stopGhostThreads();
        gameOver = false;
        score = 0;
        scoreLabel.setText("Puntaje: 0");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText(null);
        alert.setContentText("¡Felicitaciones! Has ganado el juego con 10,000 puntos.");
        alert.showAndWait();

        pacmanFila = 5;
        pacmanCol = 5;

        int FilaBola = (int) (Math.random() * 10);
        int ColumnaBola = (int) (Math.random() * 10);
        powerFila = FilaBola;
        powerCol = ColumnaBola;

        ghosts.clear();
        ghosts.add(new Ghost(0, FILAS - 1, COLUMNAS - 1)); // BFS
        ghosts.add(new Ghost(1, 0, COLUMNAS - 1));         // DFS
        ghosts.add(new Ghost(2, 9, 0));                    // Dijkstra

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        dibujarCuadriculaGrafo(gc);
        drawPacMan(gc);
        drawGhosts(gc);

        startGhostThreads();
        startPowerSpawner();
    }













}
