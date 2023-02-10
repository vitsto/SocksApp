package pro.sky.socksapp.service;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String json);

    boolean saveToLog(String json);

    String readFromFile();

    Path createTempFile(String suffix);

    boolean cleanDataFile();

    File getDataFile();
}
