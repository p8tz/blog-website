package cc.p8t.blog.result;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/12/7 16:58
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(CodeInfo c) {
        this.code = c.code;
        this.msg = c.msg;
    }

    public Result(CodeInfo c, T data) {
        this.code = c.code;
        this.msg = c.msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void SetCodeInfo(CodeInfo c) {
        this.code = c.code;
        this.msg = c.msg;
    }
}
