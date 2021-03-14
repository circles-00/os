import java.io.*;

public class VowelsInFile {

    public static final String src = "D:\\tmp\\in.txt";
    public static final String dest = "D:\\tmp\\out.txt";


    public static void findWriteNumberofVowels(File src, File dest) throws IOException {
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            in = new BufferedReader(new FileReader(src));
            out = new BufferedWriter(new FileWriter(dest));
            String line = null;
            String[] chars = null;


            while ((line = in.readLine()) != null) {
                int numberOfVowels = 0;
                chars = line.split("");
                for (String c : chars) {
                    if (c.equals("A") || c.equals("E") || c.equals("I") || c.equals("O") || c.equals("U")
                    || c.equals("a") || c.equals("e") || c.equals("i") || c.equals("o") || c.equals("u"))
                        numberOfVowels++;
                }
                out.write(numberOfVowels + "\n");
            }

        } catch (IOException ex) {
            System.out.println("IOException thrown at: findWriteNumberofVowels");
        } finally {
            if (in != null)
                in.close();

            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File srcFile = new File(src);
        File destFile = new File(dest);

        findWriteNumberofVowels(srcFile, destFile);
    }
}
