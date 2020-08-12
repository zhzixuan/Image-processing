/**
 * In this task you will implement the method gradientImage of the class P2_1 which will calculate the gradient image.
 * <p>
 * To determine the gradient in images in x and y directions use the masks [-1 0 1]^T and [-1 0 1], respectively.
 * <p>
 * Note that values should be scaled to [0, 255]. This can be done by multiplying with 1 / sqrt(2).
 * <p>
 * The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic.
 **/
public class P2_1 {
    public P2_1() {
        Img img = new Img("Fig0314a.png");
        gradientImage(img);
        img.save();
    }

    public void gradientImage(Img i) {
        //Your code here
        byte[] i2 = new byte[i.img.length];
        System.arraycopy(i.img, 0, i2, 0, i.img.length);
        for (int y = 1; y < i.width - 1; y++) {
            for (int x = 1; x < i.height - 1; x++) {
                int f_x = -1 * (i2[(x - 1) * i.width + y] & 0xFF) + 0 * (i2[x * i.width + y] & 0xFF) + 1 * (i2[(x + 1) * i.width + y] & 0xFF);
                int f_y = -1 * (i2[x * i.width + y - 1] & 0xFF) + 0 * (i2[x * i.width + y] & 0xFF) + 1 * (i2[x * i.width + y + 1] & 0xFF);
                double f = Math.sqrt(Math.pow(f_x, 2) + Math.pow(f_y, 2)) * (1 / Math.sqrt(2));
                i.img[x * i.width + y] = (byte) f;
            }
        }

    }

    public static void main(String[] args) {
        new P2_1();
    }
}
