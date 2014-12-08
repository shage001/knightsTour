//// Knight's Tour
//// Sam Hage, Julian Billings
//// CS201 May 2013
//
//import java.applet.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class Game2 extends Applet implements ActionListener, ItemListener {
//
//	//instance variables
//	private static final long serialVersionUID = 1L;
//	Button new_game, instructions;
//	Choice size;
//	Chess_Board board;
//	Image knight;
//	Label score_label;
//	int score = 1;
//
//	public void init () { //sets up the applet's layout
//
//		setLayout(new BorderLayout());
//		Panel p1 = new Panel();
//		p1.setLayout(new GridLayout(1, 2));
//
//		new_game = new Button("New Game");
//		new_game.setBackground(Color.blue);
//		new_game.setForeground(Color.white);
//		new_game.addActionListener(this);
//		p1.add(new_game);
//		instructions = new Button("Instructions");
//		instructions.setBackground(new Color(160, 0, 100));
//		instructions.setForeground(Color.white);
//		instructions.addActionListener(this);
//		p1.add(instructions);
//		size = new Choice();
//		size.add("8x8");
//		size.add("5x5");
//		size.addItemListener(this);
//		p1.add(size);
//
//		score_label = new Label("Score: " + score);
//		p1.add(score_label);
//		knight = getImage(getDocumentBase(), "knight.jpg");
//		board = new Chess_Board(knight, this);
//		board.addMouseListener(board);
//		board.setBackground(Color.blue.darker());
//
//		add("North", p1);
//		add("Center", board);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		//handles button presses
//		if (e.getSource() == new_game) 
//			board.restart(); 
//		else if (e.getSource() == instructions)
//			board.instruct();
//	}
//
//	public void itemStateChanged(ItemEvent e) {
//		//handles drop-down size button
//		if (e.getSource() == size) {
//			int game_state = size.getSelectedIndex();
//			board.set_board(game_state);
//			board.restart();
//		}
//	}
//}
//
////-------------------------------------------------------------------------------------------------------------------
//
//class Chess_Board extends Canvas implements MouseListener {
//
//	//instance variables
//	private static final long serialVersionUID = 1L;
//	protected static final int BOX_UNIT = (int) Math.round(500/8.0);
//	Image knight;
//	boolean new_game = true;
//	int x = 0;
//	int y = 0;
//	int[] squares = new int[64];
//	int score = 1;
//	Game2 parent;
//	boolean game_over = false;
//	boolean instruction_page = false;
//	boolean small = false;
//
//	//constructor
//	public Chess_Board (Image k, Game2 s) {
//
//		//setting black and white squares
//		for (int i = 0; i <= 63; i++) {
//			if ((i/8) % 2 == 0) {
//				if ((i%8) % 2 == 0)
//					squares[i] = 0;
//				else 
//					squares[i] = 1;
//			} else {
//				if ((i%8) % 2 == 0)
//					squares[i] = 1;
//				else 
//					squares[i] = 0;
//			}
//		}
//		squares[0] = 2; //makes starting square red
//		squares[10] = 3; //gives first option
//		squares[17] = 3; //gives second option
//		knight = k;
//		parent = s;
//	}
//
//	public void paint(Graphics g) {
//
//		int[] temp_array = new int[8];
//		int[] final_array = new int[8];
//		int current = coordinates_to_index(x, y);
//
//		//filling temp_array array with possible indices
//		if((current%8) > 1 && (current/8) > 0 && squares[current - 10] != 2)
//			temp_array[2] = current-10;
//		if((current%8) > 1 && (current/8) < 7 && squares[current + 6] != 2)
//			temp_array[4] = current+6;
//		if((current%8) < 6 && (current/8) > 0 && squares[current - 6] != 2)
//			temp_array[3] = current-6;
//		if((current%8) < 6 && (current/8) < 7 && squares[current + 10] != 2)
//			temp_array[5] = current+10;
//		if((current%8) > 0 && (current/8) > 1 && squares[current - 17] != 2)
//			temp_array[0] = current-17;
//		if((current%8) < 7 && (current/8) > 1 && squares[current - 15] != 2)
//			temp_array[1] = current-15;
//		if((current%8) > 0 && (current/8) < 6 && squares[current + 15] != 2)
//			temp_array[6] = current+15;
//		if((current%8) < 7 && (current/8) < 6 && squares[current + 17] != 2)
//			temp_array[7] = current+17;
//
//		//setting possible indices' color to green
//		for (int i = 0; i <= 7; i++) {
//			if (temp_array[i] >= 0 && temp_array[i] <= 63)
//				final_array[i] = temp_array[i];
//			if (final_array[i] != 0)         //making sure empty indices in final_array are ignored
//				squares[final_array[i]] = 3;    
//		}
//
//		fill_grid(g, 500, 500);
//		g.drawImage(knight, x+1, y+1, this); //draws the knight (it's slightly smaller than a square)
//		parent.score_label.setText("Score: " + score);
//
//		//checks if any squares are green, i.e. if there are any possible moves
//		boolean game_over = true;
//
//		if (small) {             //checking if the game is over for the 5x5 board
//			for (int i =0; i < 64; i++) {
//				if ((i%8 < 5) && (i/8 < 5)) {
//					if (squares[i] == 3) {
//						game_over = false;
//						break;
//					}
//				}
//			}
//		} else {                        //checking if the game is over for the 8x8 board
//			for (int i = 0; i < 64; i++)
//				if (squares[i] == 3) {
//					game_over = false;
//					break;
//				}
//		}
//
//		if (game_over) {                         //sets applet to game over screen
//			g.setFont(new Font("TimesRoman", Font.BOLD, 18));
//			g.setColor(Color.black);
//			g.fillRect(0, 0, BOX_UNIT*8, BOX_UNIT*8);
//			g.setColor(Color.white);
//			g.drawString("GAME OVER", 190, 235);
//			g.drawString("SCORE: " + score, 200, 265);
//
//			if ((! small && score == 64) || (small && score == 25)) { //checking to see if user has won
//				g.setFont(new Font("TimesRoman", Font.BOLD, 24));
//				g.drawString("YOU WIN!", 190, 300);
//			}
//		}
//
//		if (instruction_page) {  //sets applet to instruction page
//			g.setFont(new Font("TimesRoman", Font.BOLD, 18));
//			g.setColor(Color.blue.darker());
//			g.fillRect(0, 0, BOX_UNIT*8, BOX_UNIT*8);
//			g.setColor(Color.white);
//			g.drawString("INSTRUCTIONS", 170, 150);
//			g.setFont(new Font("TimesRoman", Font.BOLD, 12));
//			String text1 = "Move the knight around the board using only legal moves";
//			String text2 = "Legal moves are shown in green, visited spaces are shown in red";
//			String text3 = "Your score is the number of squares you visit; visit them all to win!";
//			g.drawString(text1, 45, 170);
//			g.drawString(text2, 20, 185);
//			g.drawString(text3, 15, 200);
//		}
//	}
//
//	public void fill_grid (Graphics g, int height, int width) {
//		//draws the grid
//
//		if (! small) {   //drawing 8x8 grid
//			for (int j = 0; j <= 7; j++) {  //this loop draws squares vertically
//
//				for (int i = 0; i <= 7; i++) {   //this loop draws squares horizontally
//					if (squares[j*8 + i] == 0)
//						g.setColor(Color.white);
//					else if (squares[j*8 + i] == 1)
//						g.setColor(Color.black);
//					else if (squares[j*8 + i] == 2) 
//						g.setColor(Color.red);
//					else
//						g.setColor(Color.green);
//
//					g.fillRect(i*BOX_UNIT, j*BOX_UNIT, BOX_UNIT, BOX_UNIT);
//				}
//			}
//		} else {                 //drawing 5x5 grid
//			for (int j = 0; j <= 4; j++) {  //this loop draws squares vertically
//
//				for (int i = 0; i <= 4; i++) {   //this loop draws squares horizontally
//					if (squares[j*8 + i] == 0)
//						g.setColor(Color.white);
//					else if (squares[j*8 + i] == 1)
//						g.setColor(Color.black);
//					else if (squares[j*8 + i] == 2) 
//						g.setColor(Color.red);
//					else
//						g.setColor(Color.green);
//
//					g.fillRect(i*BOX_UNIT, j*BOX_UNIT, BOX_UNIT, BOX_UNIT);
//				}
//			}
//		}
//	}
//
//	public static int coordinates_to_index(int x, int y) {
//		//returns the index from coordinates
//
//		int column_val = x / BOX_UNIT;
//		int row_val = 8 * (y / BOX_UNIT);
//		return row_val + column_val;
//	}
//
//	public static int[] index_to_coordinates(int index) {
//		//returns an array containing the coordinates of the top-left corner of the box with the given index
//
//		int x = (index%8)*BOX_UNIT;
//		int y = (index/8)*BOX_UNIT;
//		int[] coords = new int[2];
//		coords[0] = x;
//		coords[1] = y;
//		return coords;
//	}
//
//	public void mousePressed(MouseEvent e) {
//		//handles mouse events
//		Point p = e.getPoint();
//		int tempx = p.x;
//		int tempy = p.y;
//		int index = coordinates_to_index(tempx, tempy);
//		int current = coordinates_to_index(x, y);
//
//		//checking if attempted move is legal
//		int[] temp_array = new int[8];
//		int[] final_array = new int[8];
//		temp_array[0] = current-17;
//		temp_array[1] = current-15;
//		temp_array[2] = current-10;
//		temp_array[3] = current-6;
//		temp_array[4] = current+6;
//		temp_array[5] = current+10;
//		temp_array[6] = current+15;
//		temp_array[7] = current+17;
//		for (int i = 0; i <= 7; i++)
//			if (temp_array[i] >= 0 && temp_array[i] <= 63)
//				final_array[i] = temp_array[i];
//
//		boolean contains = false;
//		for (int i=0; i <= 7; i++)
//			if (! small) {
//				if (index == final_array[i]){
//					contains = true;
//					break;
//				}
//			} else {
//				if (index == final_array[i] && (final_array[i]%8) < 5 && (final_array[i]/8) < 5){
//					contains = true;
//					break;
//				}
//			}
//
//		if (contains && squares[index] != 2) {          //if move is legal:
//
//			for (int i = 0; i <=7; i++) {   //re-coloring green squares from before the move back to black/white
//				if (final_array[i] != 0 && squares[final_array[i]] != 2) {
//					if (final_array[i]/8 % 2 == 0) {
//						if ((final_array[i]%8) % 2 == 0)
//							squares[final_array[i]] = 0;
//						else
//							squares[final_array[i]] = 1;
//					} else {
//						if ((final_array[i]%8) % 2 == 0)
//							squares[final_array[i]] = 1;
//						else
//							squares[final_array[i]] = 0;
//					}
//				}
//			}
//
//			int[] coordinates = index_to_coordinates(index); //changing the x and y values
//			x = coordinates[0]; 
//			y = coordinates[1];
//
//			squares[index] = 2;
//			score += 1;
//			if (score == 64) game_over = true;
//			parent.score_label.setText("Score: " + score);
//			repaint();
//		}
//	}
//
//	public void restart() {
//		//this method resets the board to its original state
//
//		for (int i = 0; i <= 63; i++) {
//			if ((i/8) % 2 == 0) {
//				if ((i%8) % 2 == 0)
//					squares[i] = 0;
//				else 
//					squares[i] = 1;
//			} else {
//				if ((i%8) % 2 == 0)
//					squares[i] = 1;
//				else 
//					squares[i] = 0;
//			}
//		}
//		squares[0] = 2; //makes starting square red
//		squares[10] = 3;
//		squares[17] = 3;
//		x = 0;
//		y = 0;
//		score = 1;
//		game_over = false;
//		instruction_page = false;
//		repaint();
//	}
//
//	public void instruct() {
//		//method causes instruction screen to be displayed or not
//
//		if (! instruction_page) {
//			instruction_page = true;
//			repaint();
//		} else {
//			instruction_page = false;
//			repaint();
//		}
//	}
//
//	public void set_board (int s) {
//		//method determines whether board is 8x8 or 5x5
//
//		int size_choice = s;
//		if (size_choice == 0)
//			small = false;
//		else
//			small = true;
//		repaint();
//	}
//
//	public void mouseEntered(MouseEvent arg0) {}
//	public void mouseExited(MouseEvent arg0) {}
//	public void mouseClicked(MouseEvent arg0) {}
//	public void mouseReleased(MouseEvent arg0) {}
//}