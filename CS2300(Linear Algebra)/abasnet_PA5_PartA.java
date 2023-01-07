import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA5_PartA 
{

	static int outputFileNumber = 1;
	static int countLines = 0;
	
	public static void main(String[] args) throws IOException 
	{
		
		String input_FileName1 = "input_1-1.txt";
		readFile(input_FileName1);
		
		String input_FileName2 = "input_2-1.txt";
		readFile(input_FileName2);

	}// main

	// read the input from the file
	private static void readFile(String fileName) throws IOException 
	{
		
		String outputFile = outputFile();
		FileWriter output = new FileWriter(outputFile);
		countLines = 0;

		Vector<Float> point_OnPlane = new Vector<Float>(); // point on a plane
		Vector<Float> normal_ToPlane = new Vector<Float>(); // point normal to plane
		Vector<Float> proj_dir = new Vector<Float>(); // projection direction vector

		int count = 0;

		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);

		// read the point on plane coordinates
		while (count < 3) 
		{
			point_OnPlane.add(readFile.nextFloat());
			count++;
		} // while loop

		// read the normal to plane coordinates
		count = 0;
		while (count < 3) 
		{
			normal_ToPlane.add(readFile.nextFloat());
			count++;
		} // while loop

		// read the normal to plane coordinates
		count = 0;
		while (count < 3) 
		{
			proj_dir.add(readFile.nextFloat());
			count++;
		} // while loop

		
		// read remaning lines to define point cordinates
		while (readFile.hasNextFloat()) 
		{
			
			Vector<Float> point_ToProj = new Vector<Float>(); // point to be projected on a plane
			
			// add coordinates to vector point_ToProj
			int i = 0;
			while (i < 3)
			{
				point_ToProj.add(readFile.nextFloat());
				i++;
			} // while loop
			
			float value_Of_T = calculate_T(point_OnPlane, normal_ToPlane, proj_dir, point_ToProj);
			Vector<Float> point_After_Projection = calculate_Point_AfterProj(point_ToProj, value_Of_T, proj_dir);
			
			float x_Prime1 = point_After_Projection.get(0);
			float x_Prime2 = point_After_Projection.get(1);
			float x_Prime3 = point_After_Projection.get(2);

			BigDecimal roundup_x_Prime1 = new BigDecimal(x_Prime1).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_x_Prime2 = new BigDecimal(x_Prime2).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_x_Prime3 = new BigDecimal(x_Prime3).setScale(4 , BigDecimal.ROUND_HALF_UP);
			
			output.write(roundup_x_Prime1 + " " + roundup_x_Prime2 + " " + roundup_x_Prime3+ " ");
			countLines++;
			
			// new line after 3 points perline
			if (countLines > 2)
			{
				  output.write("\n");
				  countLines = 0;
			}// if statement
  
		} // while loop
		
		output.close();
		readFile.close();
	}// readFile method

	// calculate the point after projection
	private static Vector<Float> calculate_Point_AfterProj(Vector<Float> point_ToProj, float value_Of_T,
			Vector<Float> proj_dir)  
	{
		Vector<Float> vector = new Vector<Float>();
		float tv1 = value_Of_T * proj_dir.get(0);
		float tv2 = value_Of_T * proj_dir.get(1);
		float tv3 = value_Of_T * proj_dir.get(2);

		float x1_Prime = point_ToProj.get(0) + tv1;
		float x2_Prime = point_ToProj.get(1) + tv2;
		float x3_Prime = point_ToProj.get(2) + tv3;

		vector.add(x1_Prime);
		vector.add(x2_Prime);
		vector.add(x3_Prime);
		return vector;

	}// calculate_Point_AfterProjmethod

	// return value of T
	private static float calculate_T(Vector<Float> point_OnPlane, Vector<Float> normal_ToPlane, Vector<Float> proj_dir,
			Vector<Float> point_ToProj) 
	{
		float t = 0f;
		// coordinates of point on plane
		float q1 = point_OnPlane.get(0);
		float q2 = point_OnPlane.get(1);
		float q3 = point_OnPlane.get(2);

		// coordinates of point before the projection
		float x1 = point_ToProj.get(0);
		float x2 = point_ToProj.get(1);
		float x3 = point_ToProj.get(2);

		// components of normal point
		float n1 = normal_ToPlane.get(0);
		float n2 = normal_ToPlane.get(1);
		float n3 = normal_ToPlane.get(2);

		// components of projection direction vector
		float v1 = proj_dir.get(0);
		float v2 = proj_dir.get(1);
		float v3 = proj_dir.get(2);

		float q_Minus_X1 = q1 - x1;
		float q_Minus_X2 = q2 - x2;
		float q_Minus_X3 = q3 - x3;

		float q_Minus_X_Dot_N1 = q_Minus_X1 * n1;
		float q_Minus_X_Dot_N2 = q_Minus_X2 * n2;
		float q_Minus_X_Dot_N3 = q_Minus_X3 * n3;

		float q_Minus_X_Dot_N = q_Minus_X_Dot_N1 + q_Minus_X_Dot_N2 + q_Minus_X_Dot_N3;

		float v_Dot_N1 = v1 * n1;
		float v_Dot_N2 = v2 * n2;
		float v_Dot_N3 = v3 * n3;

		float v_Dot_N = v_Dot_N1 + v_Dot_N2 + v_Dot_N3;

		t = q_Minus_X_Dot_N / v_Dot_N;

		return t;
	}// calculate_T method

	private static String outputFile() 
	{
		String output_FileName = " ";

		switch (outputFileNumber) 
		{
		case 1:
			output_FileName = "abasnet_PA5_output_A_1.txt";
			break;

		case 2:
			output_FileName = "abasnet_PA5_output_A_2.txt";
			break;

		}// end of switch

		outputFileNumber++;
		return output_FileName;
	}// outputFile method

}// abasnet_PA5_PartA 
