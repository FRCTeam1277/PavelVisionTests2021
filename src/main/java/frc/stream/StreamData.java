package frc.stream;

/**
 * Basic data encapsulation
 */
public class StreamData<T> {
    private T data;
    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
