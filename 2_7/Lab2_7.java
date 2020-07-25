/**

In this task you will implement the method histogramEqualization of the class Lab2_7 which will perform histogram equalization.

The expected output is provided in the files solution1.png and solution2.png.

You may use the following command to check if your output is identical to ours.

cmp solution1.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
public class Lab2_7 {
	public Lab2_7() {
		Img img = new Img("Fig03161.png");
		histogramEqualization(img);
		img.save("out1.png");
		img = new Img("HawkesBay.png");
		histogramEqualization(img);
		img.save("out2.png");
	}

	public void histogramEqualization(Img i) {
		//Your code here
    int[] histogram = new int[256];
    int[] returnValue = new int[256];

    for (int x=0; x<i.img.length; x++){
        histogram[(int)(i.img[x] & 0xFF)] += 1;
    }

    for(int k=0; k<256; k++){
        for(int g=0; g<=k; g++){
            returnValue[k] += histogram[g];
        }
    }
    for (int y=0; y<i.width; y++) {
			for (int x=0; x<i.height; x++) {
                i.img[x*i.width+y] = (byte)(returnValue[(int)(i.img[x*i.width+y] & 0xFF)]
                                            *255/(i.width*i.height));
			}
		}

	}

	public static void main(String[] args) {
		new Lab2_7();
	}
}
