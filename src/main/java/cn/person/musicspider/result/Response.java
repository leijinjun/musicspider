package cn.person.musicspider.result;

/**
 * Created by lei2j on 2018/5/17.
 */
public class Response<T> {

    public static final Response OK = new Response(ResponseCode.OK);
    public static final Response BAD_REQUEST = new Response(ResponseCode.BAD_REQUEST);
    public static final Response UNAUTHORIZED = new Response(ResponseCode.UNAUTHORIZED);
    public static final Response  FORBIDDEN = new Response(ResponseCode.FORBIDDEN);
    public static final Response NOT_FOUND = new Response(ResponseCode.NOT_FOUND);
    public static final Response SERVER_INTERNAL_ERROR = new Response(ResponseCode.SERVER_INTERNAL_ERROR);

    private ResponseCode code;
    private T body;

    public Response() {
    }

    public Response(ResponseCode code) {
        this.code = code;
    }

    public Response(ResponseCode code, T body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code.getCode();
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
