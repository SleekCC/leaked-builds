package today.sleek.client.utils.math;

import today.sleek.Sleek;
import today.sleek.base.protection.ProtectionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {

//    public static boolean isInputBetween(int input, int min, int max) {
//        return input >= min && input <= max;
//    }

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;

        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }

    public static String checksum() {
        try {
            CodeSource source = Sleek.class.getProtectionDomain().getCodeSource();
            File file = new File(source.getLocation().toURI().getPath());

            MessageDigest digest = MessageDigest.getInstance("MD5");
            String cs = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(digest, file);

            //Get file input stream for reading the file content
            FileInputStream fis = new FileInputStream(file);

            //Create byte array to read data in chunks
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            //Read file data and update in message digest
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }


            //close the stream; We don't need it now.
            fis.close();

            //Get the hash's bytes
            byte[] bytes = digest.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //return complete hash
            return sb.toString();
        } catch (Exception e) {

        }
        return "none found???";
    }

//    public static float secRanFloat(float min, float max) {
//
//        SecureRandom rand = new SecureRandom();
//
//        return rand.nextFloat() * (max - min) + min;
//    }

    public static int secRanInt(int min, int max) {

        SecureRandom rand = new SecureRandom();

        return rand.nextInt() * (max - min) + min;
    }

    public static double secRanDouble(double min, double max) {

        SecureRandom rand = new SecureRandom();

        return rand.nextDouble() * (max - min) + min;
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        float range = max - min;
        float scaled = random.nextFloat() * range;
        return scaled + min;
    }

    public static int getRandomInRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static double getIncre(double val, double inc) {
        double one = 1 / inc;
        return Math.round(val * one) / one;
    }

    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

//    public static double newRound(double value, int precision) {
//        int scale = (int) Math.pow(10, precision);
//        return (double) Math.round(value * scale) / scale;
//    }
//
//    public static double roundPlace(final double value, final int places) {
//        if (places < 0) {
//            throw new IllegalArgumentException();
//        }
//        BigDecimal bd = new BigDecimal(value);
//        bd = bd.setScale(places, RoundingMode.HALF_UP);
//        return bd.doubleValue();
//    }
//
//    public static double defaultSpeed() {
//        double baseSpeed = 0.2873D;
//        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
//            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
//            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
//        }
//        return baseSpeed;
//    }
//
//    public static int getJumpEffect() {
//        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump))
//            return Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
//        else
//            return 0;
//    }

    public static double setRandom(final double min, final double max) {
        final Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    public static float setRandom(final float min, final float max) {
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }

//    public static double roundToPlace(final double value, final int places) {
//        if (places < 0) {
//            throw new IllegalArgumentException();
//        }
//        BigDecimal bd = new BigDecimal(value);
//        bd = bd.setScale(places, RoundingMode.HALF_UP);
//        return bd.doubleValue();
//    }
//
//    public static double preciseRound(double value, double precision) {
//        double scale = Math.pow(10, precision);
//        return (double) Math.round(value * scale) / scale;
//    }

    public static float round(float value, float offset) {
        return value % offset;
    }

    public static float round(float value, float value2, boolean random) {
        if (random) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                value -= value % value2;
            } else {
                value += value % value2;
            }
        } else {
            value -= value % value2;
        }
        return value;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }

    public static double map(double val, double mx, double from, double to) {
        return Math.min(Math.max(from + (val / mx) * (to - from), from), to);
    }

//    public static double randomFloatValue() {
//        return ThreadLocalRandom.current().nextFloat();
//    }
//
//    public static double randomNumber(double min, double max) {
//        return ThreadLocalRandom.current().nextDouble(min, max);
//    }
    public static float[][] getArcVertices(final float radius,
                                           final float angleStart,
                                           final float angleEnd,
                                           final int segments) {
        final float range = Math.max(angleStart, angleEnd) - Math.min(angleStart, angleEnd);
        final int nSegments = Math.max(2, Math.round((360.f / range) * segments));
        final float segDeg = range / nSegments;

        final float[][] vertices = new float[nSegments + 1][2];
        for (int i = 0; i <= nSegments; i++) {
            final float angleOfVert = (angleStart + i * segDeg) / 180.f * (float) Math.PI;
            vertices[i][0] = ((float) Math.sin(angleOfVert)) * radius;
            vertices[i][1] = ((float) -Math.cos(angleOfVert)) * radius;
        }

        return vertices;
    }
}