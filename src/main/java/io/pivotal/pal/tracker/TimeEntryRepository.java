package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry any) throws SQLException;

    public TimeEntry find(long l) ;

    public List<TimeEntry> list() ;

    public TimeEntry update(long eq, TimeEntry any) ;

    public TimeEntry delete(long l) ;
}
