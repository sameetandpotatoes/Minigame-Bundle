package finalProject;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import soccer.*;
import chess.*;
import othello.*;
import minesweeper.*;
import worldconquest.*;

public class FinalProjectRunner
{
	private static Player one;
	private static Player two;
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();

		//The buttons that will be displayed on the frame
		JButton button = new JButton("Othello");
		button.setFont(new Font("SansSerif", 0, 14));
		JButton button1 = new JButton("Soccer");
		button1.setFont(new Font("SansSerif", 0, 14));
		JButton button2 = new JButton("Minesweeper (1 Player Game)");
		button2.setFont(new Font("SansSerif", 0, 14));
		JButton button4 = new JButton("Chess (2 Player Game)");
		button4.setFont(new Font("SansSerif", 0, 14));
		JButton button5 = new JButton("World Conquest (1 Player Game)");
		button5.setFont(new Font("SansSerif", 0, 14));
		JButton button3 = new JButton("End Program");
		button3.setFont(new Font("SansSerif", 0, 14));

		//Names
		JLabel sameet = new JLabel("Sameet Sapra- Othello, Chess");
		sameet.setFont(new Font("Monospaced", Font.BOLD, 13));
		JLabel yaseen = new JLabel("Yaseen Saleh- Soccer, World Conquest");
		yaseen.setFont(new Font("Monospaced", Font.BOLD, 13));
		JLabel tim = new JLabel("Tim Zhou- Minesweeper");
		tim.setFont(new Font("Monospaced", Font.BOLD, 13));
		JLabel period = new JLabel("Period 7");
		period.setFont(new Font("Monospaced", Font.BOLD, 13));
		JPanel creditsPanel = new JPanel();

		//creditsPanel will contain the names
		creditsPanel.setFont(new Font("Monospaced", Font.BOLD, 15));
		creditsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Created by:"));
		creditsPanel.setLayout(new GridLayout(4, 1));
		creditsPanel.add(sameet);
		creditsPanel.add(yaseen);
		creditsPanel.add(tim);
		creditsPanel.add(period);

		//panel will contain all of the buttons and the creditsPanel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Ultimate Collection of Mini Games"));
		panel.add(button);
		panel.add(button1);
		panel.add(button2);
		panel.add(button4);
		panel.add(button5);
		panel.add(button3);
		panel.add(creditsPanel);

		//frame contains the panel
		frame.add(panel);


		//ActionListener for Othello
		class OthelloListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				//Sets up another frame with buttons for choosing 1 player or 2 player
				JFrame frame = new JFrame();

				JPanel panel = new JPanel();

				JButton button = new JButton("User vs. Computer");
				button.setFont(new Font("SansSerif", 0, 14));
				JButton button1 = new JButton("User vs. User");
				button1.setFont(new Font("SansSerif", 0, 14));

