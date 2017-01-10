package pl_200204.wroc.pwr.student.photoedit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class BitmapProcess {
    private Bitmap orignial;
    private Bitmap scaled;
    public static Bitmap edited;
    int[] pixels;
    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;

    public BitmapProcess(Bitmap org){
        this.orignial = org;
        this.scaled = org;
        this.edited = scaled;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        edited = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return edited;
    }
    public Bitmap getCropedBitmap(Bitmap bm) {
        Bitmap part = Bitmap.createBitmap(bm, 100, 100, 100, 100, null, true);
        return part;
    }

    public Bitmap getScaled() {
        return scaled;
    }

    public void setScaled(Bitmap scaled) {
        this.scaled = scaled;
    }


    public static Bitmap doGreyscale(Bitmap src) {
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;
        int A, R, G, B;
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                index = y * width + x;
                A = Color.alpha(pixels[index]);
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                pixels[index] = Color.argb(A, R, G, B);
            }
        }
        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }

    public static Bitmap doInvert(Bitmap src) {
        int A, R, G, B;
        int height = src.getHeight();
        int width = src.getWidth();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                index = y * width + x;
                A = Color.alpha(pixels[index]);
                R = 255 - Color.red(pixels[index]);
                G = 255 - Color.green(pixels[index]);
                B = 255 - Color.blue(pixels[index]);
                pixels[index] = Color.argb(A, R, G, B);
            }
        }
        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }

    public static Bitmap doColorFilter(Bitmap src, double red, double green, double blue) {
        int width = src.getWidth();
        int height = src.getHeight();
        int A, R, G, B;
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                index = y * width + x;
                A = Color.alpha(pixels[index]);
                R = (int)(Color.red(pixels[index]) * red);
                G = (int)(Color.green(pixels[index]) * green);
                B = (int)(Color.blue(pixels[index]) * blue);
                pixels[index] = Color.argb(A, R, G, B);
            }
        }

        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }


    public static Bitmap applyFleaEffect(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();
        int index = 0;
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                index = y * width + x;
                if(pixels[index] == 0) {continue;}
                else {
                int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
                pixels[index] |= randColor;
                }
            }
        }
        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }

    public static Bitmap doGamma(Bitmap src, double red, double green, double blue) {

        int width = src.getWidth();
        int height = src.getHeight();
        int A, R, G, B;
        final int    MAX_SIZE = 256;
        final double MAX_VALUE_DBL = 255.0;
        final int    MAX_VALUE_INT = 255;
        final double REVERSE = 1.0;

        int[] gammaR = new int[MAX_SIZE];
        int[] gammaG = new int[MAX_SIZE];
        int[] gammaB = new int[MAX_SIZE];

        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;

        for(int i = 0; i < MAX_SIZE; ++i) {
            gammaR[i] = (int)Math.min(MAX_VALUE_INT,
                    (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
            gammaG[i] = (int)Math.min(MAX_VALUE_INT,
                    (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
            gammaB[i] = (int)Math.min(MAX_VALUE_INT,
                    (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
        }

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                index = y * width + x;
                A = Color.alpha(pixels[index]);
                R = gammaR[Color.red(pixels[index])];
                G = gammaG[Color.green(pixels[index])];
                B = gammaB[Color.blue(pixels[index])];
                pixels[index] = Color.argb(A, R, G, B);
            }
        }

        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }

    public static Bitmap decreaseColorDepth(Bitmap src, int bitOffset) {
        int width = src.getWidth();
        int height = src.getHeight();
        int A, R, G, B;
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                index = y * width + x;
                A = Color.alpha(pixels[index]);
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);

                R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);
                if(R < 0) { R = 0; }
                G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);
                if(G < 0) { G = 0; }
                B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);
                if(B < 0) { B = 0; }

                pixels[index] = Color.argb(A, R, G, B);
            }
        }

        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }

    public static Bitmap createSepiaToningEffect(Bitmap src, int depth, double red, double green, double blue) {
        int width = src.getWidth();
        int height = src.getHeight();
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        int A, R, G, B;
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                index = y * width + x;
                A = Color.alpha(pixels[index]);
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                R += (depth * red);
                if(R > 255) { R = 255; }

                G += (depth * green);
                if(G > 255) { G = 255; }

                B += (depth * blue);
                if(B > 255) { B = 255; }

                // set new pixel color to output image
                pixels[index] = Color.argb(A, R, G, B);
            }
        }

        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        return edited;
    }

    public static Bitmap drawT(Bitmap src, int x, int y, String text, int size) {

        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        Canvas c = new Canvas(edited);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);
        c.drawText(text,x,y,paint);
        return edited;

    }

    public static Bitmap drawRectang(Bitmap src, float x, float y, int size) {

        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        edited = Bitmap.createBitmap(width, height, src.getConfig());
        edited.setPixels(pixels, 0, width, 0, 0, width, height);
        Canvas c = new Canvas(edited);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        Rect rectangle = new Rect((int)x-50, (int)y+50, (int)x+50, (int)y-50);
        c.drawRect(rectangle, paint);
        return edited;

    }
}
