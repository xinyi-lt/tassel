import com.zuoquan.lt.util.MD5Util;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * Created by xmj on 2017/6/19.
 */
public class MD5Test {
    @Test
    public void testGetMD5Str() throws NoSuchAlgorithmException {
        String str = MD5Util.getMD5Hex("18966667777");
        System.out.println(str);//25fccf30ce265a273c34c01ea0fc0c4a
    }
}
