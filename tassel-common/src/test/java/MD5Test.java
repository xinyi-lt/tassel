import com.zuoquan.lt.security.MD5Util;
import com.zuoquan.lt.security.ScrubSensitiveDataUtil;
import com.zuoquan.lt.security.SecurityHash;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by xmj on 2017/6/19.
 */
public class MD5Test {
    @Test
    public void testGetMD5Str() throws NoSuchAlgorithmException {
        String str = MD5Util.getMD5Hex("18966667777");
        System.out.println(str);//25fccf30ce265a273c34c01ea0fc0c4a
    }

    public static final int SALT_BYTE_SIZE = 10;

    @Test
    public void testSecureRandom() throws UnsupportedEncodingException {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        System.out.println(salt.length);
        String saltStr = convertByteToHex(salt);
        System.out.println(saltStr.length());
        System.out.println(saltStr);

    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    @Test
    public void testScrub(){
        String str1 = ScrubSensitiveDataUtil.scrubPhone("010-07236971");
        System.out.println(str1);
        String str2 = ScrubSensitiveDataUtil.scrubPhone("0755-0723697");
        System.out.println(str2);
        String str3 = ScrubSensitiveDataUtil.scrubPhone("15200375012");
        System.out.println(str3);
        String str4 = ScrubSensitiveDataUtil.scrubPhone("1520037501212");
        System.out.println(str4);
        String str5 = ScrubSensitiveDataUtil.scrubPhone("8236543");
        System.out.println(str5);


        String str6 = ScrubSensitiveDataUtil.scrubBankCard("6228480402564890018");
        System.out.println(str6);
        String str7 = ScrubSensitiveDataUtil.scrubBankCard("6226097806222345");
        System.out.println(str7);

        String str8 = ScrubSensitiveDataUtil.scrubIDNum("13062119900623006X");
        System.out.println(str8);
        String str9 = ScrubSensitiveDataUtil.scrubIDNum("130621199006230");
        System.out.println(str9);

    }

    @Test
    public void validate() throws NoSuchAlgorithmException {
        String salt = "88e9f19fdef2413c08fb";
        String inputPwd = "6226097806118985312";

        String validataHash = SecurityHash.getHashWithSalt("SHA-256", inputPwd, salt);
        Assert.assertEquals("a51e6a8a7c0f77a2fea730d254b6a212d0fe614f9e6593f0b0a1d264e16ade82", validataHash);
    }

    @Test
    public void testId(){
        Long id = new Long(84361596574642291L);
        String binarayStr = Long.toBinaryString(id);
        System.out.println(binarayStr);
        System.out.println(binarayStr.length());
    }
}


