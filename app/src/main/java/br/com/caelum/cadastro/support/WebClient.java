package br.com.caelum.cadastro.support;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by android6920 on 25/07/17.
 */

public class WebClient {
    public String post(String json){
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpsURLConnection conection = (HttpsURLConnection) url.openConnection();
            conection.setRequestMethod("POST");

            conection.setRequestProperty("Accept", "aplication/json");
            conection.setRequestProperty("Content-type", "aplication/json");

            conection.setDoInput(true);
            conection.setDoOutput(true);

            PrintStream saida = new PrintStream(conection.getOutputStream());
            saida.println(json);

            conection.connect();

            Scanner entrada = new Scanner(conection.getInputStream());

            if(entrada.hasNext()) {
                return entrada.next();
            }
            return "sem resposta";

        }catch (IOException e){
            return "sem resposta";
        }
    }
}
