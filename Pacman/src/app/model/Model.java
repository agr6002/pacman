package app.model;

import java.awt.Graphics2D;

import app.App;
import app.Config;

public class Model implements Config {

  public App app;
  public Player player;
  public Maze maze;
  public Ghost[] ghosts = new Ghost[N_GHOSTS];

  public int pacsLeft; // Model
  public int score;
  public int req_dx;

  public Model(App app) {
	this.app = app;
	player = new Player();
	maze = new Maze();

	for (int i = 0; i < N_GHOSTS; i++) {
	  ghosts[i] = new Ghost();
	}

  }

  public void update(long timeChange) {
	player.update(timeChange);
	for (int i = 0; i < N_GHOSTS; i++) {
	  ghosts[i].update(timeChange);
	}

  }

  public void initGame() { // model

	pacsLeft = 3;
	score = 0;
	initLevel();
	N_GHOSTS = 6;
	currentSpeed = 3;
  }

  public void initLevel() { // model

	int i;
	for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
	  screenData[i] = levelData[i];
	}

	continueLevel();
  }

  public void continueLevel() { // model

	short i;
	int dx = 1;
	int random;

	for (i = 0; i < N_GHOSTS; i++) {

	  ghost_y[i] = 4 * BLOCK_SIZE;
	  ghost_x[i] = 4 * BLOCK_SIZE;
	  ghost_dy[i] = 0;
	  ghost_dx[i] = dx;
	  dx = -dx;
	  random = (int) (Math.random() * (currentSpeed + 1));

	  if (random > currentSpeed) {
		random = currentSpeed;
	  }

	  ghostSpeed[i] = validSpeeds[random];
	}

	pacman_x = 7 * BLOCK_SIZE;
	pacman_y = 11 * BLOCK_SIZE;
	pacmand_x = 0;
	pacmand_y = 0;
	req_dx = 0;
	req_dy = 0;
	view_dx = -1;
	view_dy = 0;
	dying = false;
  }

}
public void moveGhosts(Graphics2D g2d) {

	short i;
	int pos;
	int count;

	for (i = 0; i < N_GHOSTS; i++) {
	  
	}
}