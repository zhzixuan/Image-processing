/**
  * In this task you will implement the method cornerResponseImage of the class P2_3 which will change the image to the response map R of the Harris corner detector. As usual, ignore the boundary. 
  * 
  * Set pixels to 255 if R > threshold and otherwise set pixels to 0. 
  * 
  *  The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic.
  **/
import java.util.Scanner;
public class P2_3 {
	public P2_3() { 
		Img img = new Img("chessmat.png");
		System.out.print("Sigma: ");
		Scanner in = new Scanner(System.in);
		double s = in.nextDouble();
		System.out.print("Threshold: ");
		double t = in.nextDouble();
		cornerResponseImage(img, s, t);
		img.save();
	}

	public void cornerResponseImage(Img i, double sigma, double threshold) {
		//Your code here
	}

	public static void main(String[] args) {
		new P2_3();
	}
}
