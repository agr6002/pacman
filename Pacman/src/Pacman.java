import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pacman extends JFrame {

  public Pacman() {

	initUI();
  }

  private void initUI() { // app

	add(new Board());

	setTitle("Pacman");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(380, 420);
	setLocationRelativeTo(null);
  }

  public static void main(String[] args) { // stay here

	EventQueue.invokeLater(() -> {

	  var ex = new Pacman();
	  ex.setVisible(true);
	});
  }
}

class Board extends JPanel implements ActionListener {

  private Dimension d;
  private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

  private Image ii;
  private final Color dotColor = new Color(192, 192, 0);
  private Color mazeColor;

  private boolean inGame = false;
  private boolean dying = false;

  private final int BLOCK_SIZE = 24;
  private final int N_BLOCKS = 15;
  private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
  private final int PAC_ANIM_DELAY = 2;
  private final int PACMAN_ANIM_COUNT = 4;
  private final int MAX_GHOSTS = 12;
  private final int PACMAN_SPEED = 6;

  private int pacAnimCount = PAC_ANIM_DELAY;
  private int pacAnimDir = 1;
  private int pacmanAnimPos = 0;
  private int N_GHOSTS = 6;
  private int pacsLeft, score;
  private int[] dx, dy;
  private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

  private Image ghost;
  private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
  private Image pacman3up, pacman3down, pacman3left, pacman3right;
  private Image pacman4up, pacman4down, pacman4left, pacman4right;

  private int pacman_x, pacman_y, pacmand_x, pacmand_y;
  private int req_dx, req_dy, view_dx, view_dy;

  private final short levelData[] = { 19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22, 21, 0, 0, 0, 17, 16,
	  16, 16, 16, 16, 16, 16, 16, 16, 20, 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 21, 0, 0, 0, 17, 16,
	  16, 24, 16, 16, 16, 16, 16, 16, 20, 17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20, 17, 16, 16, 16, 16,
	  16, 20, 0, 17, 16, 16, 16, 16, 24, 20, 25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21, 1, 17, 16, 20, 0,
	  0, 0, 0, 0, 0, 0, 17, 20, 0, 21, 1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16,
	  20, 0, 17, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16,
	  16, 18, 16, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21, 1, 25, 24, 24, 24,
	  24, 24, 24, 24, 24, 16, 16, 16, 18, 20, 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28 };

  private final int validSpeeds[] = { 1, 2, 3, 4, 6, 8 };
  private final int maxSpeed = 6;

  private int currentSpeed = 3;
  private short[] screenData;
  private Timer timer;

  public Board() {

	loadImages();
	initVariables();
	initBoard();
  }

  private void initBoard() { // app

	addKeyListener(new TAdapter());

	setFocusable(true);

	setBackground(Color.black);
  }

  private void initVariables() { // kind of all of them

	screenData = new short[N_BLOCKS * N_BLOCKS];
	mazeColor = new Color(5, 100, 5);
	d = new Dimension(400, 400);
	ghost_x = new int[MAX_GHOSTS];
	ghost_dx = new int[MAX_GHOSTS];
	ghost_y = new int[MAX_GHOSTS];
	ghost_dy = new int[MAX_GHOSTS];
	ghostSpeed = new int[MAX_GHOSTS];
	dx = new int[4];
	dy = new int[4];

	timer = new Timer(40, this);
	timer.start();
  }

  @Override
  public void addNotify() { // model??
	super.addNotify();

	initGame();
  }

  private void playGame(Graphics2D g2d) { // app

	if (dying) {

	  death();

	} else {

	  movePacman();
	  drawPacman(g2d);
	  moveGhosts(g2d);
	  checkMaze();
	}
  }






  @Override
  public void paintComponent(Graphics g) { // view
	super.paintComponent(g);

	doDrawing(g);
  }

  private void doDrawing(Graphics g) { // view

	Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(Color.black);
	g2d.fillRect(0, 0, d.width, d.height);

	drawMaze(g2d);
	drawScore(g2d);
	doAnim();

	if (inGame) {
	  playGame(g2d);
	} else {
	  showIntroScreen(g2d);
	}

	g2d.drawImage(ii, 5, 5, this);
	Toolkit.getDefaultToolkit().sync();
	g2d.dispose();
  }

  class TAdapter extends KeyAdapter { // controller

	@Override
	public void keyPressed(KeyEvent e) {

	  int key = e.getKeyCode();

	  if (inGame) {
		if (key == KeyEvent.VK_LEFT) {
		  req_dx = -1;
		  req_dy = 0;
		} else if (key == KeyEvent.VK_RIGHT) {
		  req_dx = 1;
		  req_dy = 0;
		} else if (key == KeyEvent.VK_UP) {
		  req_dx = 0;
		  req_dy = -1;
		} else if (key == KeyEvent.VK_DOWN) {
		  req_dx = 0;
		  req_dy = 1;
		} else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
		  inGame = false;
		} else if (key == KeyEvent.VK_PAUSE) {
		  if (timer.isRunning()) {
			timer.stop();
		  } else {
			timer.start();
		  }
		}
	  } else {
		if (key == 's' || key == 'S') {
		  inGame = true;
		  initGame();
		}
	  }
	}

	@Override
	public void keyReleased(KeyEvent e) {

	  int key = e.getKeyCode();

	  if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP || key == Event.DOWN) {
		req_dx = 0;
		req_dy = 0;
	  }
	}
  }

  @Override
  public void actionPerformed(ActionEvent e) { // app???

	repaint();
  }
}