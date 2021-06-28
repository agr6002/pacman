package app.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.App;
import app.model.Player;

public class Controller implements KeyListener {
  private App app;
  private Player player;

  public Controller(App app) {
	this.app = app;
	this.player = app.model.player;
  }

  @Override
  public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();

	if (app.inGame) {
	  if (key == KeyEvent.VK_LEFT) {
		player.req_dx = -1;
		player.req_dy = 0;
	  } else if (key == KeyEvent.VK_RIGHT) {
		player.req_dx = 1;
		player.req_dy = 0;
	  } else if (key == KeyEvent.VK_UP) {
		player.req_dx = 0;
		player.req_dy = -1;
	  } else if (key == KeyEvent.VK_DOWN) {
		player.req_dx = 0;
		player.req_dy = 1;
	  } else if (key == KeyEvent.VK_ESCAPE && app.timer.isRunning()) {
		app.inGame = false;
	  } else if (key == KeyEvent.VK_PAUSE) {
		if (app.timer.isRunning()) {
		  app.timer.stop();
		} else {
		  app.timer.start();
		}
	  }
	} else {
	  if (key == KeyEvent.VK_S) {
		app.inGame = true;
		app.model.initGame();
	  }
	}
  }

  @Override
  public void keyReleased(KeyEvent e) {
	int key = e.getKeyCode();

	if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
	  player.req_dx = 0;
	  player.req_dy = 0;
	}
  }

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}
}