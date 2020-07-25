/**

In this task you will implement the method bitPlaneSlicing of the class Lab2_4 which will perform bit plane slicing. Recall that a bit plane is a set of bits corresponding to a given bit position in each of the binary numbers representing the image. In bit plane slicing we will only keeps the bit planes specified in the mask parameter. Note that this mask parameter is specified in the decimal system.

Consider the mask 128. Note that (128)_10 = (1000000)_2
​
and therefore the mask 128 will just keep the most significant bit plane.

Consider the mask 1. Note that (1)_10 = (00000001)_2
​
and therefore the mask 1 will just keep the least significant bit plane.

Consider the mask 192. Note that (192)_10 = (11000000)_2

and therefore the mask 192 will keep the two most significant bit planes.

Similar to task 3 where we implemented the log operation, we also have to perform re-scaling in this task. To perform re-scaling, you may simply divide by the mask and multiply by 255.

The expected output is provided in the files solution1.png, solution128.png and solution192.png for the two thresholds 1, 128 and 192, respectively.

You may use the following command to check if your output is identical to ours.

cmp solution1.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
public class Lab2_4 {
	public Lab2_4() {
		Img img = new Img("Fig0314a.png");
		System.out.print("Mask: ");
		Scanner in = new Scanner(System.in);
		int mask = in.nextInt();
		bitPlaneSlicing(img, mask);
		img.save();
	}
	/**
     * Implement bit-plane slicing. The mask parameter specifies (in decimal) the
	 * bitplanes that will remain.
     */
	public void bitPlaneSlicing(Img i, int mask) {
		//Your code here
    for (int y=0; y<i.width; y++) {
			for (int x=0; x<i.height; x++) {
                i.img[x*i.width+y] &= (byte)mask;
                i.img[x*i.width+y] = (byte)(((int)i.img[x*i.width+y] & 0xFF) *255 /mask);
			}
		}

	}

	public static void main(String[] args) {
		new Lab2_4();
	}
}
