package token;

import org.junit.Test;

public class testJwtHelper {
    @Test
    public void testJ() {
        String token = JwtHelper.createToken(100000000, "张飞");
        System.out.println(token);
        System.out.println(JwtHelper.getId(token));
        System.out.println(JwtHelper.getName(token));
    }
}
