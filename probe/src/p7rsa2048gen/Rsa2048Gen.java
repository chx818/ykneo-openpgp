package p7rsa2048gen;

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

// 探针 P7 : RSA-CRT 2048 并且真的生成一对密钥
// 这段代码在 applet 实例被构造时执行（也就是 INSTALL 的时候）。
// 卡支持 -> install 成功(9000)；卡不支持 -> 抛 CryptoException -> install 返回 6F00。
public class Rsa2048Gen extends Applet {

    private Rsa2048Gen() {
        KeyPair kp = new KeyPair(KeyPair.ALG_RSA_CRT, (short) 2048);
        kp.genKeyPair();
    }

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new Rsa2048Gen().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }
        ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
    }
}
