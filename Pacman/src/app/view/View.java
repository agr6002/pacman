package app.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import app.App;
import app.Config;

public class View extends JPanel implements Config {

	private static final long serialVersionUID = 1L;
	private Dimension d;
	  private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

	  private Image ii;
	  private final Color dotColor = new Color(192, 192, 0);
	  private Color mazeColor;

	  private int pacAnimCount = PAC_ANIM_DELAY; 
	  private int pacAnimDir = 1; 
	  private int pacmanAnimPos = 0;
	  private Image ghost; 
	  private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down; 
	  private Image pacman3up, pacman3down, pacman3left, pacman3right; 
	  private Image pacman4up, pacman4down, pacman4left, pacman4right; 

	  public int view_dx, view_dy; // Model or View or Controller

	  private short[] screenData; 
	  
	  
	  private App app;
	  
	  public View(App app) {
		  this.app = app;
		  loadImages();
		  initVariables();
//		  initBoard();
	  }

//	  private void initBoard() {
//
//		addKeyListener(app.controller);
//
//		setFocusable(true);
//
//		setBackground(Color.black);
//	  }

	  private void doAnim() {

		pacAnimCount--;
	
		if (pacAnimCount <= 0) {
		  pacAnimCount = PAC_ANIM_DELAY;
		  pacmanAnimPos = pacmanAnimPos + pacAnimDir;
	
		  if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
			pacAnimDir = -pacAnimDir;
		  }
		}
	  }

	  private void initVariables() {

		screenData = new short[N_BLOCKS * N_BLOCKS];
		mazeColor = new Color(5, 100, 5);
		d = new Dimension(400, 400);
//		ghost_x = new int[MAX_GHOSTS];
//		ghost_dx = new int[MAX_GHOSTS];
//		ghost_y = new int[MAX_GHOSTS];
//		ghost_dy = new int[MAX_GHOSTS];
//		ghostSpeed = new int[MAX_GHOSTS];
//		dx = new int[4];
//		dy = new int[4];
//	
//		timer = new Timer(40, this);
//		timer.start();
  }

	  private void loadImages() {

		ghost = new ImageIcon("src/resources/ghost.png").getImage();
		pacman1 = new ImageIcon("src/resources/pacman.png").getImage();
		pacman2up = new ImageIcon("src/resources/up1.png").getImage();
		pacman3up = new ImageIcon("src/resources/up2.png").getImage();
		pacman4up = new ImageIcon("src/resources/up3.png").getImage();
		pacman2down = new ImageIcon("src/resources/down1.png").getImage();
		pacman3down = new ImageIcon("src/resources/down2.png").getImage();
		pacman4down = new ImageIcon("src/resources/down3.png").getImage();
		pacman2left = new ImageIcon("src/resources/left1.png").getImage();
		pacman3left = new ImageIcon("src/resources/left2.png").getImage();
		pacman4left = new ImageIcon("src/resources/left3.png").getImage();
		pacman2right = new ImageIcon("src/resources/right1.png").getImage();
		pacman3right = new ImageIcon("src/resources/right2.png").getImage();
		pacman4right = new ImageIcon("src/resources/right3.png").getImage();

  }
  		
	  @Override
  	  public void paintComponent(Graphics g) {
	  		super.paintComponent(g);

	  		doDrawing(g);
  	  }

	  private void showIntroScreen(Graphics2D g2d) {

		g2d.setColor(new Color(0, 32, 48));
		g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		g2d.setColor(Color.white);
		g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
	
		String s = "Press s to start.";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
	
		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
  }

	  private void drawScore(Graphics2D g) {

		int i;
		String s;
	
		g.setFont(smallFont);
		g.setColor(new Color(96, 128, 255));
		s = "Score: " + app.model.score;
		g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
	
		for (i = 0; i < app.model.pacsLeft; i++) {
		  g.drawImage(pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
		}
	  }

	  private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
	
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);
	
		drawMaze(g2d);
		drawScore(g2d);
		doAnim();
	
		if (app.inGame) {
			app.playGame(g2d);
		} else {
		  showIntroScreen(g2d);
		}
	
		g2d.drawImage(ii, 5, 5, this);
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	  }

	  private void drawGhost(Graphics2D g2d, int x, int y) {

		g2d.drawImage(ghost, x, y, this);
	}

	  public void drawPacman(Graphics2D g2d) {

		if (view_dx == -1) {
		  drawPacnanLeft(g2d);
		} else if (view_dx == 1) {
		  drawPacmanRight(g2d);
		} else if (view_dy == -1) {
		  drawPacmanUp(g2d);
		} else {
		  drawPacmanDown(g2d);
		}
	}

	  private void drawPacmanUp(Graphics2D g2d) {

		switch (pacmanAnimPos) {
		case 1:
		  g2d.drawImage(pacman2up, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 2:
		  g2d.drawImage(pacman3up, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 3:
		  g2d.drawImage(pacman4up, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		default:
		  g2d.drawImage(pacman1, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		}
	}
	  private void drawPacmanDown(Graphics2D g2d) {

		switch (pacmanAnimPos) {
		case 1:
		  g2d.drawImage(pacman2down, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 2:
		  g2d.drawImage(pacman3down, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 3:
		  g2d.drawImage(pacman4down, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		default:
		  g2d.drawImage(pacman1, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		}
	}
	  private void drawPacnanLeft(Graphics2D g2d) {

		switch (pacmanAnimPos) {
		case 1:
		  g2d.drawImage(pacman2left, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 2:
		  g2d.drawImage(pacman3left, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 3:
		  g2d.drawImage(pacman4left, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		default:
		  g2d.drawImage(pacman1, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		}
	}
	  private void drawPacmanRight(Graphics2D g2d) {

		switch (pacmanAnimPos) {
		case 1:
		  g2d.drawImage(pacman2right, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 2:
		  g2d.drawImage(pacman3right, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		case 3:
		  g2d.drawImage(pacman4right, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		default:
		  g2d.drawImage(pacman1, app.model.player.pacman_x + 1, app.model.player.pacman_y + 1, this);
		  break;
		}
	}

	  private void drawMaze(Graphics2D g2d) {

		short i = 0;
		int x, y;
	
		for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
		  for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
	
			g2d.setColor(mazeColor);
			g2d.setStroke(new BasicStroke(2));
	
			if ((screenData[i] & 1) != 0) {
			  g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
			}
	
			if ((screenData[i] & 2) != 0) {
			  g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
			}
	
			if ((screenData[i] & 4) != 0) {
			  g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
			}
	
			if ((screenData[i] & 8) != 0) {
			  g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
			}
	
			if ((screenData[i] & 16) != 0) {
			  g2d.setColor(dotColor);
			  g2d.fillRect(x + 11, y + 11, 2, 2);
			}
	
			i++;
		  }
		}
	  }
	  
//	  private void moveGhosts(Graphics2D g2d) {
//
//			short i;
//			int pos;
//			int count;
//
//			for (i = 0; i < N_GHOSTS; i++) {
//			  if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
//				pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (ghost_y[i] / BLOCK_SIZE);
//
//				count = 0;
//
//				if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
//				  dx[count] = -1;
//				  dy[count] = 0;
//				  count++;
//				}
//
//				if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
//				  dx[count] = 0;
//				  dy[count] = -1;
//				  count++;
//				}
//
//				if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
//				  dx[count] = 1;
//				  dy[count] = 0;
//				  count++;
//				}
//
//				if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
//				  dx[count] = 0;
//				  dy[count] = 1;
//				  count++;
//				}
//
//				if (count == 0) {
//
//				  if ((screenData[pos] & 15) == 15) {
//					ghost_dx[i] = 0;
//					ghost_dy[i] = 0;
//				  } else {
//					ghost_dx[i] = -ghost_dx[i];
//					ghost_dy[i] = -ghost_dy[i];
//				  }
//
//				} else {
//
//				  count = (int) (Math.random() * count);
//
//				  if (count > 3) {
//					count = 3;
//				  }
//
//				  ghost_dx[i] = dx[count];
//				  ghost_dy[i] = dy[count];
//				}
//
//			  }
//
//			  ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
//			  ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
//			  drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
//
//			  if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12) && pacman_y > (ghost_y[i] - 12)
//				  && pacman_y < (ghost_y[i] + 12) && inGame) {
//
//				dying = true;
//			  }
//			}
//		  }
}