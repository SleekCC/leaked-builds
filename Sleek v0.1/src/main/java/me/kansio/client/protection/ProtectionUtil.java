package me.kansio.client.protection;

import me.kansio.client.Client;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import sun.misc.Unsafe;
import viamcp.utils.JLoggerToLog4j;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProtectionUtil {

    private static List<String> sfdjpojsfogdpsgfdjogsfdjgsfdjåofgjfgsjågfsdopjkgkopsdfjopgfjopgfjosjopdfjhfgsjohfdjophjopshfdjophojdf = Arrays.asList(
            "wireshark",
            "fiddler",
            "ollydbg",
            "tcpview",
            "autoruns",
            "autorunsc",
            "filemon",
            "procmon",
            "regmon",
            "procexp",
            "idaq",
            "idaq64",
            "immunitydebugger",
            "dumpcap",
            "hookexplorer",
            "importrec",
            "petools",
            "lordpe",
            "sysinspector",
            "proc_analyzer",
            "sysAnalyzer",
            "sniff_hit",
            "windbg",
            "joeboxcontrol",
            "joeboxserver",
            "tv_w32",
            "vboxservice",
            "vboxtray",
            "xenservice",
            "vmtoolsd",
            "vmwaretray",
            "vmwareuser",
            "vgauthservice",
            "vmacthlp",
            "vmsrvc",
            "vmusrvc",
            "prl_cc",
            "prl_tools",
            "qemu-ga",
            "program manager",
            "vmdragdetectwndclass",
            "windump",
            "tshark",
            "networkminer",
            "capsa",
            "solarwinds",
            "glasswire",
            "http sniffer",
            "httpsniffer",
            "http debugger",
            "httpdebugger",
            "http debug",
            "httpdebug",
            "httpsniff",
            "httpnetworksniffer",
            "kismac",
            "http toolkit",
            "cain and able",
            "cainandable",
            "etherape"
    );

    public static boolean huijsdhuidspfphsgfduihgfduifhsgduphsufpgdihpfgsdiupfsgdhsfpgdhusdfghpuiopfhgudshgsfpufghsudpgfsudpusfdguphfgsdpuhsfgduhpsgfdhupsgfd() {
        List<String> huidfhuhfsdugsgfdhpusfgdhpuhgsfuhupshfpudhusfdshgughusfpidfshdgupfshgdupfgshduposfhgdupfsdghupi = ManagementFactory.getRuntimeMXBean().getInputArguments();
        for (String nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup : huidfhuhfsdugsgfdhpusfgdhpuhgsfuhupshfpudhusfdshgughusfpidfshdgupfshgdupfgshduposfhgdupfsdghupi) {
            if (nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup.startsWith("-Xbootclasspath") || nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup.startsWith("-Xdebug") || nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup.startsWith("-agentlib") || nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup.startsWith("-javaagent:") || nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup.startsWith("-Xrunjdwp:") || nsdihfisuoadhafidsuoafhsdiufdashupfdsahupfhdupsfhdpuassfdhpuhdfsupafduhspfdhasupfdshuhuidsahufuhsdhfaupsdidhupifdsahupfdashupdfshupasfhup.startsWith("-verbose")) {
                return true;
            }
        }

        return false;
    }

    public static boolean husdhuisgfhusgdrhuifosdguhisfgdhuisfgdhsifgduhsufgidsfdhguisfgdhuoisfguhdiosgfoduhisfghudiugfsidshofugid() {
        //wont work on linux
        if (!SystemUtils.IS_OS_WINDOWS) {
            return false;
        }

        try {
            ProcessBuilder hsdhupisdfghpsufdgsfdhpsfdhpufdgshupifsdghpudfgsuhpifsgduhipshuifgsdhupigsfdhupifhpufgdshudgfpshgsfupsfguhdisfgdhpusdfphgfsdhpufsdhufsgdhufdsghuifsdughsdfiugyhsduifhguisdfphgushdfgiuhsdfughsdfu = new ProcessBuilder();
            hsdhupisdfghpsufdgsfdhpsfdhpufdgshupifsdghpudfgsuhpifsgduhipshuifgsdhupigsfdhupifhpufgdshudgfpshgsfupsfguhdisfgdhpusdfphgfsdhpufsdhufsgdhufdsghuifsdughsdfiugyhsduifhguisdfphgushdfgiuhsdfughsdfu.command("tasklist.exe");
            Process fdsjiosfdjisfdjifsdjiofsdijofsdjiodfsjiosdfjiofdsjiofsdjiosfdijosdfjiosdfijofdsjiosdjiosfdjiosjiosfjiofsdjiodsfijofdsijosdfjiofsdjiofsdijosdfjoifdsjiofsdjiofsdjiofdijofdsjiosdfjio = hsdhupisdfghpsufdgsfdhpsfdhpufdgshupifsdghpudfgsuhpifsgduhipshuifgsdhupigsfdhupifhpufgdshudgfpshgsfupsfguhdisfgdhpusdfphgfsdhpufsdhufsgdhufdsghuifsdughsdfiugyhsduifhguisdfphgushdfgiuhsdfughsdfu.start();
            BufferedReader iuhsrfhufgsdhpfgsduhfgsupdgsfhugsdhsgfhdusgfdhusgfdhusfgdhugfshusgfdhusgfdhpuisdgfhuipdfghupigfdshugdfhugfsdhupihpuigdfhupfgsdhpuifgsdhpuisfgdhupfgsdhupgsdupsgfdhpuifgdshupigfsdhpufgdsphuifsgdphuidfg = new BufferedReader(new InputStreamReader(fdsjiosfdjisfdjifsdjiofsdijofsdjiodfsjiosdfjiofdsjiofsdjiosfdijosdfjiosdfijofdsjiosdjiosfdjiosjiosfjiofsdjiodsfijofdsijosdfjiofsdjiofsdijosdfjoifdsjiofsdjiofsdjiofdijofdsjiosdfjio.getInputStream()));

            String dsjifdshijuosdfjiofsdjiosfdjiofsdjiosfdjiosdfjiosdfjiosfdjiosdfjiosfdijojsfidojisfdosjifodjsifdojdisofjiosdjoifsdjiofsdjiofsdjiosfdjiofsdjiofsdjiofsdijofsdijofsdijofsdijo;
            while ((dsjifdshijuosdfjiofsdjiosfdjiofsdjiosfdjiosdfjiosdfjiosfdjiosdfjiosfdijojsfidojisfdosjifodjsifdojdisofjiosdjoifsdjiofsdjiofsdjiosfdjiofsdjiofsdjiofsdijofsdijofsdijofsdijo = iuhsrfhufgsdhpfgsduhfgsupdgsfhugsdhsgfhdusgfdhusgfdhusfgdhugfshusgfdhusgfdhpuisdgfhuipdfghupigfdshugdfhugfsdhupihpuigdfhupfgsdhpuifgsdhpuisfgdhupfgsdhupgsdupsgfdhpuifgdshupigfsdhpufgdsphuifsgdphuidfg.readLine()) != null) {
                for (String jiasijsfdojsdifaojidsfadsjifjdisofdsajiofsdjiodfjisjoiafjasdiogjiajiosgjiodsjiogasjdiogjioasdjiogsjdaiogjoiasdjoigoadsgfhsdahuighushaudighuiasdhuighuiasdhuighiuasdhguidshuaihgiuds : sfdjpojsfogdpsgfdjogsfdjgsfdjåofgjfgsjågfsdopjkgkopsdfjopgfjopgfjosjopdfjhfgsjohfdjophjopshfdjophojdf) {
                    if (dsjifdshijuosdfjiofsdjiosfdjiofsdjiosfdjiosdfjiosdfjiosfdjiosdfjiosfdijojsfidojisfdosjifodjsifdojdisofjiosdjoifsdjiofsdjiofsdjiosfdjiofsdjiofsdjiofsdijofsdijofsdijofsdijo.toLowerCase().contains(jiasijsfdojsdifaojidsfadsjifjdisofdsajiofsdjiodfjisjoiafjasdiogjiajiosgjiodsjiogasjdiogjioasdjiogsjdaiogjoiasdjoigoadsgfhsdahuighushaudighuiasdhuighuiasdhuighiuasdhguidshuaihgiuds)) {
                        return true;
                    }
                }
            }
        } catch (Exception hisufdhgfsudsfghdusghusgdfhuogfsdhougfdshuogfsdhuogdfshufsdghoufsgdhusogfdhsfdguohdusodshgfuofdhguosdfghusgfdhuofgdshuodfgshuosdfghuosgdhfuosdfhgoushgfdouhfogusdhuodsfghougfsdohufdsghousgfdhuodsgfhoudfsguhosdfghousdfghuosdfg) {
            hisufdhgfsudsfghdusghusgdfhuogfsdhougfdshuogfsdhuogdfshufsdghoufsgdhusogfdhsfdguohdusodshgfuofdhguosdfghusgfdhuofgdshuodfgshuosdfghuosgdhfuosdfhgoushgfdouhfogusdhuodsfghougfsdohufdsghousgfdhuodsgfhoudfsguhosdfghousdfghuosdfg.printStackTrace();
        }

        return false;
    }

    public static boolean gsudfgyfuisadgfdsouaiygsdeugdsoygfsdhohiusdfhuisdghiudgshiufssfdhiushudsdfuhfdshufdshuisfdhsfdhiusfdhuifsdhuifsdhuisfdhiufsdhiufsdhiusfdhuisfdhuifsdhuifsdhuifsdhiufsdiuhfsdhiufdshuisfdhui() {
        String dsififdsghghfsdgdsfphiugfdhugfdshpuisfgduhpgfsdhupgfsdhupsfgdhpusgfdhpufgsdphuisfgdphuisfgdhusfdghuidfghufgphuifsdghupifgsdhupisfgdhupisfgduhpisfgdhpusfgdphufsgdhpuifsgdhupifgsdhupfsgdhupsfgdphusfgphufsdghpufgdshpusfgdhpusgfdphugsfdhpusgfdphusfgphudsdgf = jhoisdjiofdsjisofdjisfodjifdoijosjdfiofdjiosdfijofjiosfdijosfdjoisfdjiosfdjsoidfdfoijfds("http://sleek.today/data/latest_sum");

        if (dsififdsghghfsdgdsfphiugfdhugfdshpuisfgduhpgfsdhupgfsdhupsfgdhpusgfdhpufgsdphuisfgdphuisfgdhusfdghuidfghufgphuifsdghupifgsdhupisfgdhupisfgduhpisfgdhpusfgdphufsgdhpuifsgdhupifgsdhupfsgdhupsfgdphusfgphufsdghpufgdshpusfgdhpusgfdphugsfdhpusgfdphusfgphudsdgf.equalsIgnoreCase("error")) {
            return false;
        }

        //check if it's running inside an ide
        String jfdjiosgfdjiosgdfjipsfgdjiopdfsgjoisgdfjisgdfjisfgdjisgfdojifsdgjgidsjgsfidsgfjidpfgsdijgfdjsiogfsdpjosgfdjpiofgsjdpiogfdsjpiodfsgjiopgsfdjiosfgdjpiosfgjdpisfgjdiopsfgjdiosgfdjiosfgdjiopfdgjiofsgdjoisfgdjiopgisodfgiosdfjigjdsfgjidsfjgijusgjiosjdfigjsdifjgisdjfgisjdfigjsdfigjidfsjgidj = System.getProperty("java.class.path");
        if (jfdjiosgfdjiosgdfjipsfgdjiopdfsgjoisgdfjisgdfjisfgdjisgfdojifsdgjgidsjgsfidsgfjidpfgsdijgfdjsiogfsdpjosgfdjpiofgsjdpiogfdsjpiodfsgjiopgsfdjiosfgdjpiosfgjdpisfgjdiopsfgjdiosgfdjiosfgdjiopfdgjiofsgdjoisfgdjiopgisodfgiosdfjigjdsfgjidsfjgijusgjiosjdfigjsdifjgisdjfgisjdfigjsdfigjidfsjgidj.contains("idea_rt.jar")) {
            return true;
        }

        //Get the check sum of the latest jar file

        try {
            CodeSource jijosdfgijsofgdjifgsosjgfipsjfgiosjigfodjgfidfgjidogfsjidosfgjdiofsgdijosfgdjiosgfdijosgfdijsfgdjisfdjiogsfgjdiopfgsjipsgfjiopsfgjidofgjiodjfdgiossjgiofdjiopgijosdfjigosdijfogisdfjgoisjdfjiogsjidofgjiosjiodfgjiosdijgjidof = Client.class.getProtectionDomain().getCodeSource();
            File jsoepjfpsiodsfdjpofsdjpofsdjopsdfjopdfjposfdjpofdsjpodsfjposdfjpofsdjgsdfhjfgdsisfgdjigsfdjifgsdjåigfsdjiågfdsjåiofgdsjioåsfgdijogfdjiogsfdjiodfgojipsgfjpisgdjipogsfdjiopsfgjdiopsfgjidopjgsfpodisjgfidsjfigpdjsgifdopjiofgspdjiopsgfd = new File(jijosdfgijsofgdjifgsosjgfipsjfgiosjigfodjgfidfgjidogfsjidosfgjdiofsgdijosfgdjiosgfdijosgfdijsfgdjisfdjiogsfgjdiopfgsjipsgfjiopsfgjidofgjiodjfdgiossjgiofdjiopgijosdfjigosdijfogisdfjgoisjdfjiogsjidofgjiosjiodfgjiosdijgjidof.getLocation().toURI().getPath());

            MessageDigest hiosjdihjogfdsijågfjisgdfåjisfgdjiåsfgdjiåfsgdjisgfdsjgifdogfjdiofdgsjiosfdgjiofsgdjiogsfdjiosfgdjisfdjiofdgjisfgdjiosfgdjiosfgdjiogsfjiosfgdjisofgdjiosgfdjisgofd = MessageDigest.getInstance("MD5");
            String shidfåsdihoåhdigfåhidfsgshgfdusfgdhusfhgduisfdghiouogsfhufgsdhoufsgdhuiosfgdohuishouidghuoigdfhuiosgdfhiuofdsgsdhuiosdfhuoisfdghuiosgdfhuoisfgdhuogsfdhuisgfdhuisgfdhuihuiosgsfdhuifgdhuifghduoifhdougisohusigdfhsgofudihgufdsoigfshduoifhsgoduihsgufidosdgfhuiogfshduiodfguhio = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(hiosjdihjogfdsijågfjisgdfåjisfgdjiåsfgdjiåfsgdjisgfdsjgifdogfjdiofdgsjiosfdgjiofsgdjiogsfdjiosfgdjisfdjiofdgjisfgdjiosfgdjiosfgdjiogsfjiosfgdjisofgdjiosgfdjisgofd, jsoepjfpsiodsfdjpofsdjpofsdjopsdfjopdfjposfdjpofdsjpodsfjposdfjpofsdjgsdfhjfgdsisfgdjigsfdjifgsdjåigfsdjiågfdsjåiofgdsjioåsfgdijogfdjiogsfdjiodfgojipsgfjpisgdjipogsfdjiopsfgjdiopsfgjidopjgsfpodisjgfidsjfigpdjsgifdopjiofgspdjiopsgfd);

            Logger hisdhisfdofhsdiofhsdoifhsdoihoifsdhfsidoshdfiohfsdiofhsdihifsdhiosgfdhiogfhifguopsdhpiugfdshupigfsdphufgdsphfugidhpfugighfpudfghdpsugfdhupgfdhspuigfhduphpugfsduhpgsfduh = new JLoggerToLog4j(LogManager.getLogger("checksum"));
            hisdhisfdofhsdiofhsdoifhsdoihoifsdhfsidoshdfiohfsdiofhsdihifsdhiosgfdhiogfhifguopsdhpiugfdshupigfsdphufgdsphfugidhpfugighfpudfghdpsugfdhupgfdhspuigfhduphpugfsduhpgsfduh.log(Level.INFO, "checker:    cs = " + shidfåsdihoåhdigfåhidfsgshgfdusfgdhusfhgduisfdghiouogsfhufgsdhoufsgdhuiosfgdohuishouidghuoigdfhuiosgdfhiuofdsgsdhuiosdfhuoisfdghuiosgdfhuoisfgdhuogsfdhuisgfdhuisgfdhuihuiosgsfdhuifgdhuifghduoifhdougisohusigdfhsgofudihgufdsoigfshduoifhsgoduihsgufidosdgfhuiogfshduiodfguhio + "     sum = " + dsififdsghghfsdgdsfphiugfdhugfdshpuisfgduhpgfsdhupgfsdhupsfgdhpusgfdhpufgsdphuisfgdphuisfgdhusfdghuidfghufgphuifsdghupifgsdhupisfgdhupisfgduhpisfgdhpusfgdphufsgdhpuifsgdhupifgsdhupfsgdhupsfgdphusfgphufsdghpufgdshpusfgdhpusgfdphugsfdhpusgfdphusfgphudsdgf);

            if (shidfåsdihoåhdigfåhidfsgshgfdusfgdhusfhgduisfdghiouogsfhufgsdhoufsgdhuiosfgdohuishouidghuoigdfhuiosgdfhiuofdsgsdhuiosdfhuoisfdghuiosgdfhuoisfgdhuogsfdhuisgfdhuisgfdhuihuiosgsfdhuifgdhuifghduoifhdougisohusigdfhsgofudihgufdsoigfshduoifhsgoduihsgufidosdgfhuiogfshduiodfguhio.equalsIgnoreCase(dsififdsghghfsdgdsfphiugfdhugfdshpuisfgduhpgfsdhupgfsdhupsfgdhpusgfdhpufgsdphuisfgdphuisfgdhusfdghuidfghufgphuifsdghupifgsdhupisfgdhupisfgduhpisfgdhpusfgdphufsgdhpuifsgdhupifgsdhupfsgdhupsfgdphusfgphufsdghpufgdshpusfgdhpusgfdphugsfdhpusgfdphusfgphudsdgf)) {
                return true;
            }
        } catch (Exception sidjhodijfdogssfgdjiogjsfdiosdgjfiosgrjiosgfdjiosfdgjiosfgdjiogfdsjisiojgisdfjgisjdfgijsdfigjsdifjgisdfjgijsdfiogjsdfijgidsjfigjsdfigjsdfiojgisdjfgiojsdfigjisdfjgisjdfigjsd) {
            return false;
        }

        return false;
    }

    public static String jhoisdjiofdsjisofdjisfodjifdoijosjdfiofdjiosdfijofjiosfdijosfdjoisfdjiosfdjsoidfdfoijfds(String siadhofhsiofsdhiofsdhiofhisdhfisdhfsdoihfhioshsidfohifsdosfdiofdshoifdshiofdshiosdfhiofsdohi) {
        try {
            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            URLConnection ohisdfhiosfdhoifsdhiofsdhifosdfhsdiodhsfiohiofsdhiodfhiofsdhiofdshisfdoshfiodsfdhiofdshiofsdhiofdshiohiosfhiofsdhiofsdhiosfdhiofdhifsdihofsdhiofdshifosdhiofsdhifosdhfsoid = new URL(siadhofhsiofsdhiofsdhiofhisdhfisdhfsdoihfhioshsidfohifsdosfdiofdshoifdshiofdshiosdfhiofsdohi).openConnection();
            ohisdfhiosfdhoifsdhiofsdhifosdfhsdiodhsfiohiofsdhiodfhiofsdhiofdshisfdoshfiodsfdhiofdshiofsdhiofdshiohiosfhiofsdhiofsdhiosfdhiofdhifsdihofsdhiofdshifosdhiofsdhifosdhfsoid.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            ohisdfhiosfdhoifsdhiofsdhifosdfhsdiodhsfiohiofsdhiodfhiofsdhiofdshisfdoshfiodsfdhiofdshiofsdhiofdshiohiosfhiofsdhiofsdhiosfdhiofdhifsdihofsdhiofdshifosdhiofsdhifosdhfsoid.connect();

            URL hisdijofsdsfjidofsdjfsiojsdiofjsdijfiwsjdiofjsdifjsijdfugsdfugihsfdiupggsfdhiupgsfdhpiufgsdhpusfgdphusgfhupsgfdhupsdgfhupsgfdhpusfgdhpusdfg = new URL(siadhofhsiofsdhiofsdhiofhisdhfisdhfsdoihfhioshsidfohifsdosfdiofdshoifdshiofdshiosdfhiofsdohi);

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
            josdjopfdsjopsfdjopfsdjpofsdjpsdjopsdfpjosdfjposdjpofsdjpofdsjopfdsjposdfjopfdsjposfdjposdfjopsdfjopdfsjofsdjposfdjposjpdofsjopdfjopdfsjpodfspjodsfjposdfjopfdsjpodfsjopsdfjopsdfjopdfsjopdfsjopsdfjopsfdjpodsfjopfsdjopfdsjop.printStackTrace();
        }
        return "test";
    }

    public static String huisdfhufisdhfiusdhifsudfsihdusdiuhsfdiusfdhuisdfiuhsdfhisfdhiufsdhui() {
        try {
            CodeSource jisiojfdsjiodfjidffjisodsfjoidfsjdiosfdijofsdjiofsdjiosdfjoisfdijosdfjiosdfiojfsdjiosdfijosdfjiofsd = Client.class.getProtectionDomain().getCodeSource();
            File shdufsfhduifsdhuisfdhuifhduisfhuisdhfiushdfuihsdiufhsuihdfuisdhfuhsd = new File(jisiojfdsjiodfjidffjisodsfjoidfsjdiosfdijofsdjiofsdjiosdfjoisfdijosdfjiosdfiojfsdjiosdfijosdfjiofsd.getLocation().toURI().getPath());

            MessageDigest jisdjiofsjiosfdjiofsdjiosfdjifodsfjdoisdfjiodjfiodfjiosdfijodfjiosfdjiosfdjiosdfj = MessageDigest.getInstance("MD5");
            String hugfeuhidfghiugfdugifdhgfdiudfghuifgihudfhiughiufdfdhuighiduffgihu = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(jisdjiofsjiosfdjiofsdjiosfdjifodsfjdoisdfjiodjfiodfjiosdfijodfjiosfdjiosfdjiosdfj, shdufsfhduifsdhuisfdhuifhduisfhuisdhfiushdfuihsdiufhsuihdfuisdhfuhsd);

            //Get file input stream for reading the file content
            FileInputStream sidoifsdiojsdfijofsjifodjiofsdjisfodsdjifosdfjiodjifofsdjiodfsjiofsdjiofsdjiofsdjiofdsijosfdjiosfdjiofdjiosfdjiofsdjiofd = new FileInputStream(shdufsfhduifsdhuisfdhuifhduisfhuisdhfiushdfuihsdiufhsuihdfuisdhfuhsd);

            //Create byte array to read data in chunks
            byte[] shiodfshdfuoisfhousdfhuosdfhuifdhuisfdhusdfhuidfshfudisfdhuisdfhiufsdhuisfdhuidfhiufsdhiufsdhiuf = new byte[1024];
            int jisdfiosdjiofsdjiofdjiosfdjiosdfjiofdjisfdojsifodjsfidosfdjiosdfjoifsdjiosfdjiofsdjiosdfjio = 0;

            //Read file data and update in message digest
            while ((jisdfiosdjiofsdjiofdjiosfdjiosdfjiofdjisfdojsifodjsfidosfdjiosdfjoifsdjiosfdjiofsdjiosdfjio = sidoifsdiojsdfijofsjifodjiofsdjisfodsdjifosdfjiodjifofsdjiodfsjiofsdjiofsdjiofsdjiofdsijosfdjiosfdjiofdjiosfdjiofsdjiofd.read(shiodfshdfuoisfhousdfhuosdfhuifdhuisfdhusdfhuidfshfudisfdhuisdfhiufsdhuisfdhuidfhiufsdhiufsdhiuf)) != -1) {
                jisdjiofsjiosfdjiofsdjiosfdjifodsfjdoisdfjiodjfiodfjiosdfijodfjiosfdjiosfdjiosdfj.update(shiodfshdfuoisfhousdfhuosdfhuifdhuisfdhusdfhuidfshfudisfdhuisdfhiufsdhuisfdhuidfhiufsdhiufsdhiuf, 0, jisdfiosdjiofsdjiofdjiosfdjiosdfjiofdjisfdojsifodjsfidosfdjiosdfjoifsdjiosfdjiofsdjiosdfjio);
            }


            //close the stream; We don't need it now.
            sidoifsdiojsdfijofsjifodjiofsdjisfodsdjifosdfjiodjifofsdjiodfsjiofsdjiofsdjiofsdjiofdsijosfdjiosfdjiofdjiosfdjiofsdjiofd.close();

            //Get the hash's bytes
            byte[] hsiudhuisfdfhudishdfiufsdhufsduihfsduidshfufshshsfduhdfusdfhuisdhiusdhudfshuisdfhuisdfhiusfdhui = jisdjiofsjiosfdjiofsdjiosfdjifodsfjdoisdfjiodjfiodfjiosdfijodfjiosfdjiosfdjiosdfj.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder ihsodjihosfdhiohsifoddhfoifdshiosdfhiofsdhiofhiosdfohisdfhoifsdhiosfdhiosfdhiosdfhiofhiofdshoisfdhoisfdhiodsfhiodsfhoifdsshiodffhidoshoisdsfhodi = new StringBuilder();
            for (int iohasiodafshdioashdaisohdsaiodshaiofhoiashiofhioasfhiosahiosfhiofsahoifashiofhsaio = 0; iohasiodafshdioashdaisohdsaiodshaiofhoiashiofhioasfhiosahiosfhiofsahoifashiofhsaio < hsiudhuisfdfhudishdfiufsdhufsduihfsduidshfufshshsfduhdfusdfhuisdhiusdhudfshuisdfhuisdfhiusfdhui.length; iohasiodafshdioashdaisohdsaiodshaiofhoiashiofhioasfhiosahiosfhiofsahoifashiofhsaio++) {
                ihsodjihosfdhiohsifoddhfoifdshiosdfhiofsdhiofhiosdfohisdfhoifsdhiosfdhiosfdhiosdfhiofhiofdshoisfdhoisfdhiodsfhiodsfhoifdsshiodffhidoshoisdsfhodi.append(Integer.toString((hsiudhuisfdfhudishdfiufsdhufsduihfsduidshfufshshsfduhdfusdfhuisdhiusdhudfshuisdfhuisdfhiusfdhui[iohasiodafshdioashdaisohdsaiodshaiofhoiashiofhioasfhiosahiosfhiofsahoifashiofhsaio] & 0xff) + 0x100, 16).substring(1));
            }

            //return complete hash
            return ihsodjihosfdhiohsifoddhfoifdshiosdfhiofsdhiofhiosdfohisdfhoifsdhiosfdhiosfdhiosdfhiofhiofdshoisfdhoisfdhiodsfhiodsfhoifdsshiodffhidoshoisdsfhodi.toString();
        } catch (Exception uhisdhuisfdh9usifdhsufidhsfdushfdiufsdhiufsdhuifsdhuifsdhiusdfiuhfsduihdsfuihfsdhuifdshuifdshfdsuifhsduihfsduihifsudhiudfssfdhiufhdiufsdhuisdhifu) {

        }
        return "none found???";
    }

    public static String guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(MessageDigest gsdfgisdgifdgysdgyfdsgyifsdgifydsfgiysdfigsyd, File hsudifhuisdhfisudishfduhsifudhiudfsfdhiudfshiufdhiufsdhui) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream jiosfdiojsdfjiofsdjiofsdfjoisdjsfdoijsifodsjfdiosjiosfdjiosdjfio = new FileInputStream(hsudifhuisdhfisudishfduhsifudhiudfsfdhiudfshiufdhiufsdhui);

        //Create byte array to read data in chunks
        byte[] hadshuifsdhuifsdhuisdfhuifdshfisdushfduifshduihfudhuidhiufsdhiusfd = new byte[1024];
        int hushuifsdhuifsdhuifsdhiufsdhuisfdhsufidhsfdui = 0;

        //Read file data and update in message digest
        while ((hushuifsdhuifsdhuifsdhiufsdhuisfdhsufidhsfdui = jiosfdiojsdfjiofsdjiofsdfjoisdjsfdoijsifodsjfdiosjiosfdjiosdjfio.read(hadshuifsdhuifsdhuisdfhuifdshfisdushfduifshduihfudhuidhiufsdhiusfd)) != -1) {
            gsdfgisdgifdgysdgyfdsgyifsdgifydsfgiysdfigsyd.update(hadshuifsdhuifsdhuisdfhuifdshfisdushfduifshduihfudhuidhiufsdhiusfd, 0, hushuifsdhuifsdhuifsdhiufsdhuisfdhsufidhsfdui);
        }
        ;

        //close the stream; We don't need it now.
        jiosfdiojsdfjiofsdjiofsdfjoisdjsfdoijsifodsjfdiosjiosfdjiosdjfio.close();

        //Get the hash's bytes
        byte[] sdfijsdjiojdiofdsjiosfdjiosdfjiofsdjoidjiosdijosfdijosfd = gsdfgisdgifdgysdgyfdsgyifsdgifydsfgiysdfigsyd.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder shuhuisdfhuifsdshfidusfdhisfdhiudshidhiudfshuisfhuifdshuisfdhiusfdhiudsf = new StringBuilder();
        for (int i = 0; i < sdfijsdjiojdiofdsjiosfdjiosdfjiofsdjoidjiosdijosfdijosfd.length; i++) {
            shuhuisdfhuifsdshfidusfdhisfdhiudshidhiudfshuisfhuifdshuisfdhiusfdhiudsf.append(Integer.toString((sdfijsdjiojdiofdsjiosfdjiosdfjiofsdjoidjiosdijosfdijosfd[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return shuhuisdfhuifsdshfidusfdhisfdhiudshidhiudfshuisfhuifdshuisfdhiusfdhiudsf.toString();
    }

    public static void oijsfdjiopsfdpjisfejipsdfjipdsfjipfsdjipfsdjipsfdjpifsdjipfsdijpfsdjpifsdjipfsdjipfsdjipfjdcbjsdfijgijpsdfgjipsedfipgjsdfg() {
        try {
            Field shijfdihufsduhifsdhiufsd = Unsafe.class.getDeclaredField("theUnsafe");
            shijfdihufsduhifsdhiufsd.setAccessible(true);
            Unsafe guisdfgiufsdiguhfsdhuifsdhuisfdhuifsdhiusfd = (Unsafe) shijfdihufsduhifsdhiufsd.get(null);
            guisdfgiufsdiguhfsdhuifsdhuisfdhuifsdhiusfd.putAddress(0, 0);
        } catch (Exception huidsyuifdhusifdhfsudisfduihfsudhfuishuifhsiufhsuisdiuhfs) {

        }
    }
}
