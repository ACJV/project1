package com.example.project.Service;

import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
//import jdk.vm.ci.meta.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestingAvailability {


    public static void checkDate(String startD, String endD, ArrayList<Booking> bookings){ //Assuming we'll have dates as strings - Otherwise, same methodology.
        LocalDate startDate = LocalDate.parse(startD);
        LocalDate endDate = LocalDate.parse(endD);
        ArrayList<LocalDate> searchDates = new ArrayList<LocalDate>();
        for(LocalDate i = startDate; i.isBefore(endDate); i.plusDays(1)){
            searchDates.add(i);
        }

        //ArrayList<Vehicle> unAvailableV = new ArrayList<Vehicle>():
        ArrayList<Vehicle> availableVehicles = new ArrayList<Vehicle>();
        ArrayList<Vehicle> allVehicles = new ArrayList<Vehicle>();// Would get this list from another class - Or make a method to fill this arrayList in this method.

        // ------------------ The important part / methodology:

        // List of unavailable regNumbers - To be compared to with list of vehicles...
        ArrayList<String> unAvailableVehicles = new ArrayList<String>(); // Could make this more efficient by having vehicle and retrieving the vehicle instead of the regNumber.

        // Go through all bookings: - We could get this to be more limited - As in having a booking array that only contains bookings in a certain month and so on - Just to have it go faster.
        for(int i = 0; i < bookings.size(); i++) {

            // I just use LocalDate since it doesn't give time as well, - Maybe that's something we want or not, doesn't really matter, just to test if this works.
            LocalDate dtStart = LocalDate.parse(bookings.get(i).getPickUpDate()); // I changed the Date in Booking to String - Have to make sure the format from HTML fits LocalDate.
            LocalDate dtEnd = LocalDate.parse(bookings.get(i).getDropOffDate());
            dtEnd = dtEnd.plusDays(1);                                              // isBefore method needs to include the last day.
            for(LocalDate k = dtStart; k.isBefore(dtEnd); k.plusDays(1)){
                if(searchDates.contains(k)){
                    if(!unAvailableVehicles.contains(bookings.get(i).getVehicleRegNumber())){
                        //unAvailableV.add();
                        unAvailableVehicles.add(bookings.get(i).getVehicleRegNumber());
                    }
                }
            }
        }
        ArrayList<Vehicle> allve = new ArrayList<Vehicle>();
        ArrayList<Vehicle> available = new ArrayList<Vehicle>();

        for(int i = 0; i < allve.size(); i++) {
            if(!unAvailableVehicles.contains(allve.get(i))){
                available.add(allve.get(i));
            }
        }
    }
}

//Sources - Code inspiration and methods used in the code for LocalDate:
// https://mkyong.com/java8/java-8-how-to-convert-string-to-localdate/
