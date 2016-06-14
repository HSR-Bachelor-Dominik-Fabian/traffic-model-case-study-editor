package testenvironment;

import dataaccess.database.tables.records.LinkRecord;
import org.easymock.IArgumentMatcher;

import java.util.Arrays;

class StreamLinkEquals implements IArgumentMatcher {
    private final LinkRecord[] expected;

    public StreamLinkEquals(LinkRecord[] expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object o) {
        return o instanceof LinkRecord[] && TestDataUtil.matchStreamLinkToLinks(expected, (LinkRecord[]) o);
    }

    @Override
    public void appendTo(StringBuffer stringBuffer) {
        stringBuffer.append("StreamLinkEq(");
        stringBuffer.append(expected.getClass().getName());
        stringBuffer.append(" with content \"");
        stringBuffer.append(Arrays.toString(expected));
        stringBuffer.append("\")");
    }
}
