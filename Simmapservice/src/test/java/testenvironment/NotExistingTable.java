package testenvironment;

import org.jooq.impl.TableImpl;

/**
 * Created by dohee on 11.05.2016.
 */
public class NotExistingTable extends TableImpl<NotExistingRecord> {
    public NotExistingTable(String name) {
        super(name);
    }
}
