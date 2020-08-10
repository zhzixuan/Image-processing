/**
 * In this task you will implement the method gaussianSmooth of the class P2_2 which will apply 2D Gaussian smoothing to the image.
 * <p>
 * You should implement the 2D convolution using 1D masks (first x then y) for performance reasons. You should also output the size of the mask and the values used for the smoothing mask.
 * <p>
 * Note that you should cut off the Gaussian, as discussed in class. Consider the following input/output:
 * <p>
 * Sigma: 0.5
 * Size: 3
 * Mask: [0.10650697891920077, 0.7869860421615985, 0.10650697891920077]
 * <p>
 * Sigma: 1
 * Size: 7
 * Mask: [0.004433048175243746, 0.05400558262241449, 0.24203622937611433, 0.3990502796524549, 0.24203622937611433, 0.05400558262241449, 0.004433048175243746]
 * <p>
 * Note that the mask is always symmetric and sums to one.
 * Don't worry if you cannot generate the exact values. We will manually check the correctness of your solution.
 * For simplicity, you should handle the boundary case simply by using the original intensities there.
 * <p>
 * The solution files are provided for qualitative comparison. They have been generated with input 1 and 0.5. Output could be different because of differences in floating point arithmetic.
 **/

import java.util.Scanner;
import java.util.Arrays;

public class P2_2 {
    public P2_2() {
        Img img = new Img("Fig0457.png");
        System.out.print("Sigma: ");
        Scanner in = new Scanner(System.in);
        double s = in.nextDouble();
        gaussianSmooth(img, s);
        img.save();
    }

    public void gaussianSmooth(Img i, double sigma) {
        //Your code here

        // Determine when to truncate to calculate the Size
        int x_ = 0;
        double g = 1;
        int size = 0;
        while (true) {
            // System.out.println("x_ is: "+x_);
            g = 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow(x_, 2) / (2 * Math.pow(sigma, 2)));
            if (g <= 0.001) {
                size = 2 * (x_ - 1) + 1;
                System.out.println("Size: " + size);
                break;
            }
            x_++;
        }

        // Calculate the mask
        double mask[] = new double[size];
        double sum = 0;
        for (int a = 0; a < size; a++) {
            mask[a] = 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow(a - size / 2, 2) / (2 * Math.pow(sigma, 2)));
            // System.out.println(mask[a]);
            sum = sum + mask[a];
        }
        // System.out.println("Sum: "+sum);

        // Normalize
        for (int a = 0; a < size; a++) {
            mask[a] = mask[a] / sum;
        }
        System.out.println("Mask: " + Arrays.toString(mask));

        byte[] i2 = new byte[i.img.length];
        double[] i3 = new double[i.img.length];
        System.arraycopy(i.img, 0, i2, 0, i.img.length);
        for (int y = 0; y < i.width; y++) {
            for (int x = 0; x < i.height; x++) {
                i3[x * i.width + y] = (double) (i2[x * i.width + y] & 0xFF);
            }
        }

        // Implement the 2D convolution for x
        int boundary = size / 2;
        for (int y = boundary; y < i.width - boundary; y++) {
            for (int x = boundary; x < i.height - boundary; x++) {
                double g_x = 0;
                for (int a = 0; a < size; a++) {
                    g_x = g_x + (i2[(x + a - size / 2) * i.width + y] & 0xFF) * mask[a];
                }
                i3[x * i.width + y] = g_x;
            }
        }

        // Implement the 2D convolution for y
        for (int y = boundary; y < i.width - boundary; y++) {
            for (int x = boundary; x < i.height - boundary; x++) {
                double g_y = 0;
                for (int a = 0; a < size; a++) {
                    g_y = g_y + i3[x * i.width + (y + a - size / 2)] * mask[a];
                }
                i.img[x * i.width + y] = (byte) g_y;
            }
        }

    }


    public static void main(String[] args) {
        new P2_2();
    }
}
