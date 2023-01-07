import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA5_PartD 
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
		
		Vector<Float> point_OnLine_S = new Vector<Float>(); // point on the line
		Vector<Float> point_OnLine_T = new Vector<Float>(); // point on the line
		  
		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName);
		
		// read first point coordinates
		int count = 0;
		while (count < 3) 
		{
			point_OnLine_S.add(readFile.nextFloat());
			count++;
		} // while loop

		// read second point coordinates
		count = 0;
		while (count < 3) 
		{
			point_OnLine_T.add(readFile.nextFloat());
			count++;
		} // while loop

		// avoid the last three coordinates of fist line since its perspective projection
		count = 0;
		while (count < 3)
		{
			readFile.nextFloat();
			count++;
		} // while loop

		String outputFile = outputFile();
		FileWriter output = new FileWriter(outputFile);

		Vector<Float> vector_Of_The_Line = calc_Vector(point_OnLine_S , point_OnLine_T );
		
		// read the point coordinates for triangle
		while(readFile.hasNextFloat())
		{
			// points of the Triangle
			Vector<Float> point_P = new Vector<Float>(); 
			Vector<Float> point_Q = new Vector<Float>();
			Vector<Float> point_R = new Vector<Float>();
			
			// Add point coordinates to point P of a triangle
			int i = 0;
			while (i < 3)
			{
				point_P.add(readFile.nextFloat());
				i++;
			} // while loop
			
			// Add point coordinates to point Q of a triangle
			i = 0;
			while (i < 3)
			{
				point_Q.add(readFile.nextFloat());
				i++;
			} // while loop
			
			// Add point coordinates to point R of a triangle
			i = 0;
			while (i < 3)
			{
				point_R.add(readFile.nextFloat());
				i++;
			} // while loop
			  
			boolean isLine_Interecting_Traingle = find_IsLine_Interecting(point_OnLine_S ,
			  vector_Of_The_Line , point_P , point_Q , point_R );
			
			int intersect = 0;
			
			if(isLine_Interecting_Traingle)
			{
				intersect = 1;
			}//if
			else
			{
				intersect = 0;
			}// else
			
			Vector<Float> normal_To_Triangle = calc_Norm_Triangle(point_P , point_Q ,
					  point_R);
			
			// find the point of intersection on a triangle or extended plane
			float constant_C = calc_Constant_C(normal_To_Triangle ,point_P );
			
			// assume point x on a plane ( 0  0  constant_C/n3 )T
			// find t = ( (x-s)*n / v*n )
			float x_Minus_S_Dot_N = calc_x_Minus_S_Dot_N(constant_C , point_OnLine_S , normal_To_Triangle );
			float v_Dot_N = calc_V_Dot_N( vector_Of_The_Line , normal_To_Triangle);
			float value_Of_t = x_Minus_S_Dot_N / v_Dot_N;
			
			
			//find the intersecting point  on a triangle or extended line
			Vector<Float> point_Y = calc_Point_Y( point_OnLine_S , value_Of_t , vector_Of_The_Line );
			
			float y1 = point_Y.get(0);
			float y2 = point_Y.get(1);
			float y3 = point_Y.get(2);
			
			BigDecimal roundup_y1 = new BigDecimal(y1).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_y2 = new BigDecimal(y2).setScale(4 , BigDecimal.ROUND_HALF_UP);
			BigDecimal roundup_y3 = new BigDecimal(y3).setScale(4 , BigDecimal.ROUND_HALF_UP);
			
			output.write(intersect + " " + roundup_y1 + " " + roundup_y2 + " " + roundup_y3 + "\n" );
			
		}//while loop
		
		output.close();
		readFile.close();
		
	}// readFile method
	
	// calculate vector representing the given line
	private static Vector<Float> calc_Vector(Vector<Float> s, Vector<Float> t) 
	{
		Vector<Float> vector = new Vector<Float>();
		
		vector.add(t.get(0) - s.get(0));
		vector.add(t.get(1) - s.get(1));
		vector.add(t.get(2) - s.get(2));
	
		return vector;
	}// calc_Vector
	
	// normal vector of the triangle
	private static Vector<Float> calc_Norm_Triangle(Vector<Float> p, Vector<Float> q,
			Vector<Float> r) 
	{
		Vector<Float> norm_N = new Vector<>();
		
		// vect_v = q - p
		Vector<Float> vect_V = new Vector<>();
		vect_V.add(q.get(0) - p.get(0));
		vect_V.add(q.get(1) - p.get(1));
		vect_V.add(q.get(2) - p.get(2));
					
		// vect_W = r - p
		Vector<Float> vect_W = new Vector<>();
		vect_W.add(r.get(0) - p.get(0));
		vect_W.add(r.get(1) - p.get(1));
		vect_W.add(r.get(2) - p.get(2));
		
		//cross product
		float n1 = (vect_V.get(1) * vect_W.get(2) ) - ( vect_W.get(1) * vect_V.get(2) );
		float n2 = (vect_V.get(2) * vect_W.get(0) ) - ( vect_W.get(2) * vect_V.get(0) );
		float n3 = (vect_V.get(0) * vect_W.get(1) ) - ( vect_W.get(0) * vect_V.get(1) );
					
		// add normal components to vector norm_N
		norm_N.add(n1);
		norm_N.add(n2);
		norm_N.add(n3);
			
		return norm_N;
	}// calc_Norm_Triangle

	//find if the line intersect bounded triangle or not
	private static boolean find_IsLine_Interecting(Vector<Float> point_OnLine_S, Vector<Float> vector_Of_The_Line,
			Vector<Float> point_P, Vector<Float> point_Q, Vector<Float> point_R) 
	{
		// coordinates of point on line S
		float s1 = point_OnLine_S.get(0);
		float s2 = point_OnLine_S.get(1);
		float s3 = point_OnLine_S.get(2);
		
		// component of vector representing line
		float v1 = vector_Of_The_Line.get(0);
		float v2 = vector_Of_The_Line.get(1);
		float v3 = vector_Of_The_Line.get(2);
		
		// coordinates of point P on Triangle 
		float p1 = point_P.get(0);
		float p2 = point_P.get(1);
		float p3 = point_P.get(2);
		
		// coordinates of point Q on Triangle 
		float q1 = point_Q.get(0);
		float q2 = point_Q.get(1);
		float q3 = point_Q.get(2);
		
		// coordinates of point R on Triangle 
		float r1 = point_R.get(0);
		float r2 = point_R.get(1);
		float r3 = point_R.get(2);
		
		// Calculated on design notebook based on section 11.4 from textbook
		// p + tv = point_P + u1 ( point_Q - point_P ) + u2 ( point_R - point_P )
		
		// assign value to variable to simplify the complex equation
		float a =  ( (q1 - p1)  + (q3 - p3) * (v1 / v3) );
		float b =  ( (r1 - p1)  + (r3 - p3) * (v1 / v3) );
		float c =  ( (s1 - p1)  + (s3 - p3) * (v1 / v3) );
		float d =  ( (q2 - p2)  + (q3 - p3) * (v2 / v3) );
		float e =  ( (r2 - p2)  + (r3 - p3) * (v2 / v3) );
		float f =  ( (s2 - p2)  + (s3 - p3) * (v2 / v3) );
		
		//calculate u2 solving three equation
		float u2 = ( (d * c) - (f * a)) / ( (d * b) - (e * a) );
		float u1 = ( (c - (u2 * b) ) ) / a; 
		
		// if line intersect inside the triangle
		if( (u1 + u2) <= 1.0f)
		{
			return true;
		}// if
		
		// Do not intersect inside the triangle
		else
		{
			return false;
		}// else
		
	}// find_IsLine_Interecting
	
	// calculate C in for a implicit form
	private static float calc_Constant_C(Vector<Float> normal_To_Triangle, Vector<Float> point_P) 
	{
		// Components of normal
		float n1 = normal_To_Triangle.get(0);
		float n2 = normal_To_Triangle.get(1);
		float n3 = normal_To_Triangle.get(2);
		
		// coordinates of point P on Triangle 
		float p1 = point_P.get(0);
		float p2 = point_P.get(1);
		float p3 = point_P.get(2);
		
		float C = ( - ( n1 * p1 ) - ( n2 * p2 ) - ( n3 * p3 ) );
		
		return C;
		
	}// calc_Constant_C

	// calculate dot product of (x-s)*n
	private static float calc_x_Minus_S_Dot_N(float constant_C, Vector<Float> point_OnLine_S,
			Vector<Float> normal_To_Triangle) 
	{
		// coordinates of point on line S
		float s1 = point_OnLine_S.get(0);
		float s2 = point_OnLine_S.get(1);
		float s3 = point_OnLine_S.get(2);
		
		// Components of normal
		float n1 = normal_To_Triangle.get(0);
		float n2 = normal_To_Triangle.get(1);
		float n3 = normal_To_Triangle.get(2);
		
		float result = ( ( - s1 * n1 ) + ( - s2 * n2 ) +  ( ( (- constant_C/n3) - s3 )  * (n3) ) ) ;
		
		return result;
		
	}// calc_x_Minus_S_Dot_N

	// calculate dot product of v*n
	private static float calc_V_Dot_N(Vector<Float> vector_Of_The_Line, Vector<Float> normal_To_Triangle) 
	{
		// component of vector representing line
		float v1 = vector_Of_The_Line.get(0);
		float v2 = vector_Of_The_Line.get(1);
		float v3 = vector_Of_The_Line.get(2);
		
		// Components of normal
		float n1 = normal_To_Triangle.get(0);
		float n2 = normal_To_Triangle.get(1);
		float n3 = normal_To_Triangle.get(2);
		
		float result = (v1 * n1) + (v2 * n2) + (v3 * n3) ;
		
		return result;
		
	}// calc_V_Dot_N
	
	// calculate coordinate of intersecting point
	private static Vector<Float> calc_Point_Y(Vector<Float> point_OnLine_S, float t,
			Vector<Float> vector_Of_The_Line) 
	{
		Vector<Float> point = new Vector<Float>();
		// component of vector representing line
		float v1 = vector_Of_The_Line.get(0);
		float v2 = vector_Of_The_Line.get(1);
		float v3 = vector_Of_The_Line.get(2);
		
		// coordinates of point on line S
		float s1 = point_OnLine_S.get(0);
		float s2 = point_OnLine_S.get(1);
		float s3 = point_OnLine_S.get(2);
		
		point.add( s1 + t * v1) ;
		point.add( s2 + t * v2) ;
		point.add( s3 + t * v3) ;
		
		return point;
	}// calc_Point_Y

	private static String outputFile() 
	{
		String output_FileName = " ";

		switch (outputFileNumber) 
		{
		case 1:
			output_FileName = "abasnet_PA5_output_D_1.txt";
			break;

		case 2:
			output_FileName = "abasnet_PA5_output_D_2.txt";
			break;

		}// end of switch

		outputFileNumber++;
		return output_FileName;
		
	}// outFile method
	
}// abasnet_PA5_PartD
