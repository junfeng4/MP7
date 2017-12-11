/**
 * A class that runs implements several simple transformations on 2D image arrays.
 * <p>
 * This file contains stub code for your image transformation methods that are called by the main
 * class. You will do your work for this MP in this file.
 * <p>
 * Note that you can make several assumptions about the images passed to your functions, both by the
 * web front end and by our testing harness:
 * <ul>
 * <li>You will not be passed empty images.</li>
 * <li>All images will have even width and height.</li>
 * </ul>
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/4/">MP4 Documentation</a>
 */
public class Transform {

    /**
     * Default amount to shift an image's position. Not used by the testing suite, so feel free to
     * change this value.
     */
    public static final int DEFAULT_POSITION_SHIFT = 16;

    /**
     * Pixel value to use as filler when you don't have any valid data. All white with complete
     * transparency. DO NOT CHANGE THIS VALUE: the testing suite relies on it.
     */
    public static final int FILL_VALUE = 0x00FFFFFF;

    /**
     * Offset the picture toward any direction by certain amount.
     * the new image is continuous.
     * @param originalImage the original image
     * @param amount the amount to shift the image to the left/ right/ up/ down.
     * @return the new image
     */
    public static int[][] shiftLeft(final int[][] originalImage, final int amount) {
// offset the picture to the left by amount.

        int[][] newImage = new int[originalImage.length][originalImage[0].length];
        for (int i = 0, a = 0; i < originalImage.length && a < amount; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
                if (i + amount < originalImage.length) {
                    newImage[i][j] = originalImage[i + amount][j];
                } else {
                    for (a = 0; a < amount; a++) {
                    newImage[i + a][j] = originalImage[a][j];
                    }
                    a++;
                }
            }
        }
        return newImage;
    }

    /*
     * Shift right like above.
     */
    /**
     *
     * @param originalImage original image
     * @param amount shift amount
     * @return new image
     */
    public static int[][] shiftRight(final int[][] originalImage, final int amount) {
     // offset the picture to the right by amount.
        int[][] newImage = new int[originalImage.length][originalImage[0].length];
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
                if (i < amount) {
                    newImage[i][j] = originalImage[originalImage.length - amount + i][j];
                } else {
                    newImage[i][j] = originalImage[i - amount][j];
                }
            }
        }
        return newImage;
    }

    /**
     *
     * @param originalImage original image
     * @param amount shift amount
     * @return new image
     */
    public static int[][] shiftUp(final int[][] originalImage, final int amount) {
     // offset the picture upward by amount.
        int[][] newImage = new int[originalImage.length][originalImage[0].length];
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0, a = 0; j < originalImage[0].length && a < amount; j++) {
                if (j + amount < originalImage[0].length) {
                    newImage[i][j] = originalImage[i][j + amount];
                } else {
                    newImage[i][j] = originalImage[i][j];
                    for (a = 0; a < amount; a++) {
                    newImage[i][j + a] = originalImage[i][a];
                    }
                    a++;
                }
            }
        }
        return newImage;
    }

    /**
     *
     * @param originalImage original image
     * @param amount shift amount
     * @return new image
     */
    public static int[][] shiftDown(final int[][] originalImage, final int amount) {
     // offset the picture downward by amount.
        int[][] newImage = new int[originalImage.length][originalImage[0].length];

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
                if (j < amount) {
                    newImage[i][j] = originalImage[i][originalImage[0].length - amount + j];
                } else {
                    newImage[i][j] = originalImage[i][j - amount];
                }
            }
        }
            return newImage;
    }

    /**
     * Rotate the image left by 90 degrees around its center.
     * <p>
     * Any pixels rotated in from off screen should be filled with FILL_VALUE. This function <i>does
     * not modify the original image</i>.
     *
     * @param originalImage the image to rotate left 90 degrees
     * @return the rotated image
     */
    public static int[][] rotateLeft(final int[][] originalImage) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int maxValue = 0xffffff;
        for (int i = 0; i < modifiedImage.length; i++) {
            for (int j = 0; j < modifiedImage[i].length; j++) {
                modifiedImage[i][j] = maxValue;
            }
        }
        int smallerLength, largerLength;
        if (originalImage.length > originalImage[0].length) {
            smallerLength = originalImage[0].length;
            largerLength = originalImage.length;
            int difference = largerLength - smallerLength;
            for (int i = difference / 2; i < largerLength - difference / 2; i++) {
                for (int j = 0; j < smallerLength; j++) {
                    modifiedImage[j + difference / 2][i - difference / 2] = originalImage[i][j];
                }
            }
        } else {
            smallerLength = originalImage.length;
            largerLength = originalImage[0].length;
            int difference = largerLength - smallerLength;
            for (int i = difference / 2; i < largerLength - difference / 2; i++) {
                for (int j = 0; j < smallerLength; j++) {
                    modifiedImage[i - difference / 2][j + difference / 2] = originalImage[j][i];
                }
            }
        }

        return flipVertical(modifiedImage);
    }

    /*
     * Rotate the image right like above.
     */
    /**
     *
     * @param originalImage the image to rotate right 90 degrees
     * @return the rotated image
     */
    public static int[][] rotateRight(final int[][] originalImage) {
        return flipHorizontal(flipVertical(rotateLeft(originalImage)));
    }

    /*
     * Flip the image on the vertical axis across its center.
     */
    /**
    *
    * @param originalImage the image to be vertically flipped
    * @return the flipped image
    */
