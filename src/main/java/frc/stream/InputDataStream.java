package frc.stream;

/**
 * Allows implementor to read datastream
 */
public class InputDataStream<T> {
    private StreamData<T> dataStream;

    public InputDataStream(StreamData<T> dataStream){
        setDataStream(dataStream);
    }
    public void setDataStream(StreamData<T> dataStream) {
        this.dataStream=dataStream;
    }
    public T readData() {
        return dataStream.getData();
    }
}
