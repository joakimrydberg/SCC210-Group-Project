package game;

/**
 * @author josh
 * @date 11/02/16.
 */
public class Constants {
    public final static String JAVA_VERSION = Runtime.class.getPackage().getImplementationVersion();

    public final static String SEP = System.getProperty("file.separator");
    //Originals

//    public final static String JDK_FONT_PATH = "C:\\Program Files\\Java\\jdk" + JAVA_VERSION + "\\jre\\lib\\fonts\\";
//
//    public final static String JRE_FONT_PATH = "C:\\Program Files\\Java\\jre" + JAVA_VERSION + "\\lib\\fonts\\";

    // Josh's JDK info

    public final static String JDK_FONT_PATH = (System.getProperty("user.name").equals("josh"))
            ? SEP + "home" + SEP + "josh" + SEP + "jdk" + JAVA_VERSION + SEP + "jre" + SEP + "lib"+ SEP +"fonts"+ SEP
            : "C:\\Program Files\\Java\\jdk" + JAVA_VERSION + "\\jre\\lib\\fonts\\";
    public final static String JRE_FONT_PATH = "C:\\Program Files\\Java\\jre" + JAVA_VERSION + "\\lib\\fonts\\";

}
