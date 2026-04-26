import java.io.File;

public class hello {
    public static void main(String[] args) {
        System.out.println("Раз текст, два текст, три текст ☺");

        var configFile = new File("sandbox/build.gradle");
        System.out.println(configFile.getAbsolutePath());
        System.out.println(configFile.exists());
    }
}
