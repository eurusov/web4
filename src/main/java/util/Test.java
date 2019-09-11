package util;

import model.SimpleCar;
import model.SimpleReport;
import service.CarService;
import service.DailyReportService;

public class Test {
    public void test() throws DBException {
        CarService carService = CarService.getInstance();
        DailyReportService dailyReportService = DailyReportService.getInstance();

        Long carId = carService.addCar(
                "Honda",
                "Acura TLX",
                "BD51 SMR",
                45000L
        );
        System.out.println("Car was added with id = " + carId);
//        carService.sellCar(carId); // mark this car as sold
        SimpleCar car = carService.getCar(carId);
        System.out.println(car);

        carId = carService.addCar(
                "Toyota",
                "Supra",
                "BND007",
                55000L
        );
        System.out.println("Car was added with id = " + carId);
        car = carService.getCar(carId);
        System.out.println(car);
        System.out.println();


        Long repId = dailyReportService.addDailyReport(123000L, 16L);
        System.out.println("DailyReport was added with id = " + repId);
        SimpleReport simpleReport = dailyReportService.getDailyReport(repId);
        System.out.println("getDailyReport by this id returns: " + simpleReport);

        DBDate.nextDay();
        System.out.println("\nDate was changed.\n");

        repId = dailyReportService.addDailyReport(3000L, 10L);
        System.out.println("DailyReport was added with id = " + repId);
        simpleReport = dailyReportService.getDailyReport(repId);
        System.out.println("getDailyReport by this id returns: " + simpleReport);

        System.out.println("\nAll not sold cars list:\n" + carService.getAllCars());
        System.out.println("\nAll reports list:\n" + dailyReportService.getAllDailyReports());
        System.out.println("\nLast report:\n" + dailyReportService.getLastReport());
        System.out.println();

        DBHelper.deleteAll();
    }
}
