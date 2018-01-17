package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {

        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(timeEntryRepository.create(timeEntry), HttpStatus.CREATED);
        return responseEntity;

    }
    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if(timeEntry == null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }

    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);

        return responseEntity;

    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(id, expected);
        if(timeEntry == null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        return new ResponseEntity<TimeEntry>(timeEntryRepository.delete(id), HttpStatus.NO_CONTENT);
    }
}
