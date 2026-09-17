package p6rng;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.security.KeyBuilder;
import javacard.security.KeyPair;
import javacard.security.MessageDigest;
import javacard.security.RandomData;
import javacard.security.Signature;
import javacardx.crypto.Cipher;

// 探针 P6 : 安全随机数
// 这段代码在 applet 实例被构造时执行（也就是 INSTALL 的时候）。
// 卡支持 -> install 成功(9000)；卡不支持 -> 抛 CryptoException -> install 返回 6F00。
public class Rng extends Applet {

    private Rng() {
        RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
    }

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new Rng().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }
        ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
    }
}
