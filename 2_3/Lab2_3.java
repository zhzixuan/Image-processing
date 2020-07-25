/**

In this task you will implement the method logTransformation of the class Lab2_3 which will apply the log transformation on the image.

To pass the test case please use the following value for c.

double c = 255 / Math.log(256);

The expected output is provided in the file solution.png.

You may use the following command to check if your output is identical to ours.

cmp solution.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
public class Lab2_3 {
	public Lab2_3() {
		Img img = new Img("Fig03164.png");
		logTransformation(img);
		img.save();
	}
	/**
     * Applies the log transformation.
     */
	public void logTransformation(Img i) {
		//Your code here
    double c = 255 / Math.log(256);
    int r;
    for (int y=0; y<i.width; y++) {
			for (int x=0; x<i.height; x++) {
                r = (int)i.img[x*i.width+y] & 0XFF;
                i.img[x*i.width+y] = (byte)(c*(Math.log(1+r)));
			}
		}
	}

	public static void main(String[] args) {
		new Lab2_3();
	}
}
