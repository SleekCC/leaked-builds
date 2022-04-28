package viamcp.utils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import org.apache.logging.log4j.LogManager;
import sun.misc.Unsafe;
import today.sleek.Sleek;
import today.sleek.base.protection.ProtectionUtil;
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
        return ProtectionUtil.gsudfgyfuisadgfdsouaiygsdeugdsoygfsdhohiusdfhuisdghiudgshiufssfdhiushudsdfuhfdshufdshuisfdhsfdhiusfdhuifsdhuifsdhuisfdhiufsdhiufsdhiusfdhuisfdhuifsdhuifsdhuifsdhiufsdiuhfsdhiufdshuisfdhui();
    }

    public static String read(String targetURL) {
        try {
            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            URLConnection ohisdfhiosfdhoifsdhiofsdhifosdfhsdiodhsfiohiofsdhiodfhiofsdhiofdshisfdoshfiodsfdhiofdshiofsdhiofdshiohiosfhiofsdhiofsdhiosfdhiofdhifsdihofsdhiofdshifosdhiofsdhifosdhfsoid = new URL(targetURL).openConnection();
            ohisdfhiosfdhoifsdhiofsdhifosdfhsdiodhsfiohiofsdhiodfhiofsdhiofdshisfdoshfiodsfdhiofdshiofsdhiofdshiohiosfhiofsdhiofsdhiosfdhiofdhifsdihofsdhiofdshifosdhiofsdhifosdhfsoid.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            ohisdfhiosfdhoifsdhiofsdhifosdfhsdiodhsfiohiofsdhiodfhiofsdhiofdshisfdoshfiodsfdhiofdshiofsdhiofdshiohiosfhiofsdhiofsdhiosfdhiofdhifsdihofsdhiofdshifosdhiofsdhifosdhfsoid.connect();

            URL hisdijofsdsfjidofsdjfsiojsdiofjsdijfiwsjdiofjsdifjsijdfugsdfugihsfdiupggsfdhiupgsfdhpiufgsdhpusfgdphusgfhupsgfdhupsdgfhupsgfdhpusfgdhpusdfg = new URL(targetURL);

            BufferedReader sjidofidsjofsdjiodfsjiosdfjidfjiofdsijosdfjiosfdijosfdjiofdsijodfsjiodsfijsdofjiodfsijsfojisfodijofdsjiosdfjiofsdjiofdsjifsdjoisfdjiosfdjiofsdjiodsfjiodsfjiofjidofsjiodsfjiofsdijofdsjiosfd = new BufferedReader(
                    new InputStreamReader(hisdijofsdsfjidofsdjfsiojsdiofjsdijfiwsjdiofjsdifjsijdfugsdfugihsfdiupggsfdhiupgsfdhpiufgsdhpusfgdphusgfhupsgfdhupsdgfhupsgfdhpusfgdhpusdfg.openStream()));

            StringBuilder hsdhodsfhoisdfhiofdshfisodhfsdiosfhdiodsfhoisfdhoifdhiofdshiosdfhoidfhoifdshiosdofhdfhghiuhsfdguihsfdiughusifhsdudfghpuigshfdupgfsdhupihpuigsfdhpugfsdhpugsfdhpgusfdihgufpsdsghfudphfgsduphsgfdupfsdhgpuhgsfpudhfgpusdihpuisfgdphdufsghpgfudhupdgfshpugfsdhfudgpfhgdupsphusfgdi = new StringBuilder();

            String sdifisjdosfdjiosfdjiofdsjiodjsfiosdfjiofsdjiofdsjoifsdjiofsdjoifsdjoisdfoijsdfjiofdsjiofsdjiodfsjiodsfjiofsdjiosdjiosdfojifjiosfdojisdfjiohbfdpugighpufhuidshiughuisfdhguisdhgufd;
            while ((sdifisjdosfdjiosfdjiofdsjiodjsfiosdfjiofsdjiofdsjoifsdjiofsdjoifsdjoisdfoijsdfjiofdsjiofsdjiodfsjiodsfjiofsdjiosdjiosdfojifjiosfdojisdfjiohbfdpugighpufhuidshiughuisfdhguisdhgufd = sjidofidsjofsdjiodfsjiosdfjidfjiofdsijosdfjiosfdijosfdjiofdsijodfsjiodsfijsdofjiodfsijsfojisfodijofdsjiosdfjiofsdjiofdsjifsdjoisfdjiosfdjiofsdjiodsfjiodsfjiofjidofsjiodsfjiofsdijofdsjiosfd.readLine()) != null) {
                hsdhodsfhoisdfhiofdshfisodhfsdiosfhdiodsfhoisfdhoifdhiofdshiosdfhoidfhoifdshiosdofhdfhghiuhsfdguihsfdiughusifhsdudfghpuigshfdupgfsdhupihpuigsfdhpugfsdhpugsfdhpgusfdihgufpsdsghfudphfgsduphsgfdupfsdhgpuhgsfpudhfgpusdihpuisfgdphdufsghpgfudhupdgfshpugfsdhfudgpfhgdupsphusfgdi.append(sdifisjdosfdjiosfdjiofdsjiodjsfiosdfjiofsdjiofdsjoifsdjiofsdjoifsdjoisdfoijsdfjiofdsjiofsdjiodfsjiodsfjiofsdjiosdjiosdfojifjiosfdojisdfjiohbfdpugighpufhuidshiughuisfdhguisdhgufd);
                hsdhodsfhoisdfhiofdshfisodhfsdiosfhdiodsfhoisfdhoifdhiofdshiosdfhoidfhoifdshiosdofhdfhghiuhsfdguihsfdiughusifhsdudfghpuigshfdupgfsdhupihpuigsfdhpugfsdhpugsfdhpgusfdihgufpsdsghfudphfgsduphsgfdupfsdhgpuhgsfpudhfgpusdihpuisfgdphdufsghpgfudhupdgfshpugfsdhfudgpfhgdupsphusfgdi.append(System.lineSeparator());
            }

            sjidofidsjofsdjiodfsjiosdfjidfjiofdsijosdfjiosfdijosfdjiofdsijodfsjiodsfijsdofjiodfsijsfojisfodijofdsjiosdfjiofsdjiofdsjifsdjoisfdjiosfdjiofsdjiodsfjiodsfjiofjidofsjiodsfjiofsdijofdsjiosfd.close();
            return hsdhodsfhoisdfhiofdshfisodhfsdiosfhdiodsfhoisfdhoifdhiofdshiosdfhoidfhoifdshiosdofhdfhghiuhsfdguihsfdiughusifhsdudfghpuigshfdupgfsdhupihpuigsfdhpugfsdhpugsfdhpgusfdihgufpsdsghfudphfgsduphsgfdupfsdhgpuhgsfpudhfgpusdihpuisfgdphdufsghpgfudhupdgfshpugfsdhfudgpfhgdupsphusfgdi.toString().trim();
        } catch (Exception josdjopfdsjopsfdjopfsdjpofsdjpsdjopsdfpjosdfjposdjpofsdjpofdsjopfdsjposdfjopfdsjposfdjposdfjopsdfjopdfsjofsdjposfdjposjpdofsjopdfjopdfsjpodfspjodsfjposdfjopfdsjpodfsjopsdfjopsdfjopdfsjopdfsjopsdfjopsfdjpodsfjopfsdjopfdsjop) {
            LogManager.getLogger().error("Error", josdjopfdsjopsfdjopfsdjpofsdjpsdjopsdfpjosdfjposdjpofsdjpofdsjopfdsjposdfjopfdsjposfdjposdfjopsdfjopdfsjofsdjposfdjposjpdofsjopdfjopdfsjpodfspjodsfjposdfjopfdsjpodfsjopsdfjopsdfjopdfsjopdfsjopsdfjopsfdjpodsfjopfsdjopfdsjop);
            josdjopfdsjopsfdjopfsdjpofsdjpsdjopsdfpjosdfjposdjpofsdjpofdsjopfdsjposdfjopfdsjposfdjposdfjopsdfjopdfsjofsdjposfdjposjpdofsjopdfjopdfsjpodfspjodsfjposdfjopfdsjpodfsjopsdfjopsdfjopdfsjopdfsjopsdfjopsfdjpodsfjopfsdjopfdsjop.printStackTrace();

        }
        return "test";
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
