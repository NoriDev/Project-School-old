package itmir.tistory.com.xor;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by whdghks913 on 2016-08-03.
 */
public class SecurityXorTest {
    private SecurityXor securityXor;

    @Before
    public void setUp() throws Exception {
        securityXor = new SecurityXor();
    }

    @Test
    public void getSecurityXor() throws Exception {
        assertThat(securityXor.getSecurityXor(5, 2), is(7));
        assertThat(securityXor.getSecurityXor("Hello", 77), is("(!!\""));
        assertThat(securityXor.getSecurityXor(43L, 2), is(41L));
        assertThat(securityXor.getSecurityXor(323L, 2323L), is(2128L));
        assertThat(securityXor.getSecurityXor('A', 98), is('#'));

//        BigDecimal bigDecimal = new BigDecimal(securityXor.getSecurityXor(securityXor.getSecurityXor(258, Code), 473))
//                .multiply(new BigDecimal(securityXor.getSecurityXor(securityXor.getSecurityXor(739, Code), 238)));
//        System.out.println(bigDecimal.toString());
    }

}