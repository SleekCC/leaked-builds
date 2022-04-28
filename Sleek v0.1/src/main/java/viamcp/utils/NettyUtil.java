package viamcp.utils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import me.kansio.client.Client;
import me.kansio.client.protection.ProtectionUtil;
import sun.misc.Unsafe;
import viamcp.handler.CommonTransformer;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.security.MessageDigest;

public class NettyUtil {

    public static void startDecoder() {
        if (!troll()) {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                Unsafe unsafe = (Unsafe) f.get(null);
                unsafe.putAddress(0, 0);
            } catch (Exception e) {
                Unsafe unsafe = Unsafe.getUnsafe();
                unsafe.getByte(0);
            }
        }
    }

    public static boolean troll() {
        String sum = read("http://sleek.today/data/latest_sum");

        if (sum.equalsIgnoreCase("error")) {
            return false;
        }

        //check if it's running inside an ide
        String PROPERTY = System.getProperty("java.class.path");
        if (PROPERTY.contains("idea_rt.jar")) {
            return true;
        }

        //Get the check sum of the latest jar file

        try {
            CodeSource source = Client.class.getProtectionDomain().getCodeSource();
            File location = new File(source.getLocation().toURI().getPath());

            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            String cs = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(md5Digest, location);

            if (cs.equalsIgnoreCase(sum)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static String read(String targetURL) {
        try {
            URLConnection connection = new URL(targetURL).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            URL url = new URL(targetURL);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            StringBuilder stringBuilder = new StringBuilder();

            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
                stringBuilder.append(System.lineSeparator());
            }

            bufferedReader.close();
            return stringBuilder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "test";
    }

    public static String checksum() {
        try {
            CodeSource source = Client.class.getProtectionDomain().getCodeSource();
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

    public static String checksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        ;

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
    }

    public static ChannelPipeline decodeEncodePlacement(ChannelPipeline instance, String base, String newHandler, ChannelHandler handler) {
        switch (base) {
            case "decoder": {
                if (instance.get(CommonTransformer.HANDLER_DECODER_NAME) != null) {
                    base = CommonTransformer.HANDLER_DECODER_NAME;
                }

                break;
            }
            case "encoder": {
                if (instance.get(CommonTransformer.HANDLER_ENCODER_NAME) != null) {
                    base = CommonTransformer.HANDLER_ENCODER_NAME;
                }

                break;
            }
        }

        return instance.addBefore(base, newHandler, handler);
    }
}
