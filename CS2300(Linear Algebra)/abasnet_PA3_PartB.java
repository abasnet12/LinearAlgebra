import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA3_PartB 
{
	static int outputFileNumber = 1;
	
	public static void main(String[] args) throws IOException 
	{
		String firstInputFile = "pa3_input_1.txt";
		readOnlyMatrix(firstInputFile);
		
		String secondInputFile = "pa3_input_2.txt";
		readOnlyMatrix(secondInputFile);
		
		String thirdInputFile = "pa3_input_3.txt";
		readOnlyMatrix(thirdInputFile);
		
		String fourthInputFile = "pa3_input_4.txt";
		readOnlyMatrix(fourthInputFile);
		
		String fifthInputFile = "pa3_input_5.txt";
		readOnlyMatrix(fifthInputFile);

	}// end of main

	private static void readOnlyMatrix(String fileName) throws IOException 
	{
		int size = 3 ;
		
		float[][] matrix_A = new float[size][size] ;
		
		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);
		
		// build A matrix from input file
		for ( int i = 0 ; i < size ; i++)
		{
			int a = 0 ; 
			// counter to keep track of matrix column and disregard vector column in the file
					
			for (int j = 0 ; j <= size ; j++)
			{
				// add matrix
				if (a <= 1)
				{
					matrix_A[i][j] = readFile.nextFloat();
				}// if
						
				a++;
			}// column for loop
		}// row for loop
		
		String output_FileName = " ";
		
		switch(outputFileNumber) 
		{
			case 1: output_FileName = "abasnet_output_B_1.txt";
				break;
		
			case 2: output_FileName = "abasnet_output_B_2.txt";
				break;
			
			case 3: output_FileName = "abasnet_output_B_3.txt";
				break;
			
			case 4: output_FileName = "abasnet_output_B_4.txt";
				break;	
				
			case 5: output_FileName = "abasnet_output_B_5.txt";
				break;
		}// end of switch
		
		
		//coefficients and variable of quadratic equation
		float lambda_1 = 0.0f, lambda_2 = 0.0f, a = 0.0f , b = 0.0f , c = 0.0f;
		
		// determinant of (matrix A - lambda * Identity matrix ) gives quadratic equation
		// a* lambda^2 + b * lambda + c
		
		// coefficient of quadratic equation
		a = 1.0f ;
		b = - (matrix_A[0][0] + matrix_A[1][1]);
		c = ( matrix_A[0][0] *  matrix_A[1][1] -  matrix_A[0][1] *  matrix_A[1][0] );
		
		// quadratic equation
		lambda_1 = (float) ((-b + (Math.sqrt( ( Math.pow(b, 2) - 4 * a * c) ) ) )/ ( 2 * a )) ;
		lambda_2 = (float) ((-b - (Math.sqrt( ( Math.pow(b, 2) - 4 * a * c) ) ) )/ ( 2 * a )) ;
		
		// absolute value of lamba1 and lambda2
		float abs_Value_Lambda1 = Math.abs(lambda_1) ;
		float abs_Value_Lambda2 = Math.abs(lambda_2) ;
		
		lambda_1 = abs_Value_Lambda1;
		lambda_2 = abs_Value_Lambda2;
		
		if(lambda_1 < lambda_2)
		{
			//swap
			float temp = lambda_1 ;
			lambda_1 = lambda_2;
			lambda_2 = temp;
			
		}// END if
		
		// write output to the file
		FileWriter output = new FileWriter(output_FileName);
		
		// NO REAL EIGEN VALUES
		if( ( Float.isNaN(lambda_1) ) || ( Float.isNaN(lambda_2) ))
		{
			output.write("No real eigenvalues.");
		}// if
		
		// IF MATRIX HAVE REAL EIGEN VALUES
		else
		{
			BigDecimal roundup_lambda_1 = new BigDecimal(lambda_1).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_lambda_2 = new BigDecimal(lambda_2).setScale(4 , BigDecimal.ROUND_HALF_UP);
			output.write(roundup_lambda_1 + "	0 \n");
			output.write("0	" + roundup_lambda_2 + "\n");
			
			
			// R matrix
			Vector<Float> r1 = findVector(matrix_A , lambda_1 , size) ;
			Vector<Float> r2 = findVector(matrix_A , lambda_2 , size) ;
			
			buildMatrix(matrix_A , r1 , r2 , lambda_1 , lambda_2 , output , size );

		}// else

		output.close();
		readFile.close();
		outputFileNumber++ ;
		
	}// readOnlyMatrix method
	
	// RETURN R VECTOR
	private static Vector<Float> findVector(float[][] matrix_A, float lambda , int size) 
	{
		// vector r1 = [r1,1 r2,1]T
		float r11 = 0.0f;
		float r21 = 0.0f;
				
				// [ A_matrix - (lamda1 * Identity Matrix) ]
				float[][] A_lambda_Matrix = new float[size][size];
				A_lambda_Matrix[0][0] = matrix_A[0][0] - lambda ;
				A_lambda_Matrix[1][0] = matrix_A[1][0] ;
				A_lambda_Matrix[0][1] = matrix_A[0][1] ;
				A_lambda_Matrix[1][1] = matrix_A[1][1] - lambda ;
				
				// absolute value of a11 , a21 , a12 , and a22
				float abs_a11 = Math.abs(A_lambda_Matrix[0][0]);
				float abs_a21 = Math.abs(A_lambda_Matrix[1][0]);
				float abs_a12 = Math.abs(A_lambda_Matrix[0][1]);
				float abs_a22 = Math.abs(A_lambda_Matrix[1][1]);
				
				////no pivoting required
				if(abs_a11 >= abs_a21 && abs_a11 >= abs_a12 &&  abs_a11 >= abs_a22)
				{
					// find r11 & r21
					if( A_lambda_Matrix[0][1] == 0)
					{
						r11 = 0.0f;
						r21 = 1.0f;
					}
					else
					{
						r11 = 1.0f;
						r21 = - (A_lambda_Matrix[0][0] * r11) / A_lambda_Matrix[0][1];
					}
				}
				
				//row pivoting if |a21| >= |a12| && |a21 | > rest of two matrices
				else if( abs_a21 > abs_a11 && abs_a21 >= abs_a12 &&  abs_a21 > abs_a22)
				{
					// swap
					float temp = A_lambda_Matrix[0][0];
					A_lambda_Matrix[0][0] = A_lambda_Matrix[1][0];
					A_lambda_Matrix[1][0] =temp;
					temp = A_lambda_Matrix[0][1];
					A_lambda_Matrix[0][1] = A_lambda_Matrix[1][1] ;
					A_lambda_Matrix[1][1] = temp;
					
					// find r11 & r21
					if( A_lambda_Matrix[0][1] == 0)
					{
						r11 = 0.0f;
						r21 = 1.0f;
					}// if
					else
					{
						r11 = 1.0f;
						r21 = - (A_lambda_Matrix[0][0] * r11) / A_lambda_Matrix[0][1];
					}// else
					
				}// end of row pivot
				
				//column pivoting if |a12| >= |a22| && |a12 | > rest of two matrices
				else if( abs_a12 > abs_a11 && abs_a12 > abs_a12 &&  abs_a12 >= abs_a22)
				{
					// swap
					float temp = A_lambda_Matrix[0][0];
					A_lambda_Matrix[0][0] = A_lambda_Matrix[0][1];
					A_lambda_Matrix[0][1] = temp;
					temp = A_lambda_Matrix[1][0];
					A_lambda_Matrix[1][0] = A_lambda_Matrix[1][1];
					A_lambda_Matrix[1][1] = temp;
					
					if( A_lambda_Matrix[0][1] == 0)
					{
						r21 = 0.0f ;
						r11 = 1.0f ;
					}// if
					
					else
					{
						r11 = 1.0f ;
						r21 = - ( A_lambda_Matrix[0][0] * r11)/ A_lambda_Matrix[0][1];
					}// else
				}// end of column pivoting
				
				// 1st column pivoting and row pivoting if |a22| > rest of 3 matrices in 2x2 matrix
				else
				{
					// swap
					float temp = A_lambda_Matrix[0][0];
					A_lambda_Matrix[0][0] = A_lambda_Matrix[1][1];
					A_lambda_Matrix[1][1] = temp ;
					temp = A_lambda_Matrix[1][0];
					A_lambda_Matrix[1][0] = A_lambda_Matrix[0][1] ;
					A_lambda_Matrix[0][1] = temp;
					
					if( A_lambda_Matrix[0][1] == 0)
					{
						r21 = 0.0f ;
						r11 = 1.0f ;
					}// if
					
					else
					{
						r11 = 1.0f ;
						r21 = - ( A_lambda_Matrix[0][0] * r11)/ A_lambda_Matrix[0][1];
					}// else
				}// end of else
				
		Vector<Float> r = new Vector<Float>();

		r.add(r11);
		r.add(r21);
		
		return r;
	}// findVector method
	
	//BUILD R , LAMBDA , R_TRANSPOSE MATRIX
	private static void buildMatrix(float[][] matrix_A , Vector<Float> r1, Vector<Float> r2, float lambda_1 , float lambda_2 , FileWriter output , int size) throws IOException 
	{
		float r11 = r1.get(0);
		float r21 = r1.get(1);
		float r12 = r2.get(0);
		float r22 = r2.get(1);
		
		BigDecimal roundup_r11 = new BigDecimal(r11).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_r21 = new BigDecimal(r21).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_r12 = new BigDecimal(r12).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_r22 = new BigDecimal(r22).setScale(4 , BigDecimal.ROUND_HALF_UP);
		
		output.write(roundup_r11 + "	" + roundup_r12 + "\n");
		output.write(roundup_r21 + "	" + roundup_r22 + "\n");
		
		// R matrix
		float[][] r_Matrix = new float[size][size];
		r_Matrix[0][0] = r11;
		r_Matrix[0][1] = r12;
		r_Matrix[1][0] = r21;
		r_Matrix[1][1] = r22;

		// transpose  R matrix
		float[][] r_transpose = new float[size][size];
		r_transpose[0][0] = r11 ;
		r_transpose[0][1] = r21 ;
		r_transpose[1][0] = r12 ;
		r_transpose[1][1] = r22 ;
		
		float[][] lambda_Identity_mat = new float[size][size];
		lambda_Identity_mat[0][0] = lambda_1;
		lambda_Identity_mat[0][1] = 0;
		lambda_Identity_mat[1][0] = 0;
		lambda_Identity_mat[1][1] = lambda_2;
		
		find_R_Lamba_Rtranspose(matrix_A , r_Matrix , r_transpose , lambda_Identity_mat , size , output);

	}// buildMatrix method

	// FIND R , LAMBDA , R_TRANSPOSE MATRIX
	private static void find_R_Lamba_Rtranspose(float[][] matrix_A , float[][] r_Matrix, float[][] r_transpose,
			float[][] lambda_Identity_mat , int size , FileWriter output) throws IOException 
	{
		// build lambda_Identity_matrix * R_transpose matrix
		float[][] lambda_Rtranspose = new float[size][size];
		lambda_Rtranspose[0][0] = ( lambda_Identity_mat[0][0] * r_transpose[0][0] ) +  ( lambda_Identity_mat[0][1] * r_transpose[1][0] ) ;
		lambda_Rtranspose[0][1] = ( lambda_Identity_mat[0][0] * r_transpose[0][1] ) +  ( lambda_Identity_mat[0][1] * r_transpose[1][1] ) ;
		lambda_Rtranspose[1][0] = ( lambda_Identity_mat[1][0] * r_transpose[0][0] ) +  ( lambda_Identity_mat[1][1] * r_transpose[1][0] ) ;
		lambda_Rtranspose[1][1] = ( lambda_Identity_mat[1][0] * r_transpose[0][1] ) +  ( lambda_Identity_mat[1][1] * r_transpose[1][1] ) ;
		
		// build R * Lambda * r_transpose matrix
		float[][] r_lambda_Rtranspose = new float[size][size];
		r_lambda_Rtranspose[0][0] = ( r_Matrix[0][0] * lambda_Rtranspose[0][0]) + ( r_Matrix[0][1] * lambda_Rtranspose[1][0]) ;
		r_lambda_Rtranspose[0][1] = ( r_Matrix[0][0] * lambda_Rtranspose[0][1]) + ( r_Matrix[0][1] * lambda_Rtranspose[1][1]) ;
		r_lambda_Rtranspose[1][0] = ( r_Matrix[1][0] * lambda_Rtranspose[0][0]) + ( r_Matrix[1][1] * lambda_Rtranspose[1][0]) ;
		r_lambda_Rtranspose[1][1] = ( r_Matrix[1][0] * lambda_Rtranspose[0][1]) + ( r_Matrix[1][1] * lambda_Rtranspose[1][1]) ;
		
		float r_lambda_Rt11 = r_lambda_Rtranspose[0][0] ;
		float r_lambda_Rt12 = r_lambda_Rtranspose[0][1] ;
		float r_lambda_Rt21 = r_lambda_Rtranspose[1][0] ;
		float r_lambda_Rt22 = r_lambda_Rtranspose[1][1] ;
		
		BigDecimal roundup_r_lambda_Rt11 = new BigDecimal(r_lambda_Rt11).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_r_lambda_Rt12 = new BigDecimal(r_lambda_Rt12).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_r_lambda_Rt21 = new BigDecimal(r_lambda_Rt21).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_r_lambda_Rt22 = new BigDecimal(r_lambda_Rt22).setScale(4 , BigDecimal.ROUND_HALF_UP);
		
		output.write(roundup_r_lambda_Rt11 + "	" + roundup_r_lambda_Rt12 + "\n");
		output.write(roundup_r_lambda_Rt21 + "	" + roundup_r_lambda_Rt22 + "\n");
		
		boolean isMatrixEqual = find_If_Equal(matrix_A ,r_lambda_Rtranspose);
		
		if(isMatrixEqual == true)
		{
			output.write("1\n");
		}
		else
		{
			output.write("0\n");
		}
	}// find_R_Lamba_Rtranspose method

	// IF MATRIX A = R * LAMBDA * R_TRANSPOSE MATRIX
	private static boolean find_If_Equal(float[][] matrix_A , float[][] r_lambda_Rtranspose) 
	{
		boolean isEqual = false;
		
		//declariNg variables for eaCh matrices in 2X2 matrix
		
		float a11 = matrix_A[0][0];
		float a12 = matrix_A[0][1];
		float a21 = matrix_A[1][0];
		float a22 = matrix_A[1][1];
		
		float rLambda11 =  r_lambda_Rtranspose[0][0];
		float rLambda12 =  r_lambda_Rtranspose[0][1];
		float rLambda21 =  r_lambda_Rtranspose[1][0];
		float rLambda22 =  r_lambda_Rtranspose[1][1];
		
		if( (a11 == rLambda11) && (a12 == rLambda12) && (a21 == rLambda21) && ( a22 == rLambda22) )
		{
			isEqual = true;
		}
		else
		{
			isEqual = false;
		}
	
		return isEqual;
	} // find_If_Equal method

}// abasnet_PA3_PartB
