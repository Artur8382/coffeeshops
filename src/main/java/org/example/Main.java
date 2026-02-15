package org.example;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.csv.CSVParser;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        if(!validateArgs(args)){
            return;
        }
        

    }

    public static boolean validateArgs(String[] args){
        if (args.length < 3) {
            System.out.println("Too few arguments");
            System.out.println("Usage: java -jar app.jar <user x coordinate> <user y coordinate> <shop data url>");
            return false;
        }
        try {
            Double.parseDouble(args[0]);
            Double.parseDouble(args[1]); 
            new URI(args[2]);
        } catch (NumberFormatException exception) {
            System.out.println("First argument must be a decimal number");
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL syntax!");
        }
        return true;
    } 
}