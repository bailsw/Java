import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Administrator on 2018/09/13.
 */
public class Format {
    public static String format(StringBuffer sb){
        String str=sb.toString();
        str=str.replaceAll(";",";\r\n");
        return str;

    }
}
