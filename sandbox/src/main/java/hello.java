public class hello {
    public static void main(String[] args) {
        System.out.println("Раз текст, два текст, три текст ☺");
/*
        var configFile = new File("sandbox/build.gradle");
        System.out.println(configFile.getAbsolutePath());
        System.out.println(configFile.exists());

 */
         var x = 1;
         var y = 1;
         if (y==0){
            System.out.println("Деление на 0 запрещено");
         } else {
                var z = devide(x, y);
                System.out.println("Парам");
            }
    }

    private static int devide(int x, int y) {
        var z = x / y;
        return z;
    }
}
