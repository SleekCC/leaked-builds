package me.kansio.client.manager;

import me.kansio.client.Client;
import me.kansio.client.utils.Util;
import me.kansio.client.utils.chat.ChatUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Kansio
 */
public class KillsultManager extends Util {

    private ArrayList<String> killSults = new ArrayList<>();

    final List<String> defaultMessages = Arrays.asList(
            "You got sleeked L",
            "Sleek is just better...",
            "Verus got killed by Sleek",
            "You just got absolutely raped by Sleek :)",
            "Sleek too op I guess",
            "You got killed by %user% (uid: %uid%) using Sleek hake",
            "We do be doing slight amounts of trolling using Sleek",
            "me and da sleek bois destroying blocksmc",
            "sussy among us sleek hack???",
            "mad? rage at me on discord: %discord%",
            "got angry? rage at me on discord: %discord%",
            "rage at me on discord: %discord%",
            "mad? rage at me on discord: %discord%",
            "like da hack? .gg/GUauVwtFKj",
            "hack too good? get it here: .gg/GUauVwtFKj"
    );

    public ArrayList<String> getKillSults() {
        return killSults;
    }

    public void reload() {
        killSults.clear();
        readKillSults();
    }

    public void readKillSults() {
        File killsultsFile = new File(mc.mcDataDir, File.separator + "Sleek" + File.separator +  "killsults.txt");

        try {
            //if the file doesn't exist just create it
            if (!killsultsFile.exists()) {
                killsultsFile.createNewFile();

                BufferedWriter writer = new BufferedWriter(new FileWriter(killsultsFile));
                writer.write("// You can customize the killsults in this file");
                writer.newLine();
                writer.write("// Placeholders: ");
                writer.newLine();
                writer.write("// %discord% - Displays your discord");
                writer.newLine();
                writer.write("// %username% - Displays your client username");
                writer.newLine();
                writer.write("// %killed% - Displays the name of the person you killed");
                writer.newLine();
                writer.write("// %uid% - Displays your uid");
                writer.newLine();
                writer.write(" ");
                writer.newLine();
                for (String ks : defaultMessages) {
                    writer.write(ks);
                    writer.newLine();
                }
                writer.close();
            }

            //read from the file
            Scanner scanner = new Scanner(killsultsFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();

                //allow comments in the file
                if (data.startsWith("//"))
                    continue;

                if (data.startsWith(" "))
                    continue;

                System.out.println("Found: " + data);
                killSults.add(data);
            }
            scanner.close();
        } catch (Exception e) {
            ChatUtil.log("There was an error whilst reading the killsults.");
            ChatUtil.log(e.getMessage());
        }

    }

}
