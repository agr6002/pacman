package app.model;

import app.Config;

public class Player {

  public int pacmanAnimPos = 0;
  public boolean dying = false;

  public int pacman_x, pacman_y, pacmand_x, pacmand_y;

  public int[] dx, dy; // Model

  public int req_dx, req_dy, view_dx, view_dy;

  public void update(long timeChange) {
	// TODO
  }

  public void movePacman() {

	int pos;
	short ch;

	if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
	  pacmand_x = req_dx;
	  pacmand_y = req_dy;
	  view_dx = pacmand_x;
	  view_dy = pacmand_y;
	}

	if (pacman_x % Config.BLOCK_SIZE == 0 && pacman_y % Config.BLOCK_SIZE == 0) {
	  pos = pacman_x / Config.BLOCK_SIZE + N_BLOCKS * (pacman_y / Config.BLOCK_SIZE);
	  ch = screenData[pos];

	  if ((ch & 16) != 0) {
		screenData[pos] = (short) (ch & 15);
		score++;
	  }

	  if (req_dx != 0 || req_dy != 0) {
		if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0) || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
			|| (req_dx == 0 && req_dy == -1 && (ch & 2) != 0) || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
		  pacmand_x = req_dx;
		  pacmand_y = req_dy;
		  view_dx = pacmand_x;
		  view_dy = pacmand_y;
		}
	  }

	  // Check for standstill
	  if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0) || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
		  || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
		  || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
		pacmand_x = 0;
		pacmand_y = 0;
	  }
	}
	pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
	pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
  }

}

public void death() { // model

	pacsLeft--;

	if (pacsLeft == 0) {
	  inGame = false;
	}

	continueLevel();
}