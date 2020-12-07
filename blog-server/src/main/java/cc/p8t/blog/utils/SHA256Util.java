package cc.p8t.blog.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {
    // 复用一个MessageDigest就行了, 没必要每次加密都获取一个
    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getDigest(String msg) {
        // 更新要加密的信息
        md.update(msg.getBytes(StandardCharsets.UTF_8));
        // 加密(获取256位摘要)
        byte[] digest = md.digest();
        // 转16进制
        return byte2Hex(digest);

        // MessageDigest md;
        // String encodeStr = "";
        // try {
        //     md = MessageDigest.getInstance("SHA-256");
        //     // 获取信息摘要需要传入信息的字节数组形式
        //     // String默认编码是与平台相关的,因此需要指定编码方式
        //     // msg.getBytes();
        //     md.update(msg.getBytes(StandardCharsets.UTF_8));
        //     // 加密并转为16进制
        //     encodeStr = byte2Hex(md.digest());
        // } catch (NoSuchAlgorithmException e) {
        //     e.printStackTrace();
        // }
        // return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String temp;
        for (byte b : bytes) {
            // 一个字节可以转换成2位16进制数
            // 一个byte是8位, 0xFF也是8位, 看似没必要与一个0xFF
            // 实际上Integer.toHexString()接受int参数
            // 如果byte是负数, 则会高24位补1, 像下面这样
            // 0xc4  --> 0xffffffc4
            temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                // 长度为1, 高位补0
                sb.append("0");
            }
            sb.append(temp);

            // 效率低 1000万个输入差了50%
            // String low = Integer.toHexString(b & 0x0F);
            // String high = Integer.toHexString(b >>> 4 & 0x0F);
            // sb.append(high).append(low);
        }
        return sb.toString();
    }
}