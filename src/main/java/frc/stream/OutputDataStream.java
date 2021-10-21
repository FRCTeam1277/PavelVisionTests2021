package frc.stream;

/**
 * Allows implementor to output/write to data
 */
public class OutputDataStream<T> {
    private StreamData<T> dataStream;
    public OutputDataStream(StreamData<T> dataStream){
        setDataStream(dataStream);
    }
    public void setDataStream(StreamData<T> dataStream) {
        this.dataStream=dataStream;
    }
    public void changeData(T newData) {
        dataStream.setData(newData);
    }
}
