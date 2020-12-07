package cc.p8t.blog.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 1. bit填充使满足   bit数 mode 512 == 448   然后再加原信息长度的64位表示形式得到n个512bits块
 * ____填充方式: 原信息末尾 加 1 再加若干个 0
 * ____________若原信息已满足对 512 模 448 则填充 512 bits
 * 2. 取第一个512bits块, 分割为 16 * 32bits  其中32bits可视为 unsigned int(后面加法均为mod 2的32次幂的加法)
 * ____通过公式计算得到第17~64个unsigned int
 * ________对这64个unsigned int 进行64次循环: 除了第一次, 每次循环更新一次8个hash
 * ____________对后续512bits块采取相同操作, 最后将得到的8个hash拼接即得256位hash值
 */
public class MySHA256Util {
    /**
     * Initialize hash values:
     * (first 32 bits of the fractional parts of the roots of the first 8 primes)
     * 2, 3, 5, 7, 11, 13, 17, 19
     */
    private static final int[] initHash = {0x6a09e667, 0xbb67ae85, 0x3c6ef372,
            0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
    /**
     * first 32 bits of the fractional parts of the cube roots of the first 64 primes
     * 2...311
     */
    private static final int[] K = {0x428a2f98, 0x71374491, 0xb5c0fbcf,
            0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74,
            0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786,
            0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc,
            0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
            0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85,
            0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb,
            0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70,
            0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3,
            0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f,
            0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7,
            0xc67178f2};

    private static final int[] W = new int[64];
    private static final int[] hash = new int[8];
    // 用于存储每轮压缩后的hash
    private static final int[] temp = new int[8];

    private static byte[] SHA256(byte[] message) {
        System.arraycopy(initHash, 0, hash, 0, initHash.length);
        int[] words = toIntArray(bitsPad(message));
        final int blockSize = 16;
        // 16个int为一块512bits
        for (int i = 0; i < words.length / blockSize; ++i) {
            // W[0] ~ W[15]
            System.arraycopy(words, 16 * i, W, 0, 16);
            // 计算 W[16] ~ W[63]
            for (int j = 16; j < 64; ++j) {
                W[j] = W[j - 16] + W[j - 7] + s0(W[j - 15]) + s1(W[j - 2]);
            }
            // 将8个要用的hash copy一份
            System.arraycopy(hash, 0, temp, 0, hash.length);
            // 对8个hash值进行64轮压缩
            for (int j = 0; j < 64; ++j) {
                int t1 = temp[7] + S1(temp[4]) + ch(temp[4], temp[5], temp[6]) + K[j] + W[j];
                int t2 = S0(temp[0]) + maj(temp[0], temp[1], temp[2]);
                // 压缩
                System.arraycopy(temp, 0, temp, 1, temp.length - 1);
                temp[0] = t1 + t2;
                temp[4] += t1;
            }
            // 更新hash值
            for (int j = 0; j < hash.length; ++j) {
                hash[j] += temp[j];
            }
        }
        return toByteArray(hash);
    }

    public static String getSHA256(String str) {
        return bytesToHex(SHA256(str.getBytes(StandardCharsets.UTF_8)));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    // 填充比特
    private static byte[] bitsPad(byte[] originMessage) {
        final int blockBits = 512;
        final int blockBytes = blockBits / 8;

        // 计算填充0b10000000(1byte)和原信息长度(64位形式)后的长度
        int newMsgLength = originMessage.length + 1 + 8;
        // 需要填充 0 的字节数
        int paddingBytes = blockBytes - newMsgLength % 512;
        newMsgLength += paddingBytes;
        byte[] paddedMessage = new byte[newMsgLength];
        System.arraycopy(originMessage, 0, paddedMessage, 0, originMessage.length);
        // 填充 1-bit
        paddedMessage[originMessage.length] = (byte) 0b10000000;
        // 0不需要填充

        // 填充originMessage的长度(64位形式)
        // 从paddedMessage的lenPos起填充8bytes, value为原信息的bit长度
        int lenPos = originMessage.length + 1 + paddingBytes;
        ByteBuffer.wrap(paddedMessage, lenPos, 8).putLong(originMessage.length * 8);
        return paddedMessage;
    }

    // 将填充后的信息转为int数组, 每次取16个 即可达到分512bits为一块的要求
    private static int[] toIntArray(byte[] bytes) {
        if (bytes.length % Integer.BYTES != 0) {
            throw new IllegalArgumentException("ByteArrayLength");
        }
        // 把bytes数组作为缓冲区
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        int[] target = new int[bytes.length / Integer.BYTES];
        for (int i = 0; i < target.length; ++i) {
            // 读取Integer.BYTES个字节, 按顺序当作int值读取
            target[i] = bb.getInt();
        }
        return target;
    }

    private static byte[] toByteArray(int[] arr) {
        ByteBuffer bb = ByteBuffer.allocate(arr.length * Integer.BYTES);
        for (int value : arr) {
            bb.putInt(value);
        }
        return bb.array();
    }

    private static int s0(int x) {
        return Integer.rotateRight(x, 7) ^ Integer.rotateRight(x, 18) ^ (x >>> 3);
    }

    private static int s1(int x) {
        return Integer.rotateRight(x, 17) ^ Integer.rotateRight(x, 19) ^ (x >>> 10);
    }

    private static int S1(int x) {
        return Integer.rotateRight(x, 6) ^ Integer.rotateRight(x, 11) ^ Integer.rotateRight(x, 25);
    }

    private static int ch(int x, int y, int z) {
        return (x & y) ^ ((~x) & z);
    }

    private static int S0(int x) {
        return Integer.rotateRight(x, 2) ^ Integer.rotateRight(x, 13) ^ Integer.rotateRight(x, 22);
    }

    private static int maj(int x, int y, int z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }
}