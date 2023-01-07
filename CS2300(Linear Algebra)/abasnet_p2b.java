import java.io.FileWriter;
import java.io.IOException;


public class abasnet_p2b 
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
		
		MultiplyMatrices matB = new MultiplyMatrices();
		matB.multiplyMatrices(mat1, mat2 , "abasnet_p2b_out12.txt");
		matB.multiplyDecMatrices(mat1, mat3 , "abasnet_p2b_out13.txt");
		matB.multiplyMatrices(mat1, mat4 , "abasnet_p2b_out14.txt");
		matB.multiplyMatrices(mat1, mat5 , "abasnet_p2b_out15.txt");
		matB.multiplyMatrices(mat2, mat1 , "abasnet_p2b_out21.txt");
		matB.multiplyDecMatrices(mat2, mat3 , "abasnet_p2b_out23.txt");
		matB.multiplyMatrices(mat2, mat4 , "abasnet_p2b_out24.txt");
		matB.multiplyMatrices(mat2, mat5, "abasnet_p2b_out25.txt");
		matB.multiplyDecMatrices1(mat3, mat1 , "abasnet_p2b_out31.txt");
		matB.multiplyDecMatrices1(mat3, mat2 , "abasnet_p2b_out32.txt");
		matB.multiplyDecMatrices1(mat3, mat4 , "abasnet_p2b_out34.txt");
		matB.multiplyDecMatrices1(mat3, mat5 , "abasnet_p2b_out35.txt");
		matB.multiplyMatrices(mat4, mat1 , "abasnet_p2b_out41.txt");
		matB.multiplyMatrices(mat4, mat2 , "abasnet_p2b_out42.txt");
		matB.multiplyDecMatrices(mat4, mat3 , "abasnet_p2b_out43.txt");
		matB.multiplyMatrices(mat4, mat5 , "abasnet_p2b_out45.txt");
		matB.multiplyMatrices(mat5, mat1 , "abasnet_p2b_out51.txt");
		matB.multiplyMatrices(mat5, mat2 , "abasnet_p2b_out52.txt");
		matB.multiplyDecMatrices(mat5, mat3 , "abasnet_p2b_out53.txt");
		matB.multiplyMatrices(mat5, mat4 , "abasnet_p2b_out54.txt");
		
	}// end main

}// end abasnet_p2b

class MultiplyMatrices
{
	private int[][] outputMatrices;
	private float[][] outputDecMatrices;
	public void multiplyMatrices(int[][] mat1, int[][]mat2 , String fileName) throws IOException
	{
		
		FileWriter output = new FileWriter(fileName);
		
		if(mat1[0].length != mat2.length)
		{
			output.write(" These two matrices cannot be added. ");
		}// if statement
		
		else
		{
			outputMatrices = new int[mat1.length][mat2[0].length];
			int sum = 0;
			
			for (int i = 0 ; i < mat1.length ; i++)
			{
				for (int j = 0 ; j < mat2[0].length ; j++)
				{
					for(int counter = 0 ; counter < mat2.length ; counter++)
					{
						sum = sum + (mat1[i][counter] * mat2[counter][j] );
					}// loop for counter to do multiply two matrices
					
					outputMatrices[i][j] = sum;
					output.write(outputMatrices[i][j] + " " );
					sum = 0;
					
				}// loop for mat2
				
				output.write("\n");
					
			}// for loop for mat1
		
		}// else
		
		output.close();
	}// end of addMatrices method
	
	
	public void multiplyDecMatrices(int[][] mat1, float[][]mat2 , String fileName) throws IOException
	{
		FileWriter output1 = new FileWriter(fileName);
		
		if(mat1[0].length != mat2.length)
		{
			output1.write(" These two matrices cannot be added. ");
		}// if statement
		
		else
		{
			outputDecMatrices = new float[mat1.length][mat2[0].length];
			float sum = 0.0f;
			
			for (int i = 0 ; i < mat1.length ; i++)
			{
				for (int j = 0 ; j < mat2[0].length ; j++)
				{
					for(int counter = 0 ; counter < mat2.length ; counter++)
					{
						sum = sum + (mat1[i][counter] * mat2[counter][j] );
					}// loop for counter to do multiply two matrices
					
					outputDecMatrices[i][j] = sum;
					output1.write(outputDecMatrices[i][j] + " " );
					sum = 0;
					
				}// loop for mat2
				
				output1.write("\n");
					
			}// for loop for mat1
		
		}// else
		output1.close();
	}
	
	public void multiplyDecMatrices1(float[][] mat1, int[][]mat2 , String fileName) throws IOException
	{
		FileWriter output2 = new FileWriter(fileName);
		
		if(mat1[0].length != mat2.length)
		{
			output2.write(" These two matrices cannot be added. ");
		}// if statement
		else
		{
			outputDecMatrices = new float[mat1.length][mat2[0].length];
			float sum = 0;
			
			for (int i = 0 ; i < mat1.length ; i++)
			{
				for (int j = 0 ; j < mat2[0].length ; j++)
				{
					for(int counter = 0 ; counter < mat2.length ; counter++)
					{
						sum = sum + mat1[i][counter] * mat2[counter][j] ;
						
					}// loop for counter to do multiply two matrices
					
					outputDecMatrices[i][j] = sum;
					output2.write(outputDecMatrices[i][j] + " " );
					sum = 0;
					
				}// loop for mat2
				
				output2.write("\n");
					
			}// for loop for mat1
		
		}// else
		
		output2.close();
	}
	
	
}// end of class