//  blur / mosaic filter
   public static int[][] flipVertical(final int[][] originalImage) {
        int[][] newImage = new int[originalImage.length][originalImage[0].length];

        final int redShift = 0;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int bitwiseAnd = 0xff;
        final int divisionFactor = 9;

        for (int i = 0; i < originalImage.length - 2; i += 3) {
            for (int j = 0; j < originalImage[0].length - 2; j += 3) {




                int fullColor1 = originalImage[i][j];
                int fullColor2 = originalImage[i + 1][j];
                int fullColor3 = originalImage[i + 2][j];
                int fullColor4 = originalImage[i][j + 1];
                int fullColor5 = originalImage[i + 1][j + 1];
                int fullColor6 = originalImage[i + 2][j + 1];
                int fullColor7 = originalImage[i][j + 2];
                int fullColor8 = originalImage[i + 1][j + 2];
                int fullColor9 = originalImage[i + 2][j + 2];



                int red1 = (fullColor1 >> redShift) & bitwiseAnd;
                int red2 = (fullColor2 >> redShift) & bitwiseAnd;
                int red3 = (fullColor3 >> redShift) & bitwiseAnd;
                int red4 = (fullColor4 >> redShift) & bitwiseAnd;
                int red5 = (fullColor5 >> redShift) & bitwiseAnd;
                int red6 = (fullColor6 >> redShift) & bitwiseAnd;
                int red7 = (fullColor7 >> redShift) & bitwiseAnd;
                int red8 = (fullColor8 >> redShift) & bitwiseAnd;
                int red9 = (fullColor9 >> redShift) & bitwiseAnd;
                int green1 = (fullColor1 >> greenShift) & bitwiseAnd;
                int green2 = (fullColor2 >> greenShift) & bitwiseAnd;
                int green3 = (fullColor3 >> greenShift) & bitwiseAnd;
                int green4 = (fullColor4 >> greenShift) & bitwiseAnd;
                int green5 = (fullColor5 >> greenShift) & bitwiseAnd;
                int green6 = (fullColor6 >> greenShift) & bitwiseAnd;
                int green7 = (fullColor7 >> greenShift) & bitwiseAnd;
                int green8 = (fullColor8 >> greenShift) & bitwiseAnd;
                int green9 = (fullColor9 >> greenShift) & bitwiseAnd;
                int blue1 = (fullColor1 >> blueShift) & bitwiseAnd;
                int blue2 = (fullColor2 >> blueShift) & bitwiseAnd;
                int blue3 = (fullColor3 >> blueShift) & bitwiseAnd;
                int blue4 = (fullColor4 >> blueShift) & bitwiseAnd;
                int blue5 = (fullColor5 >> blueShift) & bitwiseAnd;
                int blue6 = (fullColor6 >> blueShift) & bitwiseAnd;
                int blue7 = (fullColor7 >> blueShift) & bitwiseAnd;
                int blue8 = (fullColor8 >> blueShift) & bitwiseAnd;
                int blue9 = (fullColor9 >> blueShift) & bitwiseAnd;

                int alpha1 = (fullColor1 >> alphaShift) & bitwiseAnd;
                int alpha2 = (fullColor2 >> alphaShift) & bitwiseAnd;
                int alpha3 = (fullColor3 >> alphaShift) & bitwiseAnd;
                int alpha4 = (fullColor4 >> alphaShift) & bitwiseAnd;
                int alpha5 = (fullColor5 >> alphaShift) & bitwiseAnd;
                int alpha6 = (fullColor6 >> alphaShift) & bitwiseAnd;
                int alpha7 = (fullColor7 >> alphaShift) & bitwiseAnd;
                int alpha8 = (fullColor8 >> alphaShift) & bitwiseAnd;
                int alpha9 = (fullColor9 >> alphaShift) & bitwiseAnd;



                int redAvg = (red1 + red2 + red3 + red4 + red5 + red6
                        + red7 + red8 + red9) / divisionFactor;
                int greenAvg = (green1 + green2 + green3 + green4 + green5 + green6
                        + green7 + green8 + green9) / divisionFactor;
                int blueAvg = (blue1 + blue2 + blue3 + blue4 + blue5 + blue6
                        + blue7 + blue8 + blue9) / divisionFactor;
                int alphaAvg = (alpha1 + alpha2 + alpha3 + alpha4 + alpha5 + alpha6
                        + alpha7 + alpha8 + alpha9) / divisionFactor;

                int newColor = (alphaAvg << alphaShift) | (blueAvg << blueShift)
                | (greenAvg << greenShift) | (redAvg << redShift);
                newImage[i][j] = newColor;
                newImage[i + 1][j] = newColor;
                newImage[i + 2][j] = newColor;
                newImage[i][j + 1] = newColor;
                newImage[i + 1][j + 1] = newColor;
                newImage[i + 2][j + 1] = newColor;
                newImage[i][j + 2] = newColor;
                newImage[i + 1][j + 2] = newColor;
                newImage[i + 2][j + 2] = newColor;

            }
        }
        return newImage;
    }


