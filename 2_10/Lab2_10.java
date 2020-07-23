/**

In this task you will implement the method laplacianFilter of the class Lab2_10 which applies the Laplacian filter on the image.

Implement the isotropic mask for rotations in  increments of 45 degrees with a positive weight at the center. Clip all values to be within 0 to 255. 

The expected output is provided in the file solution.png.

You may use the following command to check if your output is identical to ours. 

cmp solution.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
public class Lab2_10 {
	public Lab2_10() {
		Img img = new Img("Fig0338.png");
		laplacianFilter(img);
		img.save();
	}
	
	public void laplacianFilter(Img i) {
		//Your code here
        
        double[] filter = {-1, -1, -1, -1, 8, -1, -1, -1, -1};
        
        byte[] i2 = new byte[i.img.length];
        System.arraycopy(i.img, 0, i2, 0, i.img.length);
        
        for (int x=1; x<i.height-1; x++) {
			for (int y=1; y<i.width-1; y++) {
                double sum = 0;
                for(int j=-1; j<=1; j++){
                    for(int k=-1; k<=1; k++){
                        sum += (double)(i2[(x+j)*i.width+y+k] & 0xFF) * filter[(j+1)*3+k+1];
                    }
                }
                i.img[x*i.width+y] = (byte)Math.max(0, Math.min(255, sum));
                
			}
		}
	}
		
	public static void main(String[] args) {
		new Lab2_10();
	}
}
