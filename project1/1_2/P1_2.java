/**
 * In this task 2 of project 1 you will implement the inverse fast Fourier transform and perform second order (n=2) ButterWorth low pass filtering in the frequency domain. You should reuse your implementation of the fast Fourier transform from the previous task of this project (P1_1).
 * <p>
 * In the filterImage() method add your code for the second order (n=2) ButterWorth low pass filtering.
 * <p>
 * Implement the inverse fast Fourier transform in the method inverseFourierTransfrom().
 * <p>
 * You may use methods declared in the class Complex.java for your convenience.
 * <p>
 * The solution file is provided for qualitative comparison. It was generated with d0=10, i.e., with the command
 * <p>
 * java P1_2 10
 * <p>
 * Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed.
 **/

import java.time.Instant;
import java.time.Duration;

public class P1_2 {
    public P1_2(double d0) {
        Img img = new Img("ic512.png");
        Instant start = Instant.now();
        filterImage(img, d0);
        Instant stop = Instant.now();
        System.out.println("Elapsed time: " + Duration.between(start, stop).getSeconds() + "s");
        img.save();
    }

    public void filterImage(Img i, double d0) {
        Complex[] F = fastFourierTransfrom(i);
        //Your code here
        int X = i.width / 2;
        int Y = i.height / 2;
        for (int u = 0; u < i.width; u++) {
            for (int v = 0; v < i.height; v++) {
                double D = Math.pow(Math.pow(u - X, 2) + Math.pow(v - Y, 2), 0.5);
                double H = 1 / (1 + Math.pow(D / d0, 4));
                F[u * i.width + v].mul(H);
            }
        }


        inverseFastFourierTransfrom(F, i);
    }

    public Complex[] FFT(Complex[] row) {
        int n = row.length;
        if (n == 1) {
            return new Complex[]{row[0]};
        }

        int K = n / 2;
        Complex[] even = new Complex[K];
        for (int x = 0; x < K; x++) {
            even[x] = row[2 * x];
        }
        Complex[] evenFFT = FFT(even);

        Complex[] odd = even;
        for (int x = 0; x < K; x++) {
            odd[x] = row[2 * x + 1];
        }
        Complex[] oddFFT = FFT(odd);

        Complex[] F = new Complex[n];
        for (int u = 0; u < K; u++) {
            double theta = -2 * Math.PI * u / (double) n;
            Complex W = new Complex(Math.cos(theta), Math.sin(theta));
            W.mul(oddFFT[u]);
            F[u] = new Complex();
            F[u].plus(evenFFT[u]);
            F[u].plus(W);
            F[u + K] = new Complex();
            F[u + K].plus(evenFFT[u]);
            F[u + K].minus(W);
        }
        return F;
    }

    public Complex[] fastFourierTransfrom(Img i) {
        //Change this code
        // fft for row
        Complex[] F_row = new Complex[i.width * i.height];
        for (int x = 0; x < i.height; x++) {
            Complex[] row = new Complex[i.width];
            for (int y = 0; y < i.width; y++) {
                row[y] = new Complex((double) (i.img[x * i.width + y] & 0xFF) * Math.pow(-1, x + y), 0);
            }
            Complex[] fft = FFT(row);
            for (int y = 0; y < i.width; y++) {
                F_row[x * i.width + y] = fft[y];
            }
        }

        // fft for column
        Complex[] F = new Complex[i.width * i.height];
        for (int y = 0; y < i.width; y++) {
            Complex[] column = new Complex[i.height];
            for (int x = 0; x < i.height; x++) {
                column[x] = F_row[x * i.width + y];
            }
            Complex[] fft = FFT(column);
            for (int x = 0; x < i.height; x++) {
                F[x * i.width + y] = fft[x];
            }
        }
        return F;
    }

    private void inverseFastFourierTransfrom(Complex[] F, Img i) {
        //Your code here
        // take all conjugate
        int n = F.length;
        Complex[] F_conj = new Complex[n];

        for (int x = 0; x < n; x++) {
            F_conj[x] = F[x].conjugate();
        }

        // fft for row
        Complex[] f_conj = new Complex[i.width * i.height];
        for (int x = 0; x < i.height; x++) {
            Complex[] row = new Complex[i.width];
            for (int y = 0; y < i.width; y++) {
                row[y] = F_conj[x * i.width + y];
            }
            Complex[] fft = FFT(row);
            for (int y = 0; y < i.width; y++) {
                f_conj[x * i.width + y] = fft[y];
            }
        }

        // fft for column
        Complex[] f_star = new Complex[i.width * i.height];
        for (int y = 0; y < i.width; y++) {
            Complex[] column = new Complex[i.height];
            for (int x = 0; x < i.height; x++) {
                column[x] = f_conj[x * i.width + y];
            }
            Complex[] fft = FFT(column);
            for (int x = 0; x < i.height; x++) {
                f_star[x * i.width + y] = fft[x];
            }
        }


        for (int x = 0; x < i.height; x++) {
            System.out.println(x);
            for (int y = 0; y < i.width; y++) {
                f_star[x * i.width + y].div(i.width * i.height);
                f_star[x * i.width + y].mul(Math.pow(-1, x + y));
                if (f_star[x * i.width + y].r < 0) f_star[x * i.width + y].r = 0;
                i.img[x * i.width + y] = (byte) (f_star[x * i.width + y].r);
            }
        }


    }

    public static void main(String[] args) {
        new P1_2(Double.parseDouble(args[0]));
    }
}
