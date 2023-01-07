import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class abasnet_P2a 
{

	public static void main(String[] args) throws IOException
	{
		int numOfLetInFirstName = 5;
		int numOfLetInLastName = 6;
		
		int[][] mat1 = new int[numOfLetInFirstName][numOfLetInLastName];
		int[][] mat2 = new int[numOfLetInLastName][numOfLetInFirstName];
		float[][] mat3 = new float[numOfLetInLastName][numOfLetInFirstName];
		int[][] mat4 = new int[5][9];
		int[][] mat5 = new int[5][9];
		
		BuildIndividualMatrices matA = new BuildIndividualMatrices();
		
		matA.buildIndividualMatrices(mat1 , "abasnet_p1_mat1.txt");
		matA.buildIndividualMatrices(mat2 , "abasnet_p1_mat2.txt");
		matA.buildIndividualDecMatrices(mat3 , "abasnet_p1_mat3.txt");
		matA.buildIndividualMatrices(mat4 , "abasnet_p1_mat4.txt");
		matA.buildIndividualMatrices(mat5 , "abasnet_p1_mat5.txt");
		
		AddingMatrices matB = new AddingMatrices();
		matB.addMatrices(mat1, mat2 , "abasnet_p2a_out12.txt");
		matB.addDecimalMatrices(mat1, mat3 , "abasnet_p2a_out13.txt");
		matB.addMatrices(mat1, mat4 , "abasnet_p2a_out14.txt");
		matB.addMatrices(mat1, mat5 , "abasnet_p2a_out15.txt");
		matB.addDecimalMatrices(mat2, mat3 , "abasnet_p2a_out23.txt");
		matB.addMatrices(mat2, mat4 , "abasnet_p2a_out24.txt");
		matB.addMatrices(mat2, mat5 , "abasnet_p2a_out25.txt");
		matB.addDecimalMatrices(mat4, mat3 , "abasnet_p2a_out43.txt");
		matB.addDecimalMatrices(mat5, mat3 , "abasnet_p2a_out53.txt");
		matB.addMatrices(mat4, mat5 , "abasnet_p2a_out45.txt");
		
		
		
	
		
	}// end of main

}// end of class abasnet_p2a

class AddingMatrices
{
	private int[][] addedMatrices;
	private float[][] addedDecimalMatrices;
	
	public void addMatrices(int[][] mat1, int[][]mat2 , String fileName) throws IOException
	{
		FileWriter output = new FileWriter(fileName);
		
		
		if(mat1.length != mat2.length)
		{
			output.write(" These two matrices cannot be added because row doesnt match. ");
		}
		else if((mat1[0].length != mat2[0].length))
		{
			output.write(" These two matrices cannot be add because column doesnt match. ");
		}	
		else
		{
			addedMatrices = new int[mat1.length][mat1[0].length];
			for(int i = 0 ; i < mat1.length ; i++)
			{
				for(int j = 0 ; j < mat1[0].length ; j++)
				{
					
					addedMatrices[i][j] = mat1[i][j] + mat2[i][j];
				}
			}
			for(int row = 0 ; row < addedMatrices.length ; row++)
			{
				for (int col = 0 ; col < addedMatrices[row].length ; col++)
				{
					output.write((  addedMatrices[row][col]) + " " );
					
				}// col- for loop
				output.write("\n");

			}// row- for loop
		}
		
		output.close();
	}
	
	public void addDecimalMatrices(int[][] mat1, float[][]mat2 , String fileName) throws IOException
	{
		FileWriter output = new FileWriter(fileName);
		
		
		if(mat1.length != mat2.length)
		{
			output.write(" These two matrices cannot be added. ");
		}
		else if((mat1[0].length != mat2[0].length))
		{
			output.write(" These two matrices cannot be added because column dont match. ");
		}	
		else
		{
			addedDecimalMatrices = new float[mat1.length][mat1[0].length];
			for(int i = 0 ; i < mat1.length ; i++)
			{
				for(int j = 0 ; j < mat1[0].length ; j++)
				{
					addedDecimalMatrices[i][j] = mat1[i][j] + mat2[i][j];
				}
			}
			for(int row = 0 ; row < addedDecimalMatrices.length ; row++)
			{
				for (int col = 0 ; col < addedDecimalMatrices[row].length ; col++)
				{
					output.write((  addedDecimalMatrices[row][col]) + " " );
					
				}// col- for loop
				output.write("\n");

			}// row- for loop
			
		}
		
		
		
		output.close();
	}
	
}

class BuildIndividualMatrices
{
	
	private Scanner readFile;

	public void buildIndividualMatrices(int[][] matrices , String fileName) throws IOException
	{
		File inputFileName = new File(fileName);
		readFile = new Scanner(inputFileName);
		
		for(int i = 0 ; i < matrices.length; i ++)
		{
			for (int j = 0 ; j < matrices[i].length ; j ++)
			{
				matrices[i][j] = readFile.nextInt();
				
			}// inner for loop
		}// end of outer for loop
				
	}// end of method
	
	public void buildIndividualDecMatrices(float[][] matrices , String fileName) throws IOException
	{
		File inputFileName = new File(fileName);
		readFile = new Scanner(inputFileName);
		
		for(int i = 0 ; i < matrices.length; i ++)
		{
			for (int j = 0 ; j < matrices[i].length ; j ++)
			{
				matrices[i][j] = readFile.nextFloat();
				
			}// inner for loop
		}// end of outer for loop
	}// end of method
	
	
}// end of class



 