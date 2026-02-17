package org.example;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        if(!validateArgs(args)){
            return;
        }
        CSVParser parser= fetchCvs(args[2]);
        if (parser == null) {
            System.out.println("Invalid CSV or unable to fetch data.");
            return;
        }
        final double x = Double.parseDouble(args[0]);
        final double y = Double.parseDouble(args[1]);
        int resultNumber = 3;


        PriorityQueue<Location> locations = new PriorityQueue<>((a, b)->
         Double.compare(b.calculateDistance(x, y), a.calculateDistance(x, y)));

        for (CSVRecord record : parser) {
                if(!validateRecord(record)){
                    return;
                }
                String name = record.get(0);
                double recordX = Double.parseDouble(record.get(1));
                double recordY = Double.parseDouble(record.get(2));
                Location location = new Location(recordX, recordY, name);
                locations.add(location);
                if (locations.size() > resultNumber) {
                    locations.poll();
                }
            }

            System.out.println(resultNumber+" closest coffee shops:");
            List<Location> result = new ArrayList<>(locations);
            result.sort(Comparator.comparing(loc -> loc.calculateDistance(x, y)));
            for (Location loc : result) {
                System.out.printf("%s -> Latitude: %.4f, Longitude: %.4f, Distance: %.4f%n",
                        loc.getName(), loc.getY(), loc.getX(), loc.calculateDistance(x, y));
            }


    }

    public static CSVParser fetchCvs(String url){

        try {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        String content = response.body();

        return CSVFormat.DEFAULT.parse(new StringReader(content));

        }catch (URISyntaxException e) {
            System.out.println("Invalid URL syntax!");
        }catch (IOException e) {
            System.out.println("IO Exception occurred");
            e.printStackTrace();
        }catch (InterruptedException e) {
            System.out.println("Request was interrupted");
            e.printStackTrace();
        }
        return null;
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
            return false;
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL syntax!");
            return false;
        }
        return true;
    } 

    public static boolean validateRecord(CSVRecord record){
        if (record.size() < 3) {
            System.out.println("Invalid CSV: missing fields");
            return false;
        }
        String name = record.get(0).trim();
        if (name.isEmpty()) {
            System.out.println("Invalid CSV: missing shop name");
            return false;
        }
        try {
            Double.parseDouble(record.get(1));
            Double.parseDouble(record.get(2));
        } catch (NumberFormatException e) {
            System.out.println("Invalid CSV: non-numeric coordinates for shop " + name);
            return false;
        }
        return true;
    }
}