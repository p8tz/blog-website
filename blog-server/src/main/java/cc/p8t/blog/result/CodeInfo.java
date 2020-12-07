package cc.p8t.blog.result;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/12/7 16:53
 */
public enum CodeInfo {

    SUCCESS(1000, "ok"),
    AUTH_ERROR(2001, "身份验证失败"),
    VALIDATED_ERROR(2002, "参数校验失败");

    int code;
    String msg;


    CodeInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
