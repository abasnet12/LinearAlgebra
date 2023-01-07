

import java.io.FileWriter;
import java.io.IOException;


public class abasnet_P1 
{

	public static void main(String[] args) throws IOException 
	{
		int numOfLetInFirstName = 5;
		int numOfLetInLastName = 6;
		
		Matrices p1_Mat1 = new Matrices(numOfLetInFirstName,numOfLetInLastName,1);
		p1_Mat1.incrementValues(1);
		p1_Mat1.writeTOFile("abasnet_p1_mat1.txt");
		
		
		Matrices p1_Mat2 = new Matrices(numOfLetInLastName,numOfLetInFirstName,2);
		p1_Mat2.incrementValues(3);
		p1_Mat2.writeTOFile("abasnet_p1_mat2.txt");
		
		Matrices p1_Mat3 = new Matrices(numOfLetInLastName , numOfLetInFirstName , 0.6f);
		p1_Mat3.incrementDecimalValues(0.2f);
		p1_Mat3.writeTOFileDecimal("abasnet_p1_mat3.txt");
		
		Matrices p1_Mat4 = new Matrices(5,9,3);
		p1_Mat4.incrementValuesColFirst(2);
		p1_Mat4.writeTOFile("abasnet_p1_mat4.txt");
		
		Matrices p1_Mat5 = new Matrices(5,9,-7);
		p1_Mat5.incrementValuesColFirst(1);
		p1_Mat5.writeTOFile("abasnet_p1_mat5.txt");
		
		
		
	}// end of main

} // end of class PA1

class Matrices
{
	private int [][] matrices;
	private float[][] decMatrices;
	private int numOfRow, numOfCol, startValue;
	float startDecimalValue;
	
	
	public Matrices(int numOfRow, int numOfCol, int startValue)
	{
		this.numOfRow = numOfRow;
		this.numOfCol = numOfCol;
		this.startValue = startValue;
		matrices = new int[numOfRow][numOfCol];
	}
	
	public Matrices(int numOfRow, int numOfCol, float startDecimalValue)
	{
		this.numOfRow = numOfRow;
		this.numOfCol = numOfCol;
		this.startDecimalValue = (float)startDecimalValue;
		decMatrices = new float[numOfRow][numOfCol];
	}
	
	public double startingDecIndex()
	{
		return startDecimalValue;
	}
	
	public int startingIndex()
	{
		return startValue;
	}
	
	
	
	public void  incrementDecimalValues(float incDecValue )
	{
		float previous = 0;
		
		for(int row = 0 ; row < numOfRow; row++)
		{
			for (int col = 0 ; col < numOfCol ; col++)
			{ 
				
				decMatrices[row][col] =(float)((previous + incDecValue));
				decMatrices[0][0] = (float) startingDecIndex();
				previous = decMatrices[row][col] ;
				
			}// col- for loop
			
		}// row- for loop
	}
	
	public void  incrementValues(int incValue )
	{
		int previous = 0;
		
		for(int row = 0 ; row < numOfRow; row++)
		{
			for (int col = 0 ; col < numOfCol ; col++)
			{ 
				
				matrices[row][col] = previous + incValue;
				matrices[0][0] = (int) startingIndex();
				previous = matrices[row][col] ;
				
			}// col- for loop
			
		}// row- for loop
	}
	
	public void  incrementValuesColFirst(int incValue )
	{
		int previous = 0;
		
		for(int col = 0 ; col < numOfCol; col++)
		{
			for (int row = 0 ; row < numOfRow ; row++)
			{ 
				
				matrices[row][col] = previous + incValue;
				matrices[0][0] = (int) startingIndex();
				previous = matrices[row][col] ;
				
			}// col- for loop
			
		}// row- for loop
	}
	
	 
	public void writeTOFile(String fileName) throws IOException
	{
		
		FileWriter mat1 = new FileWriter(fileName);
		for(int row = 0 ; row < numOfRow; row++)
		{
			for (int col = 0 ; col < numOfCol ; col++)
			{
				mat1.write(matrices[row][col] + " ");
				
			}// col- for loop
			mat1.write("\n");

		}// row- for loop
		
		mat1.close();
	}
	
	public void writeTOFileDecimal(String fileName) throws IOException
	{
		
		FileWriter mat1 = new FileWriter(fileName);
		
		for(int row = 0 ; row < numOfRow; row++)
		{
			for (int col = 0 ; col < numOfCol ; col++)
			{
				mat1.write((  decMatrices[row][col]) + " " );
				
			}// col- for loop
			mat1.write("\n");

		}// row- for loop
		
		mat1.close();
	}
}// end of class Matrices