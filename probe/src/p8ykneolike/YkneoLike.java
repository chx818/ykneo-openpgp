package p8ykneolike;

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

// 探针 P8 : ykneo 安装时的全套密码学组件（组合探针）
// 这段代码在 applet 实例被构造时执行（也就是 INSTALL 的时候）。
// 卡支持 -> install 成功(9000)；卡不支持 -> 抛 CryptoException -> install 返回 6F00。
public class YkneoLike extends Applet {

    private YkneoLike() {
        new KeyPair(KeyPair.ALG_RSA_CRT, (short) 2048);
        Signature.getInstance(Signature.ALG_DES_MAC8_ISO9797_1_M2_ALG3, false);
        Cipher.getInstance(Cipher.ALG_DES_CBC_ISO9797_M2, false);
        KeyBuilder.buildKey(KeyBuilder.TYPE_DES_TRANSIENT_DESELECT, KeyBuilder.LENGTH_DES3_2KEY, false);
        MessageDigest.getInstance(MessageDigest.ALG_SHA_1, false);
        MessageDigest.getInstance(MessageDigest.ALG_SHA_256, false);
        RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
    }

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new YkneoLike().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }
        ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
    }
}
