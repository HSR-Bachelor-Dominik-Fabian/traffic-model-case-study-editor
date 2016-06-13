package testenvironment;

import dataaccess.database.tables.records.LinkRecord;
import org.easymock.IArgumentMatcher;

public class StreamLinkEquals implements IArgumentMatcher {
    LinkRecord[] expected;

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
        stringBuffer.append(expected.toString());
        stringBuffer.append("\")");
    }
}
