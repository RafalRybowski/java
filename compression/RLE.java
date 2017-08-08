import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RLE {

    static String pathg;
    static String paths;
    static FileReader fileg;
    static FileWriter files;

    public static void kompresion() throws IOException {
        fileg = new FileReader(pathg);
        files = new FileWriter(paths);
        int counter = 0;
        int a;
        char tmp;
        if((a = fileg.read()) != -1) {
            tmp = (char) a;
            counter++;
        }
        else {
            System.out.println("FILE IS NULL");
            return;
        }
        while ((a = fileg.read()) != -1) {
                if((char)a == tmp) {
                    counter++;
                }
                else {
                    if(counter<=3) {
                        for(int i=0;i<counter;i++)
                            files.write(tmp);
                    }
                    else{
                        files.write(String.valueOf(counter));
                        files.write(tmp);
                    }
                    tmp=(char)a;
                    counter =1;
                }
             }
        if(counter<=3) {
            for(int i=0;i<counter;i++)
                files.write(tmp);
        }
        else{
            files.write(String.valueOf(counter));
            files.write(tmp);
        }
        fileg.close();
        files.close();
    }
    
    public static void decompresion() throws IOException {
        fileg = new FileReader(pathg);
        files = new FileWriter(paths);
        int a;
        int l = 1;
        int tmp = 0;
        while ((a = fileg.read()) != -1) {
            if ((char) a >= '0' && (char) a <= '9') {
                tmp *= l;
                tmp += Character.getNumericValue(a);
                l *= 10;
            } else {
                if (tmp == 0) {
                    files.write(a);
                } else {
                    for (int i = 0; i < tmp; i++)
                        files.write(a);
                    tmp = 0;
                    l = 1;
                }
            }

        }
        fileg.close();
        files.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("give path file to compres");
        Scanner get = new Scanner(System.in);
        pathg = get.next();
        if (Files.exists(Paths.get(pathg))) {
            System.out.println("give path file to save");
            paths = get.next();
            kompresion();
        }
        else{
            System.out.println("NULL OF FILE!!");
        }
    }

}
