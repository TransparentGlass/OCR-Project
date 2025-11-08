package com.hackathon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TestTesseract {

    public static void main(String[] args) {
        // 1. Input file
        File inputFile = new File("src\\test photos\\russian.jpg");
        File outputFile = new File("src\\test photos\\output.png");

        try {
            // 2. Load image
            BufferedImage originalImage = ImageIO.read(inputFile);

            // 3. Binarize image
            BufferedImage binarizedImage = binarize(originalImage, 180);

            // 4. Save binarized image
            ImageIO.write(originalImage, "png", outputFile);
            System.out.println("Saved binarized image: " + outputFile.getAbsolutePath());

            // 5. Run Tesseract OCR
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("D:\\Software\\Tesseract\\tessdata"); // path to tessdata folder
            // tesseract.setLanguage("jap+eng"); // Russian + English
            tesseract.setOcrEngineMode(1);


            String result = tesseract.doOCR(inputFile); // or use outputFile
            System.out.println("\nOCR Result:\n" + result);

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }

    // Simple global threshold binarization
    public static BufferedImage binarize(BufferedImage img, int threshold) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage binarized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                int value = (gray < threshold) ? 0 : 255; // black or white
                int newRgb = new Color(value, value, value).getRGB();
                binarized.setRGB(x, y, newRgb);
            }
        }
        return binarized;
    }
}
