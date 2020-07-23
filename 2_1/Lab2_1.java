/**

In this task you will implement the method thresholdTransformation of the class Lab2_1 which will set all intensity values below a threshold to 0 and all other values to 255. 

The expected output is provided in the files solution1.png and solution128.png for the two thresholds 1 and 128, respectively.

You may use the following command to check if your output is identical to ours. 

cmp solution1.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
public class Lab2_1 {
	public Lab2_1() {
		Img img = new Img("Fig0314a.png");
		System.out.print("Threshold: ");
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		thresholdTransformation(img, t);
		img.save();
	}
	/**
     * Sets all pixels with intensity below the threshold to black and other pixels to white.
     */
	public void thresholdTransformation(Img i, int threshold) {
		//Your code here
        for (int y=0; y<i.width; y++) {
			for (int x=0; x<i.height; x++) {
				if ((int)(i.img[x*i.width+y] & 0XFF) < threshold) {
					i.img[x*i.width+y] = (byte)0;
				} else {
					i.img[x*i.width+y] = (byte)255;
				}
			}
		}
        
        
	}
		
	public static void main(String[] args) {
		new Lab2_1();
	}
}
