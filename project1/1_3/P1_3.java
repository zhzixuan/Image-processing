/**
 * In this task 3 of project 1 you will design an appropriate filter and apply it to the car image to attenuate the impulse-like bursts in the image. You may reuse existing code from your tasks 1 and 2.
 * <p>
 * Hint: Note that the spatial resolution of the car.png is not a power of 2, i.e., it is not of the form 2^m x 2^n. You should create a new suitable image with padded black pixels by changing code in the constructor of the class P1_3.
 * <p>
 * To avoid reverse engineering, we do not provide a sample solution for this task.
 **/

import java.time.Instant;
import java.time.Duration;

public class P1_3 {
    public P1_3() {
        //Change this code.
        Img img = new Img("car.png");
        Instant start = Instant.now();
        filterImage(img);
        Instant stop = Instant.now();
        System.out.println("Elapsed time: " + Duration.between(start, stop).getSeconds() + "s");
        img.save();
    }

    public void filterImage(Img i) {
        // rescale image
        int p = (int) (Math.log(Math.max(i.width, i.height)) / Math.log(2)) + 1;
//        System.out.println(p);
        int w_o = i.width;
        int h_o = i.height;
        int rescale_size = (int) Math.pow(2, p);
        byte[] i2 = new byte[rescale_size * rescale_size];
        int w = (rescale_size - i.width) / 2;
        int h = (rescale_size - i.height) / 2;
//        System.out.println("h: " + rescale_size);
        for (int x = 0; x < rescale_size; x++) {
            for (int y = 0; y < rescale_size; y++) {
                if ((x >= h && x < (h + i.height)) && (y >= w && y < (w + i.width))) {
//                    System.out.println("x: "+ x + " y: "+ y);
                    i2[x * rescale_size + y] = i.img[(x - h) * i.width + (y - w)];
                } else {
                    i2[x * rescale_size + y] = (byte) 0;
                }
            }
        }
        i.img = i2;
        i.height = rescale_size;
        i.width = rescale_size;

        // FFT
        Complex[] F = fastFourierTransfrom(i);
//        fourierSpectrum(i);

        //Your code here
        // Notch filter
        int M = i.height;
        int N = i.width;
        for (int u = 0; u < i.height; u++) {
            for (int v = 0; v < i.width; v++) {
                double H = notchFilter(u, v, M, N, 42, 45, 10, 5);
                H *= notchFilter(u, v, M, N, 42, -45, 10, 5);
                H *= notchFilter(u, v, M, N, 85, 42, 10, 5);
                H *= notchFilter(u, v, M, N, 85, -42, 10, 5);

                F[u * i.width + v].mul(H);
            }
        }
        // fourierSpectrum(i);
        inverseFastFourierTransfrom(F, i);

        System.out.println();
        byte[] i3 = new byte[w_o * h_o];
        for (int x = 0; x < rescale_size; x++) {
            for (int y = 0; y < rescale_size; y++) {
                if ((x >= h && x < (h + h_o)) && (y >= w && y < (w + w_o))) {
                    // System.out.println("x: "+ (x-h) + " y: "+ (y-w));
                    i3[(x - h) * w_o + (y - w)] = i.img[x * rescale_size + y];
                }
            }
        }
        i.img = i3;
        i.height = h_o;
        i.width = w_o;

    }


    public double notchFilter(int u, int v, int M, int N, int center_u, int center_v, int D0, int n) {
        double Dk = Math.sqrt(Math.pow(u - M / 2 - center_u, 2) + Math.pow(v - N / 2 - center_v, 2));
        double Dk_ = Math.sqrt(Math.pow(u - M / 2 + center_u, 2) + Math.pow(v - N / 2 + center_v, 2));
        double H = (1 / (1 + Math.pow(D0 / Dk, n))) * (1 / (1 + Math.pow(D0 / Dk_, n)));
        return H;
    }

    public void fourierSpectrum(Img i) {
        Complex[] F = fastFourierTransfrom(i);
        double max = Double.NEGATIVE_INFINITY;
        for (int x = 0; x < F.length; x++)
            max = Math.max(F[x].getNorm(), max);
        for (int x = 0; x < i.img.length; x++)
            i.img[x] = (byte) (255 / Math.log(256) * Math.log(255 / max * F[x].getNorm() + 1));
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
//            System.out.println(x);
            for (int y = 0; y < i.width; y++) {
                f_star[x * i.width + y].div(i.width * i.height);
                f_star[x * i.width + y].mul(Math.pow(-1, x + y));
                if (f_star[x * i.width + y].r < 0) f_star[x * i.width + y].r = 0;
                if (f_star[x * i.width + y].r > 255) f_star[x * i.width + y].r = 255;
                i.img[x * i.width + y] = (byte) (f_star[x * i.width + y].r);
            }
        }

    }

    public static void main(String[] args) {
        new P1_3();
    }
}
