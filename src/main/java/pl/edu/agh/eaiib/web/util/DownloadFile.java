package pl.edu.agh.eaiib.web.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public final class DownloadFile {

    private DownloadFile() {
    }

    public static void handleDownloadAction(
            String fileName,
            InputStream is,
            Logger log,
            String contentType,
            HttpServletResponse response) {
        try {
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            is.close();
        } catch (NullPointerException npex) {
            log.error("Content of the log file is null " + fileName, npex);
        } catch (IOException ex) {
            log.error("Error while creating a log file: " + fileName, ex);
        }
    }
}
