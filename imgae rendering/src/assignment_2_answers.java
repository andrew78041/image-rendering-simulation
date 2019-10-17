import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class assignment_2_answers implements assignment_2 {
	public static void main(String[] args) throws IOException {
		String x = "input.png";
		String y = "text.png";
		String inputpath = "RuskaUfer23-";
		String outputPath = "image";
		assignment_2_answers z = new assignment_2_answers();

		ImageIO.write(z.Blending(x, y, 0.5, 0.5), "png", new File("blended_image.png"));
		z.downsampling(inputpath, outputPath);
		z.imageEnhancement("input.png", " image inhancment part 1", 1);
		z.imageEnhancement("input.png", " image inhancment part 2", 2);
		z.imageEnhancement("input.png", " image inhancment part 3", 3);
	}

	public BufferedImage helper_RGB(BufferedImage x, double d) throws IOException {
		BufferedImage y = new BufferedImage(x.getWidth(), x.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < x.getWidth(); i++) {
			for (int j = 0; j < x.getHeight(); j++) {
				Color myWhite = new Color(x.getRGB(i, j));
				int red = (int) (myWhite.getRed() * d);
				int green = (int) (myWhite.getGreen() * d);
				int blue = (int) (myWhite.getBlue() * d);
				Color wanted = new Color(red, green, blue);
				int rgb = wanted.getRGB();
				y.setRGB(i, j, rgb);
			}
		}

		return y;
	}

	public BufferedImage helper_blending(BufferedImage image, BufferedImage overlay) {
		BufferedImage combined = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) temp.getGraphics();
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
		// set the transparency level in range 0.0f - 1.0f
		g2d.drawImage(overlay, 0, 0, null);
		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(temp, 0, 0, null);
		return combined;

	}

	public BufferedImage Blending(String imagePath1, String imagePath2, double r1, double r2) {
		try {
			BufferedImage given_1 = ImageIO.read(new File(imagePath2));
			BufferedImage given_2 = ImageIO.read(new File(imagePath1));
			BufferedImage A = helper_RGB(given_1, r1);
			BufferedImage B = helper_RGB(given_2, r2);
			return helper_blending(B, A);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public BufferedImage spatial_down_sampling_first_step(BufferedImage x) {
		for (int i = 0; i < x.getWidth() - 1; i = i + 2) {
			for (int j = 0; j < x.getHeight() - 1; j = j + 2) {
				int c = x.getRGB(i, j);
				x.setRGB(i, j + 1, c);
				x.setRGB(i + 1, j, c);
				x.setRGB(i + 1, j + 1, c);
			}
		}
		return x;
	}

	public static BufferedImage spatial_down_sampling_second_step(BufferedImage x) {
		for (int i = 0; i < x.getWidth() - 2; i = i + 4) {
			for (int j = 0; j < x.getHeight() - 2; j = j + 4) {
				int c = x.getRGB(i, j);
				x.setRGB(i, j + 2, c);
				x.setRGB(i, j + 3, c);
				x.setRGB(i + 2, j, c);
				x.setRGB(i + 3, j, c);
				x.setRGB(i + 2, j + 2, c);
				x.setRGB(i + 3, j + 3, c);
				x.setRGB(i + 1, j + 2, c);
				x.setRGB(i + 1, j + 3, c);
				x.setRGB(i + 2, j + 1, c);
				x.setRGB(i + 3, j + 1, c);
				x.setRGB(i + 3, j + 2, c);
				x.setRGB(i + 2, j + 3, c);

			}
		}
		return x;
	}

	public void spatial_down_sampling(String inputpath, String outputPath, int q) {
		try {
			ArrayList<BufferedImage> video = new ArrayList<>();
			BufferedImage x;
			for (int i = 225; i < 257; i++) {
				x = ImageIO.read(new File(inputpath + i + ".jpg"));
				video.add(i - 225, x);
			}
			for (int i = 0; i < video.size(); i++) {
				if (q == 1) {
					BufferedImage temp = spatial_down_sampling_first_step(video.get(i));
					ImageIO.write(temp, "png",
							new File(outputPath + " " + i + " spatial down sampling level " + q + ".png"));
				}
				if (q == 2) {
					BufferedImage temp = spatial_down_sampling_second_step(video.get(i));
					ImageIO.write(temp, "png",
							new File(outputPath + " " + i + " spatial down sampling level " + q + ".png"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void temporal_down_sampling_1(ArrayList<BufferedImage> video) {
		ArrayList<BufferedImage> required = new ArrayList<>();

		for (int i = 0; i < video.size(); i = i + 2) {
			if (i == video.size() - 1) {
				required.add(video.get(i));
			} else {
				required.add(video.get(i));
				required.add(video.get(i));
			}
		}
		for (int i = 0; i < required.size(); i = i + 1) {
			try {
				ImageIO.write(required.get(i), "png",
						new File("outpath " + " " + i + " temporal down sampling" + "1" + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void temporal_down_sampling_2(ArrayList<BufferedImage> video) {
		ArrayList<BufferedImage> required = new ArrayList<>();
		for (int i = 0; i < video.size(); i = i + 4) {
			if (i == video.size() - 1) {
				required.add(video.get(i));
			} else {
				if (i == video.size() - 2) {
					required.add(video.get(i));
					required.add(video.get(i));
				} else {
					if (i == video.size() - 3) {
						required.add(video.get(i));
						required.add(video.get(i));
						required.add(video.get(i));
					} else {
						required.add(video.get(i));
						required.add(video.get(i));
						required.add(video.get(i));
						required.add(video.get(i));
					}
				}
			}
		}
		for (int i = 0; i < required.size(); i = i + 1) {
			try {
				ImageIO.write(required.get(i), "png",
						new File("outpath " + " " + i + " temporal down sampling" + "2" + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void temporal_down_sampling(String inputpath, String outputpath, int q) {
		try {
			ArrayList<BufferedImage> video = new ArrayList<>();
			BufferedImage x;
			for (int i = 225; i < 257; i++) {
				x = ImageIO.read(new File(inputpath + i + ".jpg"));
				video.add(i - 225, x);
			}
		if (q==1){
			temporal_down_sampling_1(video);
		}
		if (q==2){
			temporal_down_sampling_2(video);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void downsampling(String inputPath, String outputPath) {
		// 1st down sampling
		spatial_down_sampling(inputPath, outputPath, 1);
		temporal_down_sampling(inputPath, outputPath, 1);

		// 2nd down sampling
		spatial_down_sampling(inputPath, outputPath, 2);
		temporal_down_sampling(inputPath, outputPath, 2);
	}

	public void imageEnhancement(String inputPath, String outputPath, int key) {
		try {
			int red = 0;
			int blue = 0;
			int green = 0;
			BufferedImage x = ImageIO.read(new File(inputPath));
			for (int i = 0; i < x.getWidth(); i++) {
				for (int j = 0; j < x.getHeight(); j++) {
					Color c = new Color(x.getRGB(i, j));
					red = (int) (c.getRed());
					green = (int) (c.getGreen());
					blue = (int) (c.getBlue());
					if (key == 1) {
						red = (int) (c.getRed() + 50);
						green = (int) (c.getGreen() + 50);
						blue = (int) (c.getBlue() + 50);
					}
					if (key == 2) {
						red = (int) (c.getRed() * c.getRed());
						green = (int) (c.getGreen() * c.getGreen());
						blue = (int) (c.getBlue() * c.getBlue());
					}
					if (key == 3) {
						red = (int) Math.sqrt(c.getRed());
						green = (int) Math.sqrt(c.getGreen());
						blue = (int) Math.sqrt(c.getBlue());
					}

					if (red >= 255) {
						red = 255;
					}
					if (green >= 255) {
						green = 255;
					}
					if (blue >= 255) {
						blue = 255;
					}
					if (red < 0) {
						red = 0;
					}
					if (green < 0) {
						green = 0;
					}
					if (blue < 0) {
						blue = 0;
					}
					Color wanted = new Color(red, green, blue);
					int rgb = wanted.getRGB();
					x.setRGB(i, j, rgb);
				}
			}
			ImageIO.write(x, "png", new File(outputPath + " with key " + key + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
