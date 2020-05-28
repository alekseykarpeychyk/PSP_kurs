package sample;


import java.io.*;
import java.net.Socket;

public final class Client {
    private static Socket clientSocket; //сокет для общения
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    private static Client instance;

    public static synchronized Client getInstance(){
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }

    private Client() {
        try {
            // адрес - локальный хост, порт - 4004, такой же как у сервера
            clientSocket = new Socket("localhost", 9000); // этой строкой мы запрашиваем
            //  у сервера доступ на соединение
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // писать туда же
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            InputStream input  = clientSocket.getInputStream();
            System.out.println("client.Client connected to socket.");
            // System.out.println("Вы что-то хотели сказать? Введите это здесь:");
            // если соединение произошло и потоки успешно созданы - мы можем
            //  работать дальше и предложить клиенту что то ввести
            // если нет - вылетит исключение
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void send(Integer num) {
        try {
            out.write(num); // отправляем сообщение на сервер
            out.flush();
            System.out.println("я отправил: "+ num);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void send(String word) {
        try {
            out.write(word+'\n'); // отправляем сообщение на сервер
            out.flush();
            System.out.println("я отправил: "+ word);
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    public String get() throws IOException {
        String word = in.readLine();
        System.out.println("я принял: "+word);
        return word;
    }

    public void close() {
        try {

            clientSocket = new Socket("localhost", 9001); // этой строкой мы запрашиваем
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}