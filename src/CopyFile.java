import static java.nio.file.FileVisitResult.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;
import static java.nio.file.StandardCopyOption.*;

public class CopyFile {
    static Integer teller=0;
    static HashMap hm = new HashMap();
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

            pathStore.printAlleNavn();
        } catch(java.io.IOException e) {
            System.out.println("KOPIERING AV FILER  failed:" + e);
        }
    }

    static class PathStore {

        void printAlleNavn() {

            // kopier filer
            // Get a set of the entries
            Set set = hm.entrySet();

            // Get an iterator
            Iterator i = set.iterator();

            // Display elements
            System.out.println("Hashmap listing");
            Integer loopTeller = 0;
            while(i.hasNext() && loopTeller++ < maxAntall ) {
                Map.Entry me = (Map.Entry) i.next();
                System.out.print("key: " + me.getKey() + ": ");
                System.out.println(" value:" + me.getValue());

                String inFile = me.getValue().toString();
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

        void lagreNavn (Path path) {
            String filNavn = path.toString().toLowerCase();

            if (filNavn.endsWith(".jpg")) {
                System.out.println(teller + " skrivNavn" + filNavn);
                hm.put(teller++, filNavn);
             }
        }
    }
}

