public class ByteSize extends Token {
  private final long bytes;

  public ByteSize(String value) {
    super(value);
    this.bytes = parseToBytes(value);
  }

  private long parseToBytes(String value) {
    value = value.toLowerCase();
    if (value.endsWith("kb")) return (long) (Double.parseDouble(value.replace("kb", "")) * 1024);
    if (value.endsWith("mb")) return (long) (Double.parseDouble(value.replace("mb", "")) * 1024 * 1024);
    // Add GB, TB etc.
    throw new IllegalArgumentException("Invalid byte unit");
  }

  public long getBytes() { return bytes; }
}