// grayscale filter
    public static int[][] flipHorizontal(final int[][] originalImage) {
        final int redShift = 0;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int bitwiseAnd = 0xff;
        final int divisionFactor = 3;
        int[][] newImage = new int[originalImage.length][originalImage[0].length];

        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[0].length; j++) {
                int red = (originalImage[i][j] >> redShift) & bitwiseAnd;
                int green = (originalImage[i][j] >> greenShift) & bitwiseAnd;
                int blue = (originalImage[i][j] >> blueShift) & bitwiseAnd;
                int alpha = (originalImage[i][j] >> alphaShift) & bitwiseAnd;
                int grayScale = (red + green + blue) / divisionFactor;
                int newFullColor = (alpha << alphaShift) | (grayScale << blueShift)
                | (grayScale << greenShift) | (grayScale << redShift);
                newImage[i][j] = newFullColor;

            }
        }


        return newImage;
    }

    /**
     * Default amount to shift colors by. Not used by the testing suite, so feel free to change this
     * value.
     */
    public static final int DEFAULT_COLOR_SHIFT = 32;

    /**
     *
     * @param originalImage the image that is going to be edited
     * @param amount the amount of change of colors
     * @param componentEditing which element in RGBA is going to be edited
     * @return return the modified image
     */
    public static int[][] generalColor(final int[][] originalImage, final
            int amount, final String componentEditing) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int extract = 0xff;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[i].length; j++) {
                int element = originalImage[i][j];
                int redComponent = (element) & extract;
                int greenComponent = (element >> greenShift) & extract;
                int blueComponent = (element >> blueShift) & extract;
                int alphaComponent = (element >> alphaShift) & extract;
                int newRedComponent;
                int newGreenComponent;
                int newBlueComponent;
                int newAlphaComponent;
                final int maxValue = 255;
                final int minValue = 0;
                if (componentEditing.equalsIgnoreCase("red")) {
                    if (redComponent + amount > maxValue) {
                        newRedComponent = maxValue;
                    } else if (redComponent + amount < minValue) {
                        newRedComponent = minValue;
                    } else {
                        newRedComponent = redComponent + amount;
                    }
                    modifiedImage[i][j] = ((alphaComponent << alphaShift)
                            | (blueComponent << blueShift)
                            | (greenComponent << greenShift) | (newRedComponent));
                } else if (componentEditing.equalsIgnoreCase("green")) {
                    if (greenComponent + amount > maxValue) {
                        newGreenComponent = maxValue;
                    } else if (greenComponent + amount < minValue) {
                        newGreenComponent = minValue;
                    } else {
                        newGreenComponent = greenComponent + amount;
                    }
                     modifiedImage[i][j] = ((alphaComponent << alphaShift)
                             | (blueComponent << blueShift)
                             | (newGreenComponent << greenShift) | (redComponent));
                } else if (componentEditing.equalsIgnoreCase("blue")) {
                    if (blueComponent + amount > maxValue) {
                        newBlueComponent = maxValue;
                    } else if (blueComponent + amount < minValue) {
                        newBlueComponent = minValue;
                    } else {
                        newBlueComponent = blueComponent + amount;
                    }
                    modifiedImage[i][j] = ((alphaComponent << alphaShift)
                            | (newBlueComponent << blueShift)
                            | (greenComponent << greenShift) | (redComponent));
                } else if (componentEditing.equalsIgnoreCase("alpha")) {
                    if (alphaComponent + amount > maxValue) {
                        newAlphaComponent = maxValue;
                    } else if (alphaComponent + amount < minValue) {
                        newAlphaComponent = minValue;
                    } else {
                        newAlphaComponent = alphaComponent + amount;
                    }
                    modifiedImage[i][j] = ((newAlphaComponent << alphaShift)
                            | (blueComponent << blueShift)
                            | (greenComponent << greenShift) | (redComponent));
                }

            }
        }
        return modifiedImage;
    }

    /**
     * Add red to the image.
     * <p>
     * This function <i>does not modify the original image</i>. It should also not generate any new
     * filled pixels.
     *
     * @param originalImage the image to add red to
     * @param amount the amount of red to add
     * @return the recolored image
     */
    public static int[][] moreRed(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount, "red");
    }

    /*
     * Remove red from the image.
     */
    /**
     *
     * @param originalImage the image to subtract red from
     * @param amount the amount of red to subtract
     * @return the recolored image
     */
    public static int[][] lessRed(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount * -1, "red");
    }

    /*
     * Add green to the image.
     */
    /**
     *
     * @param originalImage the image to add green to
     * @param amount the amount of green to add
     * @return the recolored image
     */
    public static int[][] moreGreen(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount, "green");
    }

    /*
     * Remove green from the image.
     */
    /**
    *
    * @param originalImage the image to subtract green from
    * @param amount the amount of green to subtract
    * @return the recolored image
    */
    public static int[][] lessGreen(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount * -1, "green");
    }

    /*
     * Add blue to the image.
     */
    /**
    *
    * @param originalImage the image to add blue to
    * @param amount the amount of blue to add
    * @return the recolored image
    */
    public static int[][] moreBlue(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount, "blue");
    }

    /*
     * Remove blue from the image.
     */
    /**
    *
    * @param originalImage the image to subtract blue from
    * @param amount the amount of blue to subtract
    * @return the recolored image
    */
    public static int[][] lessBlue(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount * -1, "blue");
    }

    /*
     * Increase the image alpha channel.
     */
    /**
     *
     * @param originalImage the image to be made more transparent
     * @param amount the amount of transparency to add
     * @return the recolored image
     */
    public static int[][] moreAlpha(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount, "alpha");
    }

    /*
     * Decrease the image alpha channel.
     */
    /**
     *
     * @param originalImage the image to be made less transparent
     * @param amount the amount of transparency to deduct
     * @return the recolored image
     */
    public static int[][] lessAlpha(final int[][] originalImage, final int amount) {
        return generalColor(originalImage, amount * -1, "alpha");
    }

    /**
     * The default resize factor. Not used by the testing suite, so feel free to change this value.
     */
    public static final int DEFAULT_RESIZE_AMOUNT = 2;

    /*
     * Decrease saturation.
     */
    /**
     * Shrink in the vertical axis around the image center.
     * <p>
     * An amount of 2 will result in an image that is half its original height. An amount of 3 will
     * result in an image that's a third of its original height. Any pixels shrunk in from off
     * screen should be filled with FILL_VALUE. This function <i>does not modify the original
     * image</i>.
     *
     * @param originalImage the image to shrink
     * @param amount the factor by which the image's height is reduced
     * @return the shrunken image
     */
    public static int[][] shrinkVertical(final int[][] originalImage, final int amount) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int extract = 0xff;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int three = 3;
        final int random = 50;
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[i].length; j++) {
                int element = originalImage[i][j];
                int redComponent = (element) & extract;
                int greenComponent = (element >> greenShift) & extract;
                int blueComponent = (element >> blueShift) & extract;
                int alphaComponent = (element >> alphaShift) & extract;
                double h = 0;
                double s = 0;
                double l = 0;
                double[] hsl = new double[three];
                hsl = RGBtoHSL(redComponent, greenComponent, blueComponent, h, s, l);
                h = hsl[0];
                s = hsl[1];
                l = hsl[2];
                if (s >= random) {
                    s -= amount;
                }
                double[] rgb = new double[three];
                rgb = HSLtoRGB(h, s, l, redComponent, greenComponent, blueComponent);
                redComponent = (int) rgb[0];
                greenComponent = (int) rgb[1];
                blueComponent = (int) rgb[2];
                modifiedImage[i][j] = ((alphaComponent << alphaShift)
                | (blueComponent << blueShift)
                | (greenComponent << greenShift) | (redComponent));
            }
        }
        return modifiedImage;
    }

    /*
     * Increase saturation.
     */
    /**
     * @param originalImage the image to expand
    * @param amount the factor by which the image's height is expanded
    * @return the expanded image
    */
    public static int[][] expandVertical(final int[][] originalImage, final int amount) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int extract = 0xff;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int three = 3;
        final int random = 210;
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[i].length; j++) {
                int element = originalImage[i][j];
                int redComponent = (element) & extract;
                int greenComponent = (element >> greenShift) & extract;
                int blueComponent = (element >> blueShift) & extract;
                int alphaComponent = (element >> alphaShift) & extract;
                double h = 0;
                double s = 0;
                double l = 0;
                double[] hsl = new double[three];
                hsl = RGBtoHSL(redComponent, greenComponent, blueComponent, h, s, l);
                h = hsl[0];
                s = hsl[1];
                l = hsl[2];
                if (s <= random) {
                    s += amount;
                }
                double[] rgb = new double[three];
                rgb = HSLtoRGB(h, s, l, redComponent, greenComponent, blueComponent);
                redComponent = (int) rgb[0];
                greenComponent = (int) rgb[1];
                blueComponent = (int) rgb[2];
                modifiedImage[i][j] = ((alphaComponent << alphaShift)
                | (blueComponent << blueShift)
                | (greenComponent << greenShift) | (redComponent));
            }
        }
        return modifiedImage;
    }

    /*
     * Decrease lightness.
     */
    /**
     *  @param originalImage the image to shrink
    * @param amount the factor by which the image's width is reduced
    * @return the shrunken image
    */
    public static int[][] shrinkHorizontal(final int[][] originalImage, final int amount) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int extract = 0xff;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int three = 3;
        final int random = 30;
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[i].length; j++) {
                int element = originalImage[i][j];
                int redComponent = (element) & extract;
                int greenComponent = (element >> greenShift) & extract;
                int blueComponent = (element >> blueShift) & extract;
                int alphaComponent = (element >> alphaShift) & extract;
                double h = 0;
                double s = 0;
                double l = 0;
                double[] hsl = new double[three];
                hsl = RGBtoHSL(redComponent, greenComponent, blueComponent, h, s, l);
                h = hsl[0];
                s = hsl[1];
                l = hsl[2];
                if (l >= random) {
                    l -= amount;
                }
                double[] rgb = new double[three];
                rgb = HSLtoRGB(h, s, l, redComponent, greenComponent, blueComponent);
                redComponent = (int) rgb[0];
                greenComponent = (int) rgb[1];
                blueComponent = (int) rgb[2];
                modifiedImage[i][j] = ((alphaComponent << alphaShift)
                | (blueComponent << blueShift)
                | (greenComponent << greenShift) | (redComponent));
            }
        }
        return modifiedImage;
    }

    public static double[] RGBtoHSL(int red, int green, int blue, double H,double S,double L) {
        double[] hsl = new double[3];
        double R;
        double G;
        double B;
        double Max;
        double Min;
        double d_R;
        double d_G;
        double d_B;
        double d_Max;
        G = green / 255.0;
        B = blue / 255.0;
        R = red / 255.0;
        Max = Math.max(R, Math.max(G, B));
        Min = Math.min(R, Math.min(G, B));
        d_Max = Max - Min;
        L = (Max + Min) / 2.0;
        if (d_Max == 0) {
            H = 0;
            S = 0;
        } else {
            if (L < 0.5) {
                S = d_Max / (Max + Min);
            } else {
                S = d_Max / (2 - Max - Min);
            }
            d_R = (((Max - R) / 6.0) + (d_Max / 2.0)) / d_Max;
            d_G = (((Max - G) / 6.0) + (d_Max / 2.0)) / d_Max;
            d_B = (((Max - B) / 6.0) + (d_Max / 2.0)) / d_Max;

            if (R == Max) {
                H = d_B - d_G;
            }
            else if (G == Max) {
                H = (1.0 / 3.0) + d_R - d_B;
            }
            else if (B == Max) {
                H = (2.0 / 3.0) + d_G - d_R;
            }

            if (H < 0) {
                H += 1;
            }
            if (H > 1) {
                H -= 1;
            }
            H *= 240;
            S *= 240;
            L *= 240;
        }
        hsl[0] = H;
        hsl[1] = S;
        hsl[2] = L;
        return hsl;
    }

    public static double[] HSLtoRGB (double h, double s, double l, double R, double G, double B) {
        double[] rgb = new double[3];
        double d_1, d_2;
        double H = h / 240.0;
        double S = s / 240.0;
        double L = l / 240.0;

        if (S == 0) {
            R = L * 255.0;
            G = L * 255.0;
            B = L * 255.0;
        } else {
            if (L < 0.5) {
                d_2 = L * (1 + S);
            } else {
                d_2 = (L + S) - (L * S);
            }

            d_1 = 2 * L - d_2;

            R = 255 * HuetoRGB (d_1, d_2, H + (1.0 / 3.0));
            G = 255 * HuetoRGB (d_1, d_2, H);
            B = 255 * HuetoRGB (d_1, d_2, H - (1.0 / 3.0));
        }
        rgb[0] = R;
        rgb[1] = G;
        rgb[2] = B;
        return rgb;
    }

    public static double HuetoRGB (double d1, double d2, double dH) {
        if (dH < 0) {
            dH += 1;
        }
        if (dH > 1) {
            dH -= 1;
        }
        if ((6 * dH) < 1) {
            return (d1 + (d2 - d1) * 6.0 * dH);
        }
        if ((2 * dH) < 1) {
            return d2;
        }
        if ((3 * dH) < 2) {
            return (d1 + (d2 - d1)*((2.0 / 3.0) - dH) * 6.0);
        }
        return d1;
    }


    /*
     * Increase lightness.
     */
    /**
     * @param originalImage the image to expand
    * @param amount the factor by which the image's width is expanded
    * @return the expanded image
    */
    public static int[][] expandHorizontal(final int[][] originalImage, final int amount) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int extract = 0xff;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int three = 3;
        final int random = 210;
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[i].length; j++) {
                int element = originalImage[i][j];
                int redComponent = (element) & extract;
                int greenComponent = (element >> greenShift) & extract;
                int blueComponent = (element >> blueShift) & extract;
                int alphaComponent = (element >> alphaShift) & extract;
                double h = 0;
                double s = 0;
                double l = 0;
                double[] hsl = new double[three];
                hsl = RGBtoHSL(redComponent, greenComponent, blueComponent, h, s, l);
                h = hsl[0];
                s = hsl[1];
                l = hsl[2];
                if (l <= random) {
                    l += amount;
                }
                double[] rgb = new double[three];
                rgb = HSLtoRGB(h, s, l, redComponent, greenComponent, blueComponent);
                redComponent = (int) rgb[0];
                greenComponent = (int) rgb[1];
                blueComponent = (int) rgb[2];
                modifiedImage[i][j] = ((alphaComponent << alphaShift)
                | (blueComponent << blueShift)
                | (greenComponent << greenShift) | (redComponent));
            }
        }
        return modifiedImage;
    }

    /**
     * (Reverse color)
     * Remove a green screen mask from an image.
     * <p>
     * This function should remove primarily green pixels from an image and replace them with
     * transparent pixels (FILL_VALUE), allowing you to achieve a green screen effect. Obviously
     * this function will destroy pixels, but it <i>does not modify the original image</i>.
     * <p>
     * While this function is tested by the test suite, only extreme edge cases are used. Getting it
     * work work will with real green screen images is left as a challenge for you.
     *
     * @param originalImage the image to remove a green screen from
     * @return the image with the green screen removed
     */
    public static int[][] greenScreen(final int[][] originalImage) {
        int[][] modifiedImage = new int[originalImage.length][originalImage[0].length];
        final int extract = 0xff;
        final int greenShift = 8;
        final int blueShift = 16;
        final int alphaShift = 24;
        final int maxValue = 255;
        for (int i = 0; i < originalImage.length; i++) {
            for (int j = 0; j < originalImage[i].length; j++) {
                int element = originalImage[i][j];
                int redComponent = (element) & extract;
                int greenComponent = (element >> greenShift) & extract;
                int blueComponent = (element >> blueShift) & extract;
                int alphaComponent = (element >> alphaShift) & extract;
                int newRedComponent = maxValue - redComponent;
                int newGreenComponent = maxValue - greenComponent;
                int newBlueComponent = maxValue - blueComponent;
                modifiedImage[i][j] = ((alphaComponent << alphaShift)
                | (newBlueComponent << blueShift)
                | (newGreenComponent << greenShift) | (newRedComponent));
            }
        }
        return modifiedImage;
    }

    /**
     * A wild and mysterious image transform.
     * <p>
     * You are free to implement this in any way you want. It is not tested rigorously by the test
     * suite, but it should do something (change the original image) and <i>not modify the original
     * image</i>.
     * <p>
     * Call this function mystery. It should take only the original image as a single argument and
     * return a modified image.
     *
     * @param originalImage the image to perform a strange and interesting transform on
     * @return the image transformed in wooly and frightening ways
     */
    public static int[][] mystery(final int[][] originalImage) {
        return rotateLeft(originalImage);
    }
}
