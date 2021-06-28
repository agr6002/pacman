package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.Timer;

//import app.controller.TAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.controller.Controller;
import app.model.Model;
import app.view.View;

public class App extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public boolean inGame = false;
	public Timer timer;
	
	public Model model;
	public View view;
	public Controller controller;
	
	public App() {
		this.model = new Model(this);
		this.view = new View(this);
		this.controller = new Controller(this);
		
		initUI();
		initTimer();
		initBoard();
	}

	  private void initBoard() { // app

		addKeyListener(controller);

		setFocusable(true);

		setBackground(Color.black);
	  }

	public void playGame(Graphics2D g2d) {

		if (model.player.dying) {

			model.player.death();

		} else {

			model.player.movePacman();
			view.drawPacman(g2d);
			model.moveGhosts(g2d);
			model.maze.checkMaze();
		}
	}
	private void initUI() {
	
		add(view);
	
		setTitle("Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 420);
		setLocationRelativeTo(null);
	}
	
	private void initTimer() {
		timer = new Timer(40, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
}