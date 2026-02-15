package org.example;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        if(!validateArgs(args)){
            return;
        }
        CSVParser parser= fetchCvs(args[2]);

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
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL syntax!");
        }
        return true;
    } 
}