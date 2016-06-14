package testenvironment;

import org.jooq.impl.TableImpl;

public class NotExistingTable extends TableImpl<NotExistingRecord> {
    public NotExistingTable(String name) {
        super(name);
    }
}
