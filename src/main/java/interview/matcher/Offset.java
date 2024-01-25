package interview.matcher;

import java.util.Objects;

// Offset class to store line and char offsets
class Offset {
    private final int lineOffset;
    private final int charOffset;

    public Offset(int lineOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offset offset = (Offset) o;
        return lineOffset == offset.lineOffset && charOffset == offset.charOffset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineOffset, charOffset);
    }

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }
}
