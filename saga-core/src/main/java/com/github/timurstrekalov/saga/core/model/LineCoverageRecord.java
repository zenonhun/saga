package com.github.timurstrekalov.saga.core.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public final class LineCoverageRecord {

    public static final int LINE_MISSED = 0;
    public static final int LINE_NO_STATEMENT = -1;

    private int lineNr;
    private int timesExecuted;
    private String line;

    public LineCoverageRecord(final int lineNr, final int timesExecuted, final String line) {
        this.lineNr = lineNr;
        this.timesExecuted = timesExecuted;
        this.line = line;
    }

    public static LineCoverageRecord merge(final LineCoverageRecord l1, final LineCoverageRecord l2) {
        Preconditions.checkArgument(l1.lineNr == l2.lineNr, "Got different line numbers: %d  and %d", l1.lineNr, l2.lineNr);
        Preconditions.checkArgument(l1.line.equals(l2.line), "Got different lines: %d and %d", l1.line, l2.line);

        return new LineCoverageRecord(
                l1.lineNr,
                l1.timesExecuted == LINE_NO_STATEMENT ? LINE_NO_STATEMENT : l1.timesExecuted + l2.timesExecuted,
                l1.line
        );
    }

    public int getLineNr() {
        return lineNr;
    }

    public int getTimesExecuted() {
        return timesExecuted;
    }

    public String getLineSource() {
        return StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(line));
    }

    public boolean isExecutable() {
        return timesExecuted > LINE_NO_STATEMENT;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("lineNr", lineNr)
                .append("timesExecuted", timesExecuted)
                .append("line", line)
                .toString();
    }

}
