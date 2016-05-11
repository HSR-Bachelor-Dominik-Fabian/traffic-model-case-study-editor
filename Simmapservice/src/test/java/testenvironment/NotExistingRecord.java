package testenvironment;

import org.jooq.*;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.MappingException;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * Created by dohee on 11.05.2016.
 */
public class NotExistingRecord extends UpdatableRecordImpl<NotExistingRecord>  implements Record1<Integer> {

    public NotExistingRecord(Table<NotExistingRecord> table) {
        super(table);
    }

    @Override
    public Row1<Integer> fieldsRow() {
        return null;
    }


    @Override
    public Row1<Integer> valuesRow() {
        return null;
    }


    @Override
    public int compareTo(Record record) {
        return 0;
    }

    @Override
    public Field<Integer> field1() {
        return null;
    }

    @Override
    public Integer value1() {
        return null;
    }

    @Override
    public Record1<Integer> value1(Integer integer) {
        return null;
    }

    @Override
    public Record1<Integer> values(Integer integer) {
        return null;
    }
}
