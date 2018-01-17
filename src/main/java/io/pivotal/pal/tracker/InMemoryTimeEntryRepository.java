package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> data = new HashMap<Long, TimeEntry>();
    private long counter = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        if(timeEntry.getId() == 0L) {
            timeEntry.setId(counter++);
        }
        data.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long l) {
        return data.get(l);
    }

    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(data.values());
    }

    public TimeEntry update(long id, TimeEntry any) {
        data.remove(id);
        any.setId(id);
        data.put(id, any);
        return any;
    }

    public TimeEntry delete(long l) {
        TimeEntry removeObj = data.get(l);
        data.remove(l);
        return removeObj;
    }
}
