import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA5_PartC 
{
	static int outputFileNumber = 1;
	public static void main(String[] args) throws IOException 
	{
		String input_FileName1 = "input_1-1.txt";
		readFile(input_FileName1);
		
		String input_FileName2 = "input_2-1.txt";
		readFile(input_FileName2);

	}// main

	private static void readFile(String fileName) throws IOException 
	{
		String outputFile = outputFile();
		FileWriter output = new FileWriter(outputFile);

		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);
		
		while (readFile.hasNextFloat()) 
		{
			Vector<Float> point_OnPlane = new Vector<Float>(); // point on a plane
			Vector<Float> normal_ToPlane = new Vector<Float>(); // point normal to plane
			Vector<Float> coord_OfPOint = new Vector<Float>(); // coordinates of a plane
			
			// coordinates of point on the plane
			int count = 0;
			while (count < 3) 
			{
				point_OnPlane.add(readFile.nextFloat());
				count++;
			} // while loop
			
			// normal to the plane
			count = 0;
			while (count < 3) 
			{
				normal_ToPlane.add(readFile.nextFloat());
				count++;
			} // while loop
			
			// coordinates of point
			count = 0;
			while (count < 3) 
			{
				coord_OfPOint.add(readFile.nextFloat());
				count++;
			} // while loop
			
			float mag_norm = calc_Mag(normal_ToPlane);
			float norm_C = calc_Norm_C(normal_ToPlane , point_OnPlane , mag_norm);
			
			float norm_N_Dot_P = calc_Norm_N_Dot_P(normal_ToPlane , coord_OfPOint , mag_norm );
			
			// t = c + np
			// it is also the distance of point p to the plane
			float value_Of_T = norm_C + norm_N_Dot_P ;
			
			Vector<Float> point_Coord_Of_X = calc_point_Coord_Of_X(coord_OfPOint , value_Of_T , normal_ToPlane) ;
			
			float x1 = point_Coord_Of_X.get(0);
			float x2 = point_Coord_Of_X.get(1);
			float x3 = point_Coord_Of_X.get(2);
			
			BigDecimal roundup_x1 = new BigDecimal(x1).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_x2 = new BigDecimal(x2).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_x3 = new BigDecimal(x3).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_value_Of_T = new BigDecimal(value_Of_T).setScale(4 , BigDecimal.ROUND_HALF_UP);
			
			output.write(roundup_x1 + " " + roundup_x2 + " " + roundup_x3 + " " + roundup_value_Of_T + "\n");
		}// while loop
		output.close();
		readFile.close();
	}// readFile method

	private static float calc_Norm_N_Dot_P(Vector<Float> normal_ToPlane, Vector<Float> coord_OfPOint, float mag_Norm) 
	{
		// component of normal vector
		float n1 = normal_ToPlane.get(0);
		float n2 = normal_ToPlane.get(1);
		float n3 = normal_ToPlane.get(2);
				
		//coordinates of points 
		float p1 = coord_OfPOint.get(0);
		float p2 = coord_OfPOint.get(1);
		float p3 = coord_OfPOint.get(2);
		
		float n_Dot_P = ( n1 * p1 ) + ( n2 * p2 ) + ( n3 * p3 );
		float norm_N_Dot_P = n_Dot_P /  mag_Norm ;
		return norm_N_Dot_P;
	}

	// calculate magnitude of normal
	private static Float calc_Mag(Vector<Float> normal_ToPlane) 
	{
		
		float normal_N1 = normal_ToPlane.get(0);
		float normal_N2 = normal_ToPlane.get(1);
		float normal_N3 = normal_ToPlane.get(2);
		
		float mag_Normal = (float) Math.sqrt ( (Math.pow(normal_N1 , 2)) + (Math.pow(normal_N2, 2)) + (Math.pow(normal_N3, 2)));
		
		return mag_Normal;
	}// calc_Mag

	// calculate value of C to represent implicit form
	private static float calc_Norm_C(Vector<Float> normal_ToPlane, Vector<Float> point_OnPlane, float mag_Norm) 
	{
		// component of normal vector
		float n1 = normal_ToPlane.get(0);
		float n2 = normal_ToPlane.get(1);
		float n3 = normal_ToPlane.get(2);
		
		//coordinates of points on plane
		float q1 = point_OnPlane.get(0);
		float q2 = point_OnPlane.get(1);
		float q3 = point_OnPlane.get(2);
		
		
		float value_Of_C = - (n1 * q1) - (n2 * q2) - (n3 * q3);
		
		float norm_Val_C = value_Of_C / mag_Norm ;
		
		return norm_Val_C;
	}// calc_Norm_C method
	
	// calculate coordinates of point x
	private static Vector<Float> calc_point_Coord_Of_X(Vector<Float> coord_OfPOint, float value_Of_T,
			Vector<Float> normal_ToPlane) 
	{
		//coordinate 0f point P(given in input file)
		float p1 = coord_OfPOint.get(0);
		float p2 = coord_OfPOint.get(1);
		float p3 = coord_OfPOint.get(2);
		
		float t_N1 = value_Of_T * normal_ToPlane.get(0);
		float t_N2 = value_Of_T * normal_ToPlane.get(1);
		float t_N3 = value_Of_T * normal_ToPlane.get(2);
		
		Vector<Float> point_X = new Vector<>();
		
		// x = p - t*n
		point_X.add(p1 - t_N1);
		point_X.add(p2 - t_N2);
		point_X.add(p3 - t_N3);
		
		return point_X;
	}// calc_point_Coord_Of_X method
	
	private static String outputFile() 
	{
		String output_FileName = " ";

		switch (outputFileNumber) 
		{
		case 1:
			output_FileName = "abasnet_PA5_output_C_1.txt";
			break;

		case 2:
			output_FileName = "abasnet_PA5_output_C_2.txt";
			break;

		}// end of switch

		outputFileNumber++;
		return output_FileName;
		
	}// output File method

}// abasnet_PA5_PartC 
