package frc.stream;

//used since java doesn't support multi inheritance

/**
 * @implNote implement used on commands that need face data
 * @see InputDataStream
 */
public interface IStreamInput<T> {
    void setInputStream(InputDataStream<T> dataStream);
}

