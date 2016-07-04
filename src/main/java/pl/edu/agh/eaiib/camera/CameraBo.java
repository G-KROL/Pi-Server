package pl.edu.agh.eaiib.camera;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class CameraBo {

    private static final Logger log = LoggerFactory.getLogger(CameraBo.class);

    @Value("${app.movement.shots.dir}")
    private String mainDir;

    @Value("${app.movement.shots.dir.pattern}")
    private String subDirPattern;

    /***
     * @return taken shot name
     * @throws IllegalStateException when some problem occurred
     */
    public String takeShot(String shotName) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Path mainPath = Paths.get(mainDir);
            if (Files.isDirectory(mainPath)) {
                String subDirName = LocalDate.now().toString(subDirPattern);
                Path subDirPath = Paths.get(subDirName);
                Path fullDatePath = mainPath.resolve(subDirPath);
                if (!Files.isDirectory(fullDatePath)) {
                    Files.createDirectory(fullDatePath);
                }
                String fileName = shotName + ".jpg" ;
                runtime.exec("raspistill -vf -hf -o " + fullDatePath.toUri().getPath() + fileName);
                Path afterSaving = fullDatePath.resolve(fileName);
                log.debug("Shot saved under {}", afterSaving);
                return mainPath.relativize(afterSaving).toString();
            } else {
                throw new IllegalStateException(String.format("Can't access mainDirectory %s", mainDir));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Camera capturing exception occurred", e);
        }
    }
}
