import java.io.Serializable;
import java.util.Objects;

public class ResponseCorrectAnswer implements Serializable, Comparable<ResponseCorrectAnswer> {
    private static final long serialVersionUID = 7L;
    String response;

    public ResponseCorrectAnswer(String response) {
        this.setResponse(response);
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseCorrectAnswer)) return false;
        ResponseCorrectAnswer that = (ResponseCorrectAnswer) o;
        return Objects.equals(getResponse(), that.getResponse());
    }

    @Override
    public int compareTo(ResponseCorrectAnswer o) {
        return this.getResponse().compareTo(o.getResponse());
    }
}
