package app.model;

import app.Config;

public class Ghost {

  public final int validSpeeds[] = { 1, 2, 3, 4, 6, 8 }; // Model
  public final int maxSpeed = 6; // Model
  public int currentSpeed = 3; // Model

  public int posX;
  public int posY;
  public int dx;
  public int dy;
  public int speed;

  public void update(long timeChange) {

	int pos;
	int count;

	if (posX % Config.BLOCK_SIZE == 0 && posY % Config.BLOCK_SIZE == 0) {
	  pos = posX / Config.BLOCK_SIZE + Config.N_BLOCKS * (posY / Config.BLOCK_SIZE);

	  count = 0;

	  if ((screenData[pos] & 1) == 0 && dx != 1) {
		dx[count] = -1;
		dy[count] = 0;
		count++;
	  }

	  if ((screenData[pos] & 2) == 0 && dy != 1) {
		dx[count] = 0;
		dy[count] = -1;
		count++;
	  }

	  if ((screenData[pos] & 4) == 0 && dx != -1) {
		dx[count] = 1;
		dy[count] = 0;
		count++;
	  }

	  if ((screenData[pos] & 8) == 0 && dy != -1) {
		dx[count] = 0;
		dy[count] = 1;
		count++;
	  }

	  if (count == 0) {

		if ((screenData[pos] & 15) == 15) {
		  dx = 0;
		  dy = 0;
		} else {
		  dx = -dx;
		  dy = -dy;
		}

	  } else {

		count = (int) (Math.random() * count);

		if (count > 3) {
		  count = 3;
		}

		ghost_dx[i] = dx[count];
		ghost_dy[i] = dy[count];
	  }

	}

	ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
	ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
	drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

	if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12) && pacman_y > (ghost_y[i] - 12)
		&& pacman_y < (ghost_y[i] + 12) && inGame) {

	  dying = true;
	}
  }

}
