import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA2 
{
	static int fileNumber = 1;
	public static void main(String[] args) throws IOException 
	{
		String firstInputFile = "pa2_input_1.txt";
		buildTheBoard(firstInputFile);
		
		
		System.out.println("\n\n\nSECOND INPUT");
		
		String secondInputFile = "pa2_input_2.txt";
		buildTheBoard(secondInputFile);
		
		System.out.println("\n\n\nTHIRD INPUT");
		String thirdInputFile = "pa2_input_3.txt";
		buildTheBoard(thirdInputFile);
		
		
		

	}// main
	
	private static void buildTheBoard(String fileName) throws IOException
	{
		if(fileName == null)
		{
			
		}
		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);
		int N = readFile.nextInt();
		int pastTurns = readFile.nextInt();
		System.out.println(pastTurns);
	
		// build char board
		char[][] board  = new char[N][N];
		
		
		// store coordinates of past k turns
		Shell[] pastTurnLimitShell = new Shell[N*N];;
				
		boolean continueGame = true;
		
		// counting plays
		int turn = 1;
		int player1InvalidMove = 0;
		int player2InvalidMove = 0;	
		int sr ,sc ,er ,ec ;
		// continue plays until conditions meets
		
		while(readFile.hasNextInt())
		{
			sr = readFile.nextInt()-1;
			sc = readFile.nextInt()-1;
			er = readFile.nextInt()-1;
			ec = readFile.nextInt()-1;
			
			if(continueGame == true)
			{
				boolean isValid = true;
				System.out.println( "\n\nTURN: " + turn);
				
				// array of past turns shell index
				pastTurnLimitShell[turn-1] = new Shell(sr,sc,er,ec);
				System.out.print(sr + " ");
				System.out.print(sc + " ");
				System.out.print(er + " ");
				System.out.println(ec + " ");
				isValid = validStartAndEndShell(sr,sc,er,ec,pastTurns , pastTurnLimitShell, board , turn);
				System.out.println( "ISVALID: " + turn + " : "+ isValid);
				char fillColor;
				if(turn%2 == 0)
				{
					fillColor = 'O';
				}
				
				// else for 1st player
				else
				{
					fillColor = 'X';
					
				
				}// else for 1st player
				if(isValid == true)
				{
					
					// if its second player
					if(turn%2 == 0)
					{
						
						fillBoard(sr , sc, fillColor , board);
						fillBoard(er , ec, fillColor, board);
					}
					
					// else for 1st player
					else
					{
						
						fillBoard(sr , sc, fillColor, board);
						fillBoard(er , ec, fillColor, board);
					
					}// else for 1st player
					Vector<Integer> currentPlayVector = new Vector<Integer>();
					currentPlayVector = pastTurnLimitShell[turn-1].getPastTurnsVector();
					
					drawColor(sr , sc , er , ec , board , currentPlayVector , fillColor);
				}// if statement
				
				// To keep track if both players makes invalid move
				else
				{
					if(turn%2 == 0)
					{
						player2InvalidMove++;
						System.out.println(" player2InvalidMove: " + player2InvalidMove);
					}
					else
					{
						player1InvalidMove++;
						System.out.println(" player1InvalidMove: " + player1InvalidMove);
					}
				}
				
		
				//Both player has invalid play game will not continue
				if((player2InvalidMove >= 1) && (player1InvalidMove >= 1))
				{
					continueGame = false;
				}
				
				
				System.out.println("VECTORS: " +pastTurnLimitShell[turn-1].getPastTurnsVector());
				
				turn++;
				System.out.println( "T/F: " + continueGame);
				
			}// if statement to continue game
			
		}// while continue Game
		
		
		displayBoard(board);
		findWinner(board);
		
		readFile.close();
		
		writeToThefile(board);
	}// buildTheBoard method

	public static  void writeToThefile(char[][] board) throws IOException 
	{
		
		String fileName = " ";
		switch(fileNumber)
		{
			case 1 : fileName = "pa2_output_1.txt";
				break;
			case 2 : fileName = "pa2_output_2.txt";
				break;
			
			case 3 : fileName = "pa2_output_3.txt";
				break;
			
			case 4 : fileName = "abasnet_output_1.txt";
				break;
			
			case 5 : fileName = "abasnet_output_2.txt";
				break;
		
		}
		FileWriter output2 = new FileWriter(fileName);
		
		int size = board.length;
		for(int row = 0 ; row < size ; row++)
		{
			for(int col = 0 ; col < size ; col++ )
			{
				output2.write(board[row][col] + " " );
			}
			output2.write("\n");
		}
		output2.close();
		fileNumber++;
		
		
		
		
	}// write to file method

	private static  boolean validStartAndEndShell(int sr, int sc , int er, int ec, int pastTurns , Shell[] pastTurnLimitShell , char[][]board , int turn )
	{
		boolean valid = true;
		
		// to store past turns starting and ending shell index
		int pastTurnsSr , pastTurnsSc , pastTurnsEr, pastTurnsEc;
		
		// when staring and ending shell is empty
		if(board[sr][sc] == 0 &&  board[er][ec] == 0 )
		{
				valid = true;
		}// end of if statement
		
		// When atleast one of the shell is not empty
		else
		{
			// compare past k turns shell with current turn
			if(board[sr][sc] != 0 ||  board[er][ec] != 0 )
			{
				// comparing current start/end shell with past K turns start/end shells
				for(int i = turn - 2 ; i >= (turn - (pastTurns + 1)) ; i--)
				{
					// to make sure array index is not negative
					if(i >= 0)
					{
						// getting shell col and row from pastTurnshell array 
						pastTurnsSr = pastTurnLimitShell[i].getStartingRow();
						pastTurnsSc = pastTurnLimitShell[i].getStartingColumn();
						pastTurnsEr = pastTurnLimitShell[i].getEndingRow();
						pastTurnsEc = pastTurnLimitShell[i].getEndingColumn();
						
						// if the current starting shell is same as past k turns starting or ending shell
						if((sr == pastTurnsSr && sc == pastTurnsSc) || (sr == pastTurnsEr && sc == pastTurnsEc))
						{
							valid = false;
							i = -1;
						}
						
						// if the current ending shell is same as past k turns starting or ending shell
						else if((er == pastTurnsSr && ec == pastTurnsSc) || (er == pastTurnsEr && ec == pastTurnsEc))
						{
							valid = false;
							i  = -1;
						}
						else
						{
							valid = true;
						}
					}// if statement
					else
					{
						valid = true;
					}// else statement
					System.out.println("Valid shell at " + i +  ": " + valid);
				}// for loop
			}// if statement
			
			// current coordinate is not same as past k turns
			else
			{
				valid = true;
			}//inner else statement
			
		}// else statement
		
		if(valid == true)
		{
			valid = validMidPoint(sr,sc,er,ec,pastTurns , pastTurnLimitShell, board , turn);
		}
		
		return valid;
		
		
		
	}// valid start and end shell
	
	private static boolean validMidPoint(int sr, int sc , int er, int ec, int pastTurns , Shell[] pastTurnLimitShell , char[][]board , int turn)
	{
		boolean valid = true;
		
		// mid point of current play
		float currentMidPointX = (float)(er+sr)/2;
		float currentMidPointY = (float)(ec+sc)/2;
		
		float pastTurnsMidPointX , pastTurnsMidPointY;
		
		for(int i = turn - 2 ; i >= (turn - (pastTurns + 1)) ; i--)
		{
			if(i >= 0)
			{
				// assigning past k turns mid point coordinates to variable
				pastTurnsMidPointX = pastTurnLimitShell[i].getmidPointX();
				pastTurnsMidPointY = pastTurnLimitShell[i].getmidPointY();
				
				if((Float.compare(currentMidPointX, pastTurnsMidPointX) == 0) && (Float.compare(currentMidPointY, pastTurnsMidPointY) == 0))
				{
					valid = false;
					System.out.println("Valid mid point at " + i +  ": " + valid);
					i = -1;
				}
				
				else
				{
					valid = true;
				}
				
			}// if statement
			else
			{
				valid = true;
			}
			
		}// for loop
		if(valid == true)
		{
			valid = isLineParallelToAny(sr , sc ,er ,ec , pastTurnLimitShell, board , turn);
		}
		
		return valid;
	}// valid mid point method
	
	private static boolean isLineParallelToAny(int sr, int sc , int er, int ec, Shell[] pastTurnLimitShell , char[][]board , int turn)
	{
		int vectorX = er - sr;
		int vectorY = ec - sc;
		boolean isValid = true;
		// current turn's vector
		Vector<Integer> currentVector = new Vector<Integer>();
		currentVector.add(vectorX);
		currentVector.add(vectorY);
		
		// vector perpendicular to current turn's vector
		Vector<Integer> perpendicularToCurrentVector = new Vector<Integer>();
		perpendicularToCurrentVector.add(-vectorY);
		perpendicularToCurrentVector.add(vectorX);

		//Verify if the vector perpendicular to current line is also perpendicular to past turns vetor to determine third condition to be valid
		int a1 = perpendicularToCurrentVector.get(0);
		int a2 = perpendicularToCurrentVector.get(1);
		
		int pastTurnV1 , pastTurnV2;
		
		// to verify all the past turns to check 3rd condition
		for(int i = turn -2 ; i >= 0 ; i--)
		{
			// to make sure array wont go out of bounds
			if(i > 0)
			{
				pastTurnV1 = pastTurnLimitShell[i].getPastTurnsVector().get(0);
				pastTurnV2 = pastTurnLimitShell[i].getPastTurnsVector().get(1);
				
				// if two vectors are parallel
				if((a1 * pastTurnV1 + a2 * pastTurnV2) == 0)
				{
					isValid =  false;
					System.out.println("Valid not parallel  at " + i +  ": " + isValid);
					i = -1;
				}
				
				// if not parallel
				else
				{
					isValid =  true;
				}
				//System.out.println("Valid not parallel  at " + i +  ": " + isValid);
			}// if statement
			
			
		}// for loop
		
		return isValid ;
		
	}// isLineParallelToAny method
	private static void fillBoard(int row , int col , char fillWithChar , char[][]board)
	{
		board[row][col] = fillWithChar;
	}// fillBoard method
	
	private static void displayBoard(char[][] board) 
	{
		int size = board.length;
		for(int row = 0 ; row < size ; row++)
		{
			for(int col = 0 ; col < size ; col++ )
			{
				if(board[row][col] == 0)
				{
					board[row][col] = '.';
				}
				System.out.print(board[row][col]  + " ");
			}
			System.out.print("\n");
		}
	}// display Board
	
	// draw color to represent line
	private static void drawColor(int sr, int sc, int er, int ec, char[][] board , Vector<Integer> currentPlayVector , char fillColor) 
	{
		int vectX = currentPlayVector.get(0);
		int vectY = currentPlayVector.get(1);
		
		// to compare centers of a cell one at a time
		Vector<Integer> unitVector = getUnitVector(vectX , vectY);
		System.out.println("TestDRAWCOLOR " + unitVector );
		// drawing one cell at a time from starting shell to end
		int movingPx = sr ;
		int movingPy = sc;
		
		int i = unitVector.get(0);
		int j = unitVector.get(1);
		
		boolean isEndOfShell = false;
		// stop drawing once it reaches end shell
		
		while(isEndOfShell == false )
		{
			
			System.out.println("TestDRAWCOLOR " + movingPx );
			System.out.println("TestDRAWCOLOR " + movingPy + "\n");
			// reached end of shell while drawing
			if((movingPx  == er) && (movingPy  == ec))
			{
				isEndOfShell = true;
			}
			else
			{
				//first possible center
				int firstPossibleCenterX = movingPx + i;
				int firstPossibleCenterY = movingPy ;
				
				//W vector to find first possible closest center
				Vector<Integer> firstPossibleVectorW = possibleVector(sr , sc , firstPossibleCenterX , firstPossibleCenterY );
				System.out.println("firstPossibleVectorW: " + firstPossibleVectorW);
				// mag of v squared
				float magnitudeOfCurrentVectorVSquare = (float) (Math.pow(vectX ,2) + Math.pow(vectY ,2));
				
				// t = vector v * vector w / mag v squared
				float parameter_T1 = ((vectX * firstPossibleVectorW.get(0)) + (vectY * firstPossibleVectorW.get(1))) / magnitudeOfCurrentVectorVSquare;
				System.out.println("Parameter_t1: " + parameter_T1);
				// r = p + tv
				float r1 = sr + parameter_T1 * vectX;
				float r2 = sc + parameter_T1 * vectY;
				
				// foot of a line(closest to the line)
				float x = firstPossibleCenterX - r1 ; 
				float y = firstPossibleCenterY - r2 ; 
				float d1 = (float) ((Math.pow(x ,2) + Math.pow(y ,2)));
				
				// distance from center to line
				float first_Distance = (float) Math.sqrt(d1);
			
				// second possible center
				int secondPossibleCeterA = movingPx ;
				int secondPossibleCeterB = movingPy + j;
				
				Vector<Integer> secondPossibleVectorW = possibleVector(sr , sc , secondPossibleCeterA , secondPossibleCeterB );
				System.out.println("secondPossibleVectorW: " + secondPossibleVectorW);
				// mag of v squared
				
				
				// t = vector v * vector w / mag v squared
				float parameter_T2 = ((vectX * secondPossibleVectorW.get(0)) + (vectY * secondPossibleVectorW.get(1))) / magnitudeOfCurrentVectorVSquare;
				System.out.println("Parameter_t2: " + parameter_T2);
				// r = p + tv
				float q1 = sr + parameter_T2 * vectX;
				float q2 = sc + parameter_T2 * vectY;
				
				// foot of a line(closest to the line)
				float a = secondPossibleCeterA - q1 ; 
				float b = secondPossibleCeterB - q2 ; 
				float d2 = (float) ((Math.pow(a ,2) + Math.pow(b ,2)));
				
				// distance from center to line
				float second_Distance = (float) Math.sqrt(d2);
				
				if(first_Distance > second_Distance)
				{
					board[secondPossibleCeterA][secondPossibleCeterB] = fillColor;
					movingPx = secondPossibleCeterA;
					movingPy = secondPossibleCeterB;
					System.out.println("D1: " + first_Distance);
					System.out.println("D2: " + second_Distance);
					System.out.println("COLOR1 : " + secondPossibleCeterA );
					System.out.println("COLOR1: " + secondPossibleCeterB+ "\n");
				}// if statement
				
				else if(first_Distance < second_Distance)
				{
					board[firstPossibleCenterX][firstPossibleCenterY] = fillColor;
					movingPx = firstPossibleCenterX;
					movingPy = firstPossibleCenterY;
					System.out.println("D3: " + first_Distance);
					System.out.println("D4: " + second_Distance);
					System.out.println("COLOR2 : " +firstPossibleCenterX );
					System.out.println("COLOR2: " + firstPossibleCenterY+ "\n");
					
				}// else if
				
				else
				{
					if(i == 0)
					{
						
						board[secondPossibleCeterA][secondPossibleCeterB] = fillColor;
						movingPy = secondPossibleCeterB;
						System.out.println("COLOR3 : " +firstPossibleCenterX );
						System.out.println("COLOR3: " + firstPossibleCenterY+ "\n");
					}
					else if (j == 0)
					{
						board[firstPossibleCenterX][firstPossibleCenterY] = fillColor;
						movingPx = firstPossibleCenterX;
						System.out.println("COLOR4 : " +firstPossibleCenterX );
						System.out.println("COLOR4: " + firstPossibleCenterY+ "\n");
					}
					else
					{
						
						movingPx = firstPossibleCenterX;
						movingPy = firstPossibleCenterY;
					}
					
					System.out.println("D5: " + first_Distance);
					System.out.println("D6: " + second_Distance);
					System.out.println("movingPx : " + firstPossibleCenterX );
					System.out.println("movingPy: " + firstPossibleCenterY+ "\n");
				}
				
			
				
			}// else statement
		}// while loop
		
	}// draw color method

	private static Vector<Integer> possibleVector(int sr, int sc, int possibleCenterX, int possibleCenterY) 
	{
		Vector<Integer> possibleVectorW = new Vector<Integer>();
		int w1 = possibleCenterX - sr;
		int w2 = possibleCenterY - sc;
		possibleVectorW.add(w1);
		possibleVectorW.add(w2);
		
		return possibleVectorW;
	}

	private static Vector<Integer> getUnitVector(int vectX, int vectY) 
	{
		Vector<Integer> unitVector = new Vector<Integer>();
		int i = 1 , j = 1;
		if(vectX == 0 || vectX < 0 )
		{
			if(vectX < 0 )
			{
				i = -1;
			}
			else
			{
				i = 0;
			}
		}// if statement
		if(vectY == 0 || vectY < 0 )
		{
			if(vectY < 0 )
			{
				j = -1;
			}
			else
			{
				j = 0;
			}
		}// if statement
		unitVector.add(i);
		unitVector.add(j);
		
		return unitVector;
		
		
	}// getUnitVector method
	
	private static void findWinner(char[][] board)
	{
		int player1_Score = 0;
		int player2_Score = 0;
		int size = board.length;
		
		for(int row = 0 ; row < size ; row++)
		{
			for(int col = 0 ; col < size ; col++)
			{
				if(board[row][col] == 'X')
				{
					player1_Score++;
				}
				else if(board[row][col] == 'O')
				{
					player2_Score++;
				}
					
			}// inner for loop
		}// for loop
		
		if(player1_Score > player2_Score)
		{
			System.out.println("\n\nPlayer 1 score is : " + player1_Score);
			System.out.println("Player 2 score is : " + player2_Score);
			System.out.println("Player 1 WINS!!!! " );
		}
		else if (player1_Score < player2_Score)
		{
			System.out.println("\n\nPlayer 1 score is : " + player1_Score);
			System.out.println("Player 2 score is : " + player2_Score);
			System.out.println("Player 2 WINS!!!! " );
		}
		else
		{
			System.out.println("\n\nPlayer 1 score is : " + player1_Score);
			System.out.println("Player 2 score is : " + player2_Score);
			System.out.println("This game is tie!!!! " );
		}
	}// findWinner method
}// second rough linear PA2

class Shell
{
	private int sr;
	private int sc;
	private int er;
	private int ec;
	
	public Shell(int sr, int sc, int er, int ec)
	{
		this.sr = sr;
		this.sc = sc;
		this.er = er;
		this.ec = ec;
		
	}// constructor
	
	public int getStartingRow()
	{
		return sr;
	}
	
	public int getStartingColumn()
	{
		return sc;
	}
	
	public int getEndingRow()
	{
		return er;
	}
	
	public int getEndingColumn()
	{
		return ec;
	}
	
	public float getmidPointX()
	{
		float midPointX = (float)(er+sr)/2;
		
		return midPointX;
		
	}
	public float getmidPointY()
	{
		int midPointY = (ec+sc)/2;
		
		return midPointY;
		
	}
	
	public Vector<Integer> getPastTurnsVector()
	{
		int vectorX = er - sr;
		int vectorY = ec - sc;
		
		// current turn's vector
		Vector<Integer> pastTurnsVector = new Vector<Integer>();
		pastTurnsVector.add(vectorX);
		pastTurnsVector.add(vectorY);
		
		return pastTurnsVector;
	}
	
	
}// end of class Shell