				panel.add(button);
				panel.add(button1);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(panel);
				frame.setVisible(true);
				frame.setSize(200, 110);
				frame.setTitle("Choose preferences.");
				frame.setLocation(500, 500);
				class OneListener implements ActionListener
				{
					public void actionPerformed(ActionEvent event)
					{
						new OthelloWorld(true).show();
					}
				}
				class TwoListener implements ActionListener
				{
					public void actionPerformed(ActionEvent event)
					{
						new OthelloWorld(false).show();
					}
				}
				button.addActionListener(new OneListener());
				button1.addActionListener(new TwoListener());
			}
		}
		//ActionListener for Soccer
		class SoccerListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				//Sets up another frame with buttons for choosing 1 player or 2 player
				JFrame frame = new JFrame();

				JPanel panel = new JPanel();

				JButton button = new JButton("User vs. Computer");
				button.setFont(new Font("SansSerif", 0, 14));
				JButton button1 = new JButton("User vs. User");
				button1.setFont(new Font("SansSerif", 0, 14));

				panel.add(button);
				panel.add(button1);
				frame.add(panel);
				frame.setVisible(true);
				frame.setSize(200, 110);
				frame.setLocation(500, 500);
				class OneListener implements ActionListener
				{
					public void actionPerformed(ActionEvent event)
					{
						//Sets up another frame for directions/choosing players
						JFrame frame= new JFrame();

						JButton iniesta = new JButton("Andres Iniesta");
						JButton ronaldo = new JButton("Cristiano Ronaldo");
						JButton messi = new JButton("Lionel Messi");
						JButton ozil = new JButton("Mezut Ozil");

						JPanel directions = new JPanel();
						directions.setLayout(new GridLayout(3, 1));
						directions.setBorder(new TitledBorder(new EtchedBorder(), "Directions"));
						directions.add(new JLabel("Click on the buttons to choose your players"));
						directions.add(new JLabel("Player A: Uses W, A, S, D"));
						directions.add(new JLabel("Player B: Uses arrow keys"));

						JPanel playerPanel = new JPanel();
						playerPanel.setLayout(new GridLayout(5, 1));
						playerPanel.add(directions);
						playerPanel.add(iniesta);
						playerPanel.add(ronaldo);
						playerPanel.add(messi);
						playerPanel.add(ozil);

						class IniestaListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new AndresIniesta();
								SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , null);
								theBoard.show();
								theBoard.play();
							}
						}
						class RonaldoListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new CristianoRonaldo();
								SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , null);
								theBoard.show();
								theBoard.play();
							}
						}
						class MessiListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new LionelMessi();
								SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , null);
								theBoard.show();
								theBoard.play();
							}
						}
						class OzilListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new MezutOzil();
								SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , null);
								theBoard.show();
								theBoard.play();
							}
						}
						ActionListener listen = new IniestaListener();
						ActionListener listen1 = new RonaldoListener();
						ActionListener listen2 = new MessiListener();
						ActionListener listen3 = new OzilListener();

						//Attaches listeners to buttons
						iniesta.addActionListener(listen);
						ronaldo.addActionListener(listen1);
						messi.addActionListener(listen2);
						ozil.addActionListener(listen3);

						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.add(playerPanel);
						frame.setSize(280,500);
						frame.setLocation(650, 400);
						frame.setResizable(false);
						frame.setVisible(true);
					}
				}
				class TwoListener implements ActionListener
				{
					public void actionPerformed(ActionEvent event)
					{
						//Sets up another frame for directions/choosing players
						JFrame frame= new JFrame();

						JButton iniesta = new JButton("Andres Iniesta");
						JButton ronaldo = new JButton("Cristiano Ronaldo");
						JButton messi = new JButton("Lionel Messi");
						JButton ozil = new JButton("Mezut Ozil");

						JPanel directions = new JPanel();
						directions.setLayout(new GridLayout(3, 1));
						directions.setBorder(new TitledBorder(new EtchedBorder(), "Directions"));
						directions.add(new JLabel("Click on the buttons to choose your players"));
						directions.add(new JLabel("Player A: Uses W, A, S, D"));
						directions.add(new JLabel("Player B: Uses arrow keys"));

						JPanel playerPanel = new JPanel();
						playerPanel.setLayout(new GridLayout(5, 1));
						playerPanel.add(directions);
						playerPanel.add(iniesta);
						playerPanel.add(ronaldo);
						playerPanel.add(messi);
						playerPanel.add(ozil);

						class IniestaListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new AndresIniesta();
								else
									two = new AndresIniesta();

								if (one != null && two != null)
								{
									SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , two);
									theBoard.show();
									theBoard.play();
								}
							}
						}
						class RonaldoListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new CristianoRonaldo();
								else
									two = new CristianoRonaldo();
								if (one != null && two != null)
								{
									SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , two);
									theBoard.show();
									theBoard.play();
								}
							}
						}
						class MessiListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new LionelMessi();
								else
									two = new LionelMessi();

								if (one != null && two != null)
								{
									SoccerWorld theBoard=new SoccerWorld(new BoundedGrid(17,17),one , two);
									theBoard.show();
									theBoard.play();
								}

							}
						}
						class OzilListener implements ActionListener
						{
							public void actionPerformed(ActionEvent event)
							{
								if(one == null)
									one = new MezutOzil();
								else
									two = new MezutOzil();
								if (one != null && two != null)
								{
									SoccerWorld world =new SoccerWorld(new BoundedGrid(17,17),one , two);
									world.show();
									world.play();
								}
							}
						}
						ActionListener listen = new IniestaListener();
						ActionListener listen1 = new RonaldoListener();
						ActionListener listen2 = new MessiListener();
						ActionListener listen3 = new OzilListener();

						//Attaches listeners to buttons
						iniesta.addActionListener(listen);
						ronaldo.addActionListener(listen1);
						messi.addActionListener(listen2);
						ozil.addActionListener(listen3);

						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.add(playerPanel);
						frame.setSize(280,500);
						frame.setLocation(650, 400);
						frame.setResizable(false);
						frame.setVisible(true);
					}
				}
				button.addActionListener(new OneListener());
				button1.addActionListener(new TwoListener());
			}
		}
		//ActionListener for Minesweeper
		class MineSweeperListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
		        new MinesweeperWorld().show();
			}
		}
		//ActionListener for Chess
		class ChessListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				new ChessWorld().show();
			}
		}
		//ActionListener for World Conquest
		class WorldListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				new WorldConquest().show();
			}
		}
		//ActionListener to end program
		class EndProgram implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		}
		ActionListener othelloListener = new OthelloListener();
		ActionListener soccerListener = new SoccerListener();
		ActionListener mineListener = new MineSweeperListener();
		ActionListener chessListener = new ChessListener();
		ActionListener worldListener = new WorldListener();
		ActionListener endProgram = new EndProgram();

		//Attaches listeners to buttons
		button.addActionListener(othelloListener);
		button1.addActionListener(soccerListener);
		button2.addActionListener(mineListener);
		button4.addActionListener(chessListener);
		button5.addActionListener(worldListener);
		button3.addActionListener(endProgram);

		frame.setSize(330, 600);
		frame.setResizable(false);
		frame.setLocation(750, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Ultimate Collection of Mini Games");
		frame.setVisible(true);
	}
}
