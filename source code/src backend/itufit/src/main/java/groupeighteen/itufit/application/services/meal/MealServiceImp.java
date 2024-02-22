package groupeighteen.itufit.application.services.meal;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MealServiceImp implements MealService {

    public double getCalorie() {
        // String url = "https://sks.itu.edu.tr/anasayfa/yemek-listesi";
        String url = "https://sks.itu.edu.tr/ExternalPages/sks/yemek-menu-v2/uzerinde-calisilan/yemek-menu.aspx";

        Double calorie = 0.0;

        try {
            // Step 1: Make an HTTP request
            String htmlContent = sendGetRequest(url);

            // Step 2: Parse HTML content
            if (htmlContent != null) {
//                 Document document = Jsoup.parse(htmlContent);
//                 document.outputSettings().charset("UTF-8");
//
//                 Element calorieElem = document.getElementById("lbKalori");
//                 String calorieText = calorieElem.text();
//                 calorieText = calorieText.replace(",",".");
//                 calorie = Double.parseDouble(calorieText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (calorie == 0) {
            throw new RuntimeException();
        }
        return calorie;
    }

    private static String sendGetRequest(String url) throws IOException {
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        // Set the request method
        httpClient.setRequestMethod("GET");

        // Get the response code
        int responseCode = httpClient.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("GET request failed. Response Code: " + responseCode);
            return null;
        }

        // Read the response content
        BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return response.toString();

    }
}
