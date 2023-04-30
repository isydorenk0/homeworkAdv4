package Ex3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime date = getDate(scanner);
        calcTodayDif(date);
    }

    private static void calcTodayDif(LocalDateTime date) {
        LocalDateTime today = LocalDateTime.now();
        System.out.println("From date: " + date);
        System.out.println("To date: " + today);

        if (today.isBefore(date)) {
            System.out.println("Entered date is in future");
            return;
        } else if (today.isEqual(date)) {
            System.out.println("Entered date is today");
            return;
        }
        long diffMs = ChronoUnit.MILLIS.between(date, today);
        long diffSec = TimeUnit.SECONDS.convert(diffMs, TimeUnit.MILLISECONDS) % 60;
        long diffMin = TimeUnit.MINUTES.convert(diffMs, TimeUnit.MILLISECONDS) % 60;
        long diffHrs = TimeUnit.HOURS.convert(diffMs, TimeUnit.MILLISECONDS) % 24;
        // використовую Період, щоб самому не рахувати кількість днів і місяців
        Period diffDate = Period.between(date.toLocalDate(), today.toLocalDate());

        System.out.println("You are " + diffDate.getYears() + " years, " + diffDate.getMonths() +
                " months, " + diffDate.getDays() + " days, " + diffHrs + " hours, " + diffMin + " minutes, and " +
                diffSec + " seconds old.");
    }

    private static LocalDateTime getDate(Scanner scanner) {
        while(true) {
            System.out.println("Enter date and time, in format dd/mm/yyyy hh:mm:ss: ");
            String number = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            try {
                return sdf.parse(number).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            } catch (ParseException e) {
                System.out.println("Entered date has wrong format");
            }
        }
    }
}
