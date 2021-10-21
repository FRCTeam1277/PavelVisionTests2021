package frc.stream;

/**
 * @implNote implement used on RecognizeFace; don't do multiple commands for this(hard to track)
 * @see OutputDataStream
 */
public interface IStreamOutput<T> {
    void setOutputStream(OutputDataStream<T> dataStream);
}
