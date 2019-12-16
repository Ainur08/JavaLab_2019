package protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class Response<T> {
    private T data;
    private Payload payload;

    public Response(T data) {
        this.data = data;
        buildPayload();
    }

    private void buildPayload() {
        payload = new Payload();
        payload.setPayload(data);
        if (data instanceof Message) {
            payload.setHeader("message");
        } else if(data instanceof User) {
            payload.setHeader("token");
        } else if(data instanceof Pagination) {
            payload.setHeader("messages");
        } else if(data instanceof ProductList) {
            payload.setHeader("products");
        } else if (data instanceof OrderList) {
            payload.setHeader("orders");
        }
    }

    public static <T> Response<T> build(T data) {
        return new Response<>(data);
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
