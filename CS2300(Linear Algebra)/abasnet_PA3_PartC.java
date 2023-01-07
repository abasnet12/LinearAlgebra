import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class abasnet_PA3_PartC 
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

	}// END OF MAIN

	private static void readFileMethod(String fileName) throws IOException 
	{
		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);
		
		float x1 = readFile.nextFloat();
		float y1 = readFile.nextFloat();
		float z1 = readFile.nextFloat();
		float x2 = readFile.nextFloat();
		float y2 = readFile.nextFloat();
		float z2 = readFile.nextFloat();
		
		// first point (x1, x2)
		// second point (y1 , y2)
		// third point (z1 , z2)
		
		// starting point is ( x1 , x2 )
		// vector a1 ( starting point to second point ) [ a11 a21]T
		// vector a2 ( starting point to third point ) [ a22  a12]T
		
		float a11 = y1 - x1 ;
		float a22 = z2 - x2 ;
		float a12 = z1 - x1 ;
		float a21 = y2 - x2 ;
		
		//AREA OF TRIANGLE = (a11*a22 - a12*a21)/2
		float areaOfTriangle = (a11*a22 - a12*a21)/2 ;
		
		//ROUND UP TO 4 DECIMAL PLACES
		BigDecimal roundup_areaOfTriangle = new BigDecimal(areaOfTriangle).setScale(4 , BigDecimal.ROUND_HALF_UP);
		
		String output_FileName = " ";
		
		switch(outputFileNumber) 
		{
			case 1: output_FileName = "abasnet_output_C_1.txt";
				break;
		
			case 2: output_FileName = "abasnet_output_C_2.txt";
				break;
			
			case 3: output_FileName = "abasnet_output_C_3.txt";
				break;
			
			case 4: output_FileName = "abasnet_output_C_4.txt";
				break;	
				
			case 5: output_FileName = "abasnet_output_C_5.txt";
				break;
		}// end of switch
		
		FileWriter output = new FileWriter(output_FileName) ;
		output.write(roundup_areaOfTriangle + "\n");
		
		//FIND DISTANCE BETWEEN LINE AND THIRD POINT
		// W VECTOR FROM FIRST POINT TO THIRD POINT
		float w1 = z1 - x1 ;
		float w2 = z2 - x2 ;
		
		// V vector from staring point to second point
		float v1 = y1 - x1 ;
		float v2 = y2 - x2 ;
		
		float magOfV_squared = (float) ( ( Math.pow(v1, 2) + Math.pow(v2, 2) ) ) ;
		
		// foot on a line formula
		// t = ( w_vector * v_vector ) /  magOfV_squared
		float t = ( (w1* v1) + ( w2 * v2 ) ) / magOfV_squared ;
		
		float footOnline_x = x1 + t * v1 ;
		float footOnline_y = x2 + t * v2 ;
		
		// shortest distance between third point and line is a perpendiular line (foot on a line)
		float coord_X = z1 - footOnline_x;
		float coord_Y = z2 - footOnline_y;
		
		
		float distance = (float) Math.sqrt( Math.pow(coord_X , 2) + Math.pow(coord_Y , 2) ) ;
		
		//ROUND UP TO 4 DECIMAL PLACES
		BigDecimal roundup_distance = new BigDecimal(distance).setScale(4 , BigDecimal.ROUND_HALF_UP);
		
		// write distance to a file
		output.write( roundup_distance + "\n");
		
		BigDecimal roundup_footOnline_x = new BigDecimal(footOnline_x).setScale(4 , BigDecimal.ROUND_HALF_UP);
		BigDecimal roundup_footOnline_y = new BigDecimal(footOnline_y).setScale(4 , BigDecimal.ROUND_HALF_UP);
		
		output.write( roundup_footOnline_x  + "	" + roundup_footOnline_y);

		output.close();
		readFile.close();
		outputFileNumber++;
	}// READFILE METHOD

}// PA3_PARTC
