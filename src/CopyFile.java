import static java.nio.file.FileVisitResult.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;
import static java.nio.file.StandardCopyOption.*;

public class CopyFile {
    static Integer teller=0;

    static String sourcePath ="S";
    static String targetPath ="T";
    static Integer maxAntall =1;
    static Integer maxBytes=2;

    public static void main(String args[]) {
        System.out.println("Hei");

        // Pars input
        if (args.length > 0)
            sourcePath = args[0].toLowerCase();
        if (args.length > 1)
            targetPath = args[1].toLowerCase();
        if (args.length > 2)
            maxAntall = Integer.parseInt((args[2]));
        if (args.length > 3)
            maxBytes = Integer.parseInt((args[3]));

        System.out.println("sourcePath:" + sourcePath + " targetPath:" + targetPath + " maxAntall:" + maxAntall + " maxBytes:" + maxBytes);

        //Usage:
        PathStore pathStore = new PathStore();
        try {
            Files.walk(Paths.get(sourcePath))
                    .filter(Files::isRegularFile)
                    .forEach(pathStore::lagreNavn);

            pathStore.kopierAlleFiler();
        } catch(java.io.IOException e) {
            System.out.println("KOPIERING AV FILER  failed:" + e);
        }
    }

    static class PathStore {
        static HashMap<Integer, String> hm = new HashMap<Integer, String>();

        void lagreNavn (Path path) {
            String filNavn = path.toString().toLowerCase();

            if (filNavn.endsWith(".jpg")) {
                System.out.println(teller + " skrivNavn" + filNavn);
                hm.put(teller++, filNavn);
            }
        }

        void kopierAlleFiler() {

            //String filNavn = hm.get(2);
            Integer mapSize = hm.size();

            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            Integer randomNum;
           /* randomNum = rand.nextInt(mapSize);
            randomNum = rand.nextInt(mapSize);
            randomNum = rand.nextInt(mapSize);

            // kopier filer
            // Get a set of the entries
            Set set = hm.entrySet();

            // Get an iterator
            Iterator i = set.iterator();

            // Display elements*/
            System.out.println("Hashmap listing");
            Integer loopTeller = 0;
            while( loopTeller++ < maxAntall ) {

                randomNum = rand.nextInt(mapSize);
                String inFile = hm.get(randomNum);
                Path src = Paths.get(inFile);

                String outFile = targetPath + "\\" + src.getFileName().toString();
                Path dst = Paths.get(outFile);

                System.out.println("inFile:" + inFile + " outFile:" + outFile + " sourcePath:" + sourcePath + " targetPath:" + targetPath);

                try {
                    Files.copy(src, dst, REPLACE_EXISTING);
                } catch (java.io.IOException e) {
                    System.out.println("createDirectory failed:" + e);
                }
            }
        }


    }
}

