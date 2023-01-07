import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA4_PartA 
{
	static int outputFileNumber = 1;
	public static void main(String[] args) throws IOException 
	{
		
		String input_FileName1 = "abasnet_PA4_input_1.txt";
		writetoFile(input_FileName1 );
		readTheFile(input_FileName1 );
		
		String input_FileName2 = "abasnet_PA4_input_2.txt";
		writetoFile(input_FileName2 );
		readTheFile(input_FileName2 );
		
		String input_FileName3 = "abasnet_PA4_input_3.txt";
		writetoFile(input_FileName3 );
		readTheFile(input_FileName3 );
		
		String input_FileName4 = "input_1.txt";
		readTheFile(input_FileName4 );
		
		String input_FileName5 = "input_2.txt";
		readTheFile(input_FileName5 );
		
		String input_FileName6 = "input_3.txt";
		readTheFile(input_FileName6 );
		
		String input_FileName7 = "input_4.txt";
		readTheFile(input_FileName7 );
		
		String input_FileName8 = "input_5.txt";
		readTheFile(input_FileName8 );
		
	}// end of main

	// return random coordinate 
	public static int random_Coordinate()
	{
		 Random randomNum = new Random();
			
		 // random numbers from (0-9)
		 int randomInt = randomNum.nextInt(10)  ;
			
		 return randomInt;
	}// random_Coordinate
	
	//write to the input file
	private static void writetoFile(String input_FileName ) throws IOException 
	{
		Random randomNum = new Random();
		int inputFile_ColSize = 9 ;
		int numOfLines_InputFile = 10;
		int counter = 1 ;
		
		FileWriter inputFile = new FileWriter(input_FileName);
		int rand_Num;
		while ( counter < numOfLines_InputFile)
		{
			int i = 0;
			
			while( i < inputFile_ColSize)
			{
				rand_Num = random_Coordinate();
				inputFile.write(rand_Num + " ");
				i++;
			}// inner while loop
			inputFile.write("\n");
			counter++;
		}// while loop
		int dimension = randomNum.nextInt(4) ;
		inputFile.write(dimension + "\n");
		inputFile.close();
	}// writetoFile method
	
	// read from the file method
	private static void readTheFile(String fileName) throws IOException 
	{
		
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String line , lastLine = "";

		// array list to store coordinates
		ArrayList<Float> array_List = new ArrayList<>();
		
		// read the last line to find dimension
		while ( (line = input.readLine()) != null)
		{
			lastLine = line;
		}// while loop
		
		float num = Float.parseFloat(lastLine);

		int dimension = (int) num;
		
		readPoint_ForDiffDimensions(fileName , array_List ,dimension);
		coordinatesOf_ThreePoints( array_List , dimension);
		input.close();

	}// readTheFile method
	
	// read points from file for different dimensions
	private static void readPoint_ForDiffDimensions(String fileName , ArrayList<Float> array_List  ,int dimension) throws IOException 
	{
		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName); 
			
		int counter = 0;
		int numOfLines = 0;

		// read the points form file
		while (numOfLines < 3 )
		{
			while ( counter < dimension)
			{
				array_List.add(readFile.nextFloat());
				counter++;
			}// inner while loop
				
			// skip rest of the number and jump to next line
			String line = readFile.nextLine();
			counter = 0;
			numOfLines++;
				
		}// while loop
		
		readFile.close();
			
	}// readPoint_ForDiffDimensions method
		
	// assign coordinates to each points
	private static void coordinatesOf_ThreePoints(ArrayList<Float> array_List, int dimension) throws IOException 
	{
		
		int indexOf_ArrayList = 0;
		Vector<Float> firstPoint_P = new Vector<>();
		Vector<Float> secondPoint_Q = new Vector<>() ;
		Vector<Float> thirdPoint_R = new Vector<>();
		
		// first point
		for ( int i = 0 ; i < dimension ; i++)
		{
			firstPoint_P.add(array_List.get(indexOf_ArrayList)) ;
			indexOf_ArrayList++;
		}// for loop
		
		// second point
		for ( int i = 0 ; i < dimension ; i++)
		{
			secondPoint_Q.add(array_List.get(indexOf_ArrayList)) ;
			indexOf_ArrayList++;
		}// for loop
		
		// second point
		for ( int i = 0 ; i < dimension ; i++)
		{
			thirdPoint_R.add(array_List.get(indexOf_ArrayList)) ;
			indexOf_ArrayList++;
		}// for loop
		
		String output_FileName = " ";
		
		switch(outputFileNumber) 
		{
			case 1: output_FileName = "abasnet_PA4_output_A_1.txt";
				break;
		
			case 2: output_FileName = "abasnet_PA4_output_A_2.txt";
				break;
			
			case 3: output_FileName = "abasnet_PA4_output_A_3.txt";
				break;
			
			case 4: output_FileName = "abasnet_PA4_output_A_4.txt";
				break;	
				
			case 5: output_FileName = "abasnet_PA4_output_A_5.txt";
				break;
				
			case 6: output_FileName = "abasnet_PA4_output_A_6.txt";
				break;
		
			case 7: output_FileName = "abasnet_PA4_output_A_7.txt";
				break;	
			
			case 8: output_FileName = "abasnet_PA4_output_A_8.txt";
				break;
				
		}// end of switch
		
		FileWriter outputFile = new FileWriter(output_FileName);		
		float area = 0.0f;
		float distance = 0.0f ;
		
		//2D dimension or 3D
		if(dimension == 2 || dimension == 3)
		{
			//2D dimension
			if(dimension == 2)
			{
				area = findTheArea_2D(firstPoint_P , secondPoint_Q , thirdPoint_R);	
				distance = findDistance_On2DLine(firstPoint_P , secondPoint_Q , thirdPoint_R);
			}// inner if
			
			// 3D dimension
			else
			{
				area = findTheArea_3D(firstPoint_P , secondPoint_Q , thirdPoint_R);
				distance = findDistance_On3DLine(firstPoint_P , secondPoint_Q , thirdPoint_R);
			}// inner else
			
			BigDecimal roundup_area = new BigDecimal(area).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_distance = new BigDecimal(distance).setScale(4 , BigDecimal.ROUND_HALF_UP);
			outputFile.write(roundup_area + "\n");
			outputFile.write(roundup_distance + "\n");
			
		}// if 

		// dimension is not 2D or 3D
		else
		{
			outputFile.write("Invalid X\n");
		}// else
		
		outputFile.close();
		outputFileNumber++;
	}// coordinatesOf_ThreePoints method
	
	// calculate the distance from third point to the plane (bisector of two points)
	private static float findDistance_On3DLine(Vector<Float> firstPoint_P, Vector<Float> secondPoint_Q,
			Vector<Float> thirdPoint_R) 
	{
		float p1 = firstPoint_P.get(0);
		float p2 = firstPoint_P.get(1);
		float p3 = firstPoint_P.get(2);
		float q1 = secondPoint_Q.get(0);
		float q2 = secondPoint_Q.get(1);
		float q3 = secondPoint_Q.get(2);
		float r1 = thirdPoint_R.get(0);
		float r2 = thirdPoint_R.get(1);
		float r3 = thirdPoint_R.get(2);
		
		// bisector of first two points
		float s1 = (q1 - p1) / 2;
		float s2 = (q2 - p2) / 2;
		float s3 = (q3 - p3) / 2;
		
		// normal vector components to the plane
		float normal1 = q1 - s1 ;
		float normal2 = q2 - s2 ;
		float normal3 = q3 - s3 ;
		
		float mag_NormalVector = (float) Math.sqrt ( (Math.pow(normal1 , 2)) + (Math.pow(normal2, 2)) + (Math.pow(normal3, 2)));
		
		//normalized normal vector components
		float normalized_normal1 = normal1 / mag_NormalVector ;
		float normalized_normal2 = normal2 / mag_NormalVector ;
		float normalized_normal3 = normal3 / mag_NormalVector ;
		
		// vector w component
		// w = bisectorPoint_S - thirdPoint_R
		float w1 = s1 - r1 ;
		float w2 = s2 - r2 ;
		float w3 = s3 - r3 ;
		
		// Distance from third point to the plane
		// D = w * nomalized_normal (dot product)
		float distance = ( w1 * normalized_normal1 ) + ( w2 * normalized_normal2 ) + ( w3 * normalized_normal3 );
		return distance;
	}// findDistance_On3DLine method

	//calculate the distance from third point to the line (2D)
	private static float findDistance_On2DLine(Vector<Float> firstPoint_P, Vector<Float> secondPoint_Q,
			Vector<Float> thirdPoint_R) 
	{
		float p1 = firstPoint_P.get(0);
		float p2 = firstPoint_P.get(1);
		float q1 = secondPoint_Q.get(0);
		float q2 = secondPoint_Q.get(1);
		float r1 = thirdPoint_R.get(0);
		float r2 = thirdPoint_R.get(1);
		
		// vector W = r - p
		Vector<Float> vector_W = new Vector<Float>();
		vector_W.add( r1 - p1 );
		vector_W.add( r2 - p2);
		
		// vector V = q - p
		Vector<Float> vector_V = new Vector<Float>();
		vector_V.add( q1 - p1 );
		vector_V.add( q2 - p2);
		
		float w1 = vector_W.get(0);
		float w2 = vector_W.get(1);
		
		float v1 = vector_V.get(0);
		float v2 = vector_V.get(1);
		
		// dot product
		float dotProd = ( w1 * v1 ) + ( w2 * v2);
		float magOfV_squared = (float)( (Math.pow(v1, 2)) + (Math.pow(v2, 2)) ) ;
		
		// To find foot on the line
		float valueOf_T = dotProd / magOfV_squared ;
		
		//point on a line closes to third point r
		// s = p + t*v
		float s1 = p1 + (valueOf_T * v1 ) ;
		float s2 = p2 + (valueOf_T * v2 ) ;
		
		float diff_X = s1 - r1 ;
		float diff_Y = s2 - r2 ;
		float distance = (float) ( Math.sqrt ( (Math.pow(diff_X, 2)) + (Math.pow(diff_Y, 2))) );
		
		return distance;
		
	}// findDistance_On2DLine method

	// calculate area of triangle in 2-D
	private static float findTheArea_3D(Vector<Float> firstPoint_P, Vector<Float> secondPoint_Q,
			Vector<Float> thirdPoint_R) 
	{
		Vector<Float> vector_v = new Vector<Float>();
		vector_v.add( secondPoint_Q.get(0) - firstPoint_P.get(0) ) ;
		vector_v.add( secondPoint_Q.get(1) - firstPoint_P.get(1) ) ;
		vector_v.add( secondPoint_Q.get(2) - firstPoint_P.get(2) ) ;
		
		Vector<Float> vector_w = new Vector<Float>();
		vector_w.add( thirdPoint_R.get(0) - firstPoint_P.get(0) ) ;
		vector_w.add( thirdPoint_R.get(1) - firstPoint_P.get(1) ) ;
		vector_w.add( thirdPoint_R.get(2) - firstPoint_P.get(2) ) ;
		
		
		//vector_v components
		float v1 = vector_v.get(0);
		float v2 = vector_v.get(1);
		float v3 = vector_v.get(2);
		
		// vector_w components
		float w1 = vector_w.get(0);
		float w2 = vector_w.get(1);
		float w3 = vector_w.get(2);
		
		
		// v ^ w
		float first_det = ( v2 * w3 ) - ( w2 * v3);
		float second_det = ( v3 * w1 ) - ( v1 * w3);// negative
		float third_det = ( v1 * w2 ) - ( w1 * v2);
		
		//  || v ^ w ||
		float mag_of_CrossProd = (float) Math.sqrt( (Math.pow(first_det, 2)) + ( Math.pow(second_det, 2) ) 
				+  ( Math.pow(third_det, 2) ) );
		
		// area of triangle = || v ^ w || /2
		float area = mag_of_CrossProd / 2;
		return area;
		
	}// findTheArea_3D

	// calculate area of triangle in 2-D
	private static float findTheArea_2D(Vector<Float> firstPoint_P, Vector<Float> secondPoint_Q,
			Vector<Float> thirdPoint_R) 
	{
		// first point vector_P
		// second point vector_Q
		// third point vector_R
				
		float a11 = secondPoint_Q.get(0) - firstPoint_P.get(0);
		float a21 = thirdPoint_R.get(0) - firstPoint_P.get(0);
		float a12 = secondPoint_Q.get(1) - firstPoint_P.get(1);
		float a22 = thirdPoint_R.get(1) - firstPoint_P.get(1);
		
		//AREA OF TRIANGLE = (a11*a22 - a12*a21)/2
		float areaOfTriangle = (a11*a22 - a12*a21)/2 ;
		
		return areaOfTriangle;
	}// Area of 2D method

	
	
}// PA4
