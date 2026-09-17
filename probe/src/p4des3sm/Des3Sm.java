package p4des3sm;

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

// 探针 P4 : 3DES 安全消息组件（MAC + CBC + 瞬时 DES3 密钥）
// 这段代码在 applet 实例被构造时执行（也就是 INSTALL 的时候）。
// 卡支持 -> install 成功(9000)；卡不支持 -> 抛 CryptoException -> install 返回 6F00。
public class Des3Sm extends Applet {

    private Des3Sm() {
        Signature.getInstance(Signature.ALG_DES_MAC8_ISO9797_1_M2_ALG3, false);
        Cipher.getInstance(Cipher.ALG_DES_CBC_ISO9797_M2, false);
        KeyBuilder.buildKey(KeyBuilder.TYPE_DES_TRANSIENT_DESELECT, KeyBuilder.LENGTH_DES3_2KEY, false);
    }

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new Des3Sm().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }
        ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
    }
}
