package pl.edu.agh.eaiib.web.sensor;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.common.MomentValue;
import pl.edu.agh.eaiib.web.util.DownloadFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
public abstract class AbstractHistoricalController<T> {

    private Logger log = LoggerFactory.getLogger(AbstractHistoricalController.class);

    @RequestMapping(value = "/{day}",
            method = GET)
    public List<T> readByDay(
            @PathVariable
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate day) {
        return getBo().historyByDay(day);
    }

    @RequestMapping(value = "/csv/{day}",
            method = GET)
    public void printCsv(
            @PathVariable
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate day, HttpServletResponse response) {
        @SuppressWarnings("unchecked")
        ColumnPositionMappingStrategy<MomentValue> strategy = new ColumnPositionMappingStrategy();
        strategy.setColumnMapping("Moment", "Value");

        List<String[]> linesByDay = getBo().historyLineByDay(day);

        StringWriter stringWriter = new StringWriter();
        CSVWriter writer = new CSVWriter(stringWriter);
        writer.writeAll(linesByDay);
        writer.flushQuietly();

        DownloadFile
                .handleDownloadAction("report.csv", new ByteArrayInputStream(stringWriter.toString().getBytes()),
                        log, "application/csv", response);
    }

    protected abstract SensorValueHistoryBo<T> getBo();
}
