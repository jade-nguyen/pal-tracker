package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               CounterService counter,
                               GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) throws SQLException {

        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id)  {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if(timeEntry == null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }
        else {
            counter.increment("TimeEntry.read");
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }

    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
        counter.increment("TimeEntry.listed");
        return responseEntity;

    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(id, expected);
        if(timeEntry == null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }
        else {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
