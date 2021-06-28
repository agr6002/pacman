package app;

import java.awt.Color;

public interface Config {

  public int N_GHOSTS = 6;
  public final int N_BLOCKS = 15;
  public final int MAX_GHOSTS = 12;
  public final int PACMAN_SPEED = 6;

  public final int BLOCK_SIZE = 24;
  public final int SCREEN_SIZE = Config.N_BLOCKS * BLOCK_SIZE;
  public final int PAC_ANIM_DELAY = 2;
  public final int PACMAN_ANIM_COUNT = 4;

  public final Color DOT_COLOR = new Color(192, 192, 0);
}
