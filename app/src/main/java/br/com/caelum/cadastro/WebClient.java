package br.com.caelum.cadastro;

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
            URL url = new URL("https://caelum.com.br/mobile");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Contant-type", "aplication/json");
            con.setRequestProperty("Accept", "aplication/json");
            con.setDoInput(true);
            con.setDoOutput(true);
            PrintStream saida = new PrintStream(con.getOutputStream());
            saida.println(json);
            con.connect();

            Scanner entrada = new Scanner(con.getInputStream());

            if(entrada.hasNext()) {
                return entrada.next();
            }
            return "sem resposta";

        }catch (IOException e){
            return "sem resposta";
        }
    }
}
