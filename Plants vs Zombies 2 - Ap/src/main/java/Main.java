import controllers.datacontroller.Data;
import models.App;



public class Main{

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("کلاس‌لودر فعال: " + classLoader.getClass().getName());

// تست پیدا کردن URL فایل
        java.net.URL resourceUrl = classLoader.getResource("plants.json");
        if (resourceUrl != null) {
            System.out.println("موفقیت! فایل در این مسیر پیدا شد: " + resourceUrl);
        } else {
            System.out.println("خطا: کلاس‌لودر اصلاً این فایل را در Classpath نمی‌بیند.");
        }
        Data.deserializeUser();
        Data.setUp();
        Data.loadPlantsFromJson();
        while (App.getScreen() != null) {
            App.getScreen().input();
        }
    }
}
