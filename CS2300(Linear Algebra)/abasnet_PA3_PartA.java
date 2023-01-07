import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA3_PartA 
{
	static int outputFileNumber = 1;
	
	public static void main(String[] args) throws IOException
	{
		String firstInputFile = "pa3_input_1.txt";
		readFileMethod(firstInputFile);
		
		String secondInputFile = "pa3_input_2.txt";
		readFileMethod(secondInputFile);
		
		String thirdInputFile = "pa3_input_3.txt";
		readFileMethod(thirdInputFile);
		
		String fourthInputFile = "pa3_input_4.txt";
		readFileMethod(fourthInputFile);
		
		String fifthInputFile = "pa3_input_5.txt";
		readFileMethod(fifthInputFile);

	}// main

	private static void readFileMethod(String fileName) throws IOException
	{
		int row = 2 , col = 2;
		float[][] matrix_A = new float[row][col] ;
		Vector<Float> vector_B = new Vector<Float>();
		
		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);
		
		String output_FileName = " ";
		
		switch(outputFileNumber) 
		{
			case 1: output_FileName = "abasnet_output_A_1.txt";
				break;
		
			case 2: output_FileName = "abasnet_output_A_2.txt";
				break;
			
			case 3: output_FileName = "abasnet_output_A_3.txt";
				break;
			
			case 4: output_FileName = "abasnet_output_A_4.txt";
				break;	
				
			case 5: output_FileName = "abasnet_output_A_5.txt";
				break;
		}// end of switch
		
		// write output to the file
		FileWriter output = new FileWriter(output_FileName);
		
		// build A matrix and vector b from input file
		for ( int i = 0 ; i < row ; i++)
		{
			int a = 0 ; // counter to keep track of matrix and vector from input file
			
			for (int j = 0 ; j <= col ; j++)
			{
				// add matrix
				if (a <= 1)
				{
					matrix_A[i][j] = readFile.nextFloat();
				}// if
				
				// add vector
				else
				{
					vector_B.add(readFile.nextFloat());  
				}// else
				
				a++;
			}// column for loop
		}// row for loop
		
		float b1 = vector_B.get(0);
		float b2 = vector_B.get(1);
		
		// absolute value of a1,1 and a2,1
		float abs_a11 = Math.abs(matrix_A[0][0]);
		float abs_a21 = Math.abs(matrix_A[1][0]);
		
		// row pivot
		if(abs_a11 < abs_a21)
		{
			float  temp = matrix_A[0][0];
			matrix_A[0][0] = matrix_A[1][0];
			matrix_A[1][0] = temp;
			temp = matrix_A[0][1];
			matrix_A[0][1] = matrix_A[1][1];
			matrix_A[1][1] = temp;
			b1 = vector_B.get(1);
			b2 = vector_B.get(0);
		}
		
		// to find shear matrix to make upper triangle matrix	
		float d = -( 1 * matrix_A[1][0] ) / matrix_A[0][0] ;	// (d * a1,1 + 1 * a2,1) = 0 ;
		
		// sheared vector_B
		Vector<Float> sheared_Vector_B = new Vector<Float>();
		
		// after_Sheared_b1 = b1
		float after_Sheared_b1 = b1;
		
		// after_Sheared_b2 = d * b1 + 1 * b2
		float after_Sheared_b2 = d * b1 + 1 * b2;
		
		// add it to new sheared vector
		sheared_Vector_B.add(after_Sheared_b1);
		sheared_Vector_B.add(after_Sheared_b2);
		
		//float sheared_b1 = sheared_Vector_B.get(0);
		float sheared_b2 = sheared_Vector_B.get(1);
	
		// det of matrix A
		float det_Of_MatA = matrix_A[0][0] * matrix_A[1][1] - matrix_A[1][0] * matrix_A[0][1] ; 
		
		// if 0 = any real number except 0 in sheared vector x i.e. (X2)
		if (det_Of_MatA == 0.0f && sheared_b2 != 0.0f)
		{
			System.out.println("System is inconsistent. ");
			output.write("System is inconsistent.\n");
		}// if statement
		
		// if 0 = 0 for second row
		else if(det_Of_MatA == 0.0f && sheared_b2 == 0.0f)
		{
			System.out.println("System is underdetermined. ");
			output.write("System is underdetermined.\n");
		}// else if
		
		
		// if determinant of Matrix A is not zero
		else
		{
			Vector<BigDecimal> vector_X = new Vector<BigDecimal>();
			
			// x = [ Inverse A matrix ] [ b matrix]
			// Inverse A matrix * vector  b = (1/det) [ a2,2  -a1,2] [b1 b2]T
			//                            			  [-a2,1   a1,1]
			
			float x1 = ( matrix_A[1][1] * b1 + (-matrix_A[0][1] * b2 ) )/det_Of_MatA ;
			float x2 = ( (-matrix_A[1][0] * b1 ) + matrix_A[0][0] * b2  )/det_Of_MatA ;
			
			//round up to 4 decimal places
			BigDecimal roundup_x1 = new BigDecimal(x1).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_x2 = new BigDecimal(x2).setScale(4 , BigDecimal.ROUND_HALF_UP);

			//add to X vector
			vector_X.add(roundup_x1);
			vector_X.add(roundup_x2);
			
			System.out.println("System has unique solution: " + vector_X);
			output.write("System has unique solution: " + vector_X + "\n");

		}// else
		
		//  check for AX = 0 method
		homogeneous_System(output, matrix_A, det_Of_MatA);
		
		readFile.close();
		output.close();
		outputFileNumber++;
		
	}// readFileMethod

	private static void homogeneous_System(FileWriter output, float[][] matrix_A, float det_Of_MatA) throws IOException 
	{
		// Full rank matrix with only trivial solution
		if(det_Of_MatA != 0.0f)
		{
			output.write("Only trivial solution exists.\n");
		}// if
		
		// rank 1 matrix with non-trivial solution
		else
		{
			// after Gaussian elimination for rank 1 matrix
			float a = matrix_A[0][0];
			float b = matrix_A[0][1];
			
			// assume u2 = 1 to find u1
			float u2 = 1.0f ;
			
			// ( a11 * b1 + a12 * b2 ) = 0 
			float u1 = -(b * u2) / a ;
			
			// to find normalized u1 and u2
			float mag_Of_U1_U2 = (float) (Math.sqrt ( Math.pow(u1 ,2) + Math.pow(u2 ,2) ) );
			float normalized_u1 = u1 / mag_Of_U1_U2 ;
			float normalized_u2 = u1 / mag_Of_U1_U2 ;
			
			// Round up to 4 decimal places
			BigDecimal roundup_normalized_u1 = new BigDecimal(normalized_u1).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_normalized_u2 = new BigDecimal(normalized_u2).setScale(4 , BigDecimal.ROUND_HALF_UP);
			
			// adding it to X vector
			Vector<BigDecimal> homogeneous_Xvect = new Vector<BigDecimal>();
			homogeneous_Xvect.add(roundup_normalized_u1);
			homogeneous_Xvect.add(roundup_normalized_u2);
			
			output.write("Non-trivial solution is: " + homogeneous_Xvect);
			
		}// else
		
	}// homogeneous system method

}// abasnet_PA3_PartA



	