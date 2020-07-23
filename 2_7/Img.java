import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Img {
	public byte[] img;
	public int width;
	public int height;
	
	public Img(String fileName) {
		load(fileName);
	}
	
	private void load(String fileName) {
		try {
			BufferedImage bi = (ImageIO.read(new File(fileName)));
			width = bi.getWidth();
			height = bi.getHeight();
			img = ((DataBufferByte)bi.getRaster().getDataBuffer()).getData();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	public void save(String fileName) {
		try {
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			bi.setData(Raster.createRaster(bi.getSampleModel(), new DataBufferByte(img, img.length), null));
			ImageIO.write(bi, "png", new File(fileName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	public void saveHistogram(int[] h) {
		try {
			int width = 512;
			int height = 256;
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D g2 = bi.createGraphics();
			int max = 0;
			for (int i=0;i<h.length;i++) if (h[i]>max) max = h[i];
			for (int i=0;i<h.length;i++) {
				g2.draw(new Line2D.Double(2*i, height, 2*i, height-rescale(h[i], max)));
				g2.draw(new Line2D.Double(2*i+1, height, 2*i+1, height-rescale(h[i], max)));
			}
			ImageIO.write(bi, "png", new File("out.png"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	private int rescale(int in, int max) {
		return (int)(in*255.0/max);
	}	
}
