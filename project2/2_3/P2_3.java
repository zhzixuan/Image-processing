/**
 * In this task you will implement the method cornerResponseImage of the class P2_3 which will change the image to the response map R of the Harris corner detector. As usual, ignore the boundary.
 * <p>
 * Set pixels to 255 if R > threshold and otherwise set pixels to 0.
 * <p>
 * The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic.
 **/

import java.util.Arrays;
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

    public void gaussianSmooth(int width, int height, double[] img, double sigma) {
        // Calculate the Gaussian mask
        int x_ = 0;
        double g = 1;
        int size = 0;
        while (true) {
            g = 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow(x_, 2) / (2 * Math.pow(sigma, 2)));
            if (g < 0.001) {
                size = 2 * (x_ - 1) + 1;
                break;
            }
            x_++;
        }
//        System.out.println(size);

        double mask[] = new double[size];
        double sum = 0;
        for (int a = 0; a < size; a++) {
            mask[a] = 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow(a - size / 2, 2) / (2 * Math.pow(sigma, 2)));
            sum = sum + mask[a];
        }
        for (int a = 0; a < size; a++) {
            // Normalize
            mask[a] = mask[a] / sum;
        }

//        System.out.println("Mask: " + Arrays.toString(mask));

        // 1D mask in x
        double[] temp = new double[img.length];
        int boundary = size / 2;
        for (int x = boundary; x < height - boundary; x++) {
            for (int y = boundary; y < width - boundary; y++) {
                for (int a = 0; a < size; a++) {
                    temp[x * width + y] += img[(x + a - size / 2) * width + y] * mask[a];
                }
            }
        }

        // 1D mask in y
        for (int x = boundary; x < height - boundary; x++) {
            for (int y = boundary; y < width - boundary; y++) {
                double f = 0;
                for (int a = 0; a < size; a++) {
                    f += temp[x * width + y + a - size / 2] * mask[a];
                }
                img[x * width + y] = f;
            }
        }

    }

    public void cornerResponseImage(Img i, double sigma, double threshold) {
        //Your code here
        double[] f_xx = new double[i.img.length];
        double[] f_yy = new double[i.img.length];
        double[] f_xy = new double[i.img.length];
        for (int y = 1; y < i.width-1; y++) {
            for (int x = 1; x < i.height-1; x++) {
                // 1. Calculate gradient f_x f_y using mask [-1, 0, 1]
                double f_x = (i.img[(x + 1) * i.width + y] & 0xFF) - (i.img[(x - 1) * i.width + y] & 0xFF);
                double f_y = (i.img[x * i.width + y + 1] & 0xFF) - (i.img[x * i.width + y - 1] & 0xFF);

                // 2. Three images f_xf_x f_yf_y and f_xf_y
                f_xx[x * i.width + y] = f_x * f_x;
                f_yy[x * i.width + y] = f_y * f_y;
                f_xy[x * i.width + y] = f_x * f_y;
            }
        }

        // Apply 1D gaussian masks (first x then y) of f_xf_x f_yf_y and f_xf_y
        gaussianSmooth(i.width, i.height, f_xx, sigma);
        gaussianSmooth(i.width, i.height, f_yy, sigma);
        gaussianSmooth(i.width, i.height, f_xy, sigma);

        // Form an image of R using the smoothed images of derivatives
        for (int y = 0; y < i.width; y++) {
            for (int x = 0; x < i.height; x++) {
                // R=λ1λ2-k(λ1+λ2)^2
                double det = f_xx[x * i.width + y] * f_yy[x * i.width + y] - f_xy[x * i.width + y] * f_xy[x * i.width + y];
                double trace = f_xx[x * i.width + y] + f_yy[x * i.width + y];
                double k = 0.04;
                double r = det - k * Math.pow(trace, 2);
                if (r > threshold) {
                    i.img[x * i.width + y] = (byte) 255;
                } else {
                    i.img[x * i.width + y] = (byte) 0;
                }
            }
        }

    }

    public static void main(String[] args) {
        new P2_3();
    }
}
