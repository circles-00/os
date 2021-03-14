import java.io.File;
import java.io.FileNotFoundException;

public class BiggestDocinFolder {
    public static final String filePath = "D:\\tmp";

    public static long getFileSize(File f) throws FileNotFoundException {
        if (!f.exists())
            throw new FileNotFoundException();
        return f.length();
    }

    public static File[] getFilesInFolder(File dir) throws FileNotFoundException {
        if (!dir.exists())
            throw new FileNotFoundException();
        if (!dir.isDirectory())
            throw new FileNotFoundException();
        return dir.listFiles();
    }

    public static void main(String[] args) throws FileNotFoundException {
        File dir = new File(filePath);
        File[] files = getFilesInFolder(dir);
        long max = 0;
        File biggestFile = null;
        for (File f : files) {
            if (f.isDirectory())
                continue;
            if (getFileSize(f) > max) {
                max = getFileSize(f);
                biggestFile = f;
            }
        }
        System.out.println(biggestFile.getName());
    }
}
