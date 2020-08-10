import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

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
	
	public void save() {
		try {
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			bi.setData(Raster.createRaster(bi.getSampleModel(), new DataBufferByte(img, img.length), null));
			ImageIO.write(bi, "png", new File("out.png"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
}
