import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class FileExistsException extends Exception {}

public class CreateTxtRec {
    public static final String filePath = "D:\\tmp";

    public static void createFile(File currDir) throws IOException, FileExistsException {
        if (!currDir.exists())
            throw new FileNotFoundException();
        if(!currDir.isDirectory())
            throw new FileNotFoundException();

        File txt = new File(currDir.getAbsolutePath() + "\\" + currDir.getName() + ".txt");
        if(txt.exists())
            throw new FileExistsException();
        txt.createNewFile();

        File [] files = currDir.listFiles();

        for(File f : files){
            if(!f.isDirectory())
                continue;
            createFile(f);
        }
    }

    public static void main(String[] args) throws IOException, FileExistsException {
        File dir = new File(filePath);
        createFile(dir);
        System.out.println("Files successfully created");
    }
}
