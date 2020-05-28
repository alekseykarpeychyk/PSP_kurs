package by.bsuir;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class Worker implements Runnable {
    protected Socket clientSocket = null;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static InputStream input;
    private static OutputStream output;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
            in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
            out = new BufferedWriter( new OutputStreamWriter( clientSocket.getOutputStream() ) );

            String exit = "ok";
            while (!exit.equals( "exit" )) {
                exit = "ok";

                String str = in.readLine();

                if(in!=null) {
                    System.out.println("я получил: " + str);
                    String who = null;


                    switch (str) {
                        case "authorization": {
                            who = authorization();
                            break;
                        }
                        case "registration": {
                            registration();
                            who = "user";
                            break;
                        }
                        /*case "back": {
                            exit = "ok";
                            break;
                        }*/
                        case "exit": {
                            exit = "exit";
                            output.close();
                            input.close();
                            break;
                        }

                        default:
                            break;
                    }

                    switch (who) {
                        case "admin":
                            exit = menuAdmin();
                            break;

                        case "user":
                         exit = menuUser();
                            break;

                        default : break;
                    }
                }


            }

            out.close();
            in.close();
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }







    private String menuAdmin() throws SQLException, ClassNotFoundException {
        try{

            String menu1 = "";

            while(!menu1.equals("exit") ) {
                menu1 = in.readLine();
                System.out.println("я получил2: " + menu1);
                switch (menu1) {
                    case "user manage": {
                        UserManage();
                        break;
                    }
                    case "supply manage": {
                        CompanyManage();
                        break;
                    }
                    case "exit": {
                        menu1 = "exit";
                /*in.close();
                out.close();*/
                        break;}
                    default:break;
                }
            }

        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "exit";
    }




    private String UserManage() throws SQLException, ClassNotFoundException {
        DatabaseHandler handler = new DatabaseHandler();

        String users = handler.getUsers();
        String menu = "work with users";
        System.out.println(menu);
        //System.out.println("work");
        try {
            out.write(users + '\n');
            out.flush();
            System.out.println("я отправил users: " + users);


            while (!menu.equals("back")) {
                menu = in.readLine();

                System.out.println("я получил3: " + menu);      /////////////////////////////
                switch (menu) {

                    case "deleteUser": {
                        int userId = in.read();
                        System.out.println("я получил4: " + userId);
                        handler.deleteUser(userId);
                        break;
                    }

                    case "searchLogin":{
                        String userLogin = in.readLine();
                        String search = handler.checkUser(userLogin);
                        out.write( search + '\n' );
                        out.flush();
                        System.out.println("я отправил: " + search);
                        break;
                    }

                    case "exit": {
                        menu = "exit";
                        break;
                    }

                    default:
                        break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return menu;

    }


    private String CompanyManage() throws SQLException, ClassNotFoundException {
        DatabaseHandler handler = new DatabaseHandler();

        String firma = handler.getFirma();
        String statistic = handler.getStatistic();
        String menu = "work with firma";
        System.out.println(menu);

        try {

            out.write(firma+'\n');
            out.flush();
            System.out.println("я отправил поставщиков: " + firma);

            out.write(statistic+'\n');
            out.flush();
            System.out.println("я отправил статистику: " + statistic);



            while (!menu.equals( "back" )) {
                menu = in.readLine();
                System.out.println( "я получил: " + menu );
                switch (menu) {
                    case "deleteFirma":{
                        int firmaId = in.read();
                        System.out.println( "я получил: " + firmaId );
                        handler.deleteFirma(firmaId);
                        break;
                    }

                    case "getFirma":{
                        getFirma();
                        break;
                    }

                    case "getSinglSolution":{
                        getSinglSolution();
                        break;
                    }

                    case "getStatistics":{
                        getStatistic();
                        break;
                    }

                    case "back": {
                        menu = "back";
                        break;
                    }
                    default:break;
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return menu;



    }



    protected void registration() {
        try {
            String user;
            user = in.readLine();
            if(!user.equals( "exit" )) {
                JSONObject userJson = new JSONObject( user );
                int id = IdGenerator.getInstance( "user" ).getNextId();

                String firstname = userJson.getString( "firstname" );
                String lastname = userJson.getString( "lastname" );
                String email = userJson.getString( "email" );
                String username = userJson.getString( "username" );
                String password = userJson.getString( "password" );

                DatabaseHandler handler = new DatabaseHandler();
                String sign = handler.signUpUser(firstname, lastname, email, username, password );
                out.write( sign + '\n' );
                out.flush();
                System.out.println( "я отправил: " + sign );
            }else {return;}
        }
        catch (IOException | JSONException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    protected String authorization() {
        String who = null;
        try {
            String user = in.readLine();
            JSONObject userJson = new JSONObject(user);

            String username = userJson.getString("username");
            String password = userJson.getString("password");

            if (username.equals("admin") && password.equals("alex123")) {
                output.write("admin\n".getBytes());
                who = "admin";
            } else {


                DatabaseHandler handler = new DatabaseHandler();
                String sign = handler.signInUser(username, password);
                if (sign.equals("nobody")) {
                    output.write("nobody\n".getBytes());
                    out.flush();
                    who = "nobody";
                } else {
                    who = "user";
                    output.write("user\n".getBytes());
                    out.flush();
                    System.out.println("я отправил: " + "user");
                }

            }


        } catch (IOException | JSONException | SQLException e) {
            e.printStackTrace();
        }
        return who;
    }

    public String menuUser() throws SQLException, ClassNotFoundException {
        DatabaseHandler handler = new DatabaseHandler();

        String firma = handler.getFirma();
        String statistic = handler.getStatistic();
        String menu2 = "work with firma";
        System.out.println(menu2);
        try {

            out.write(firma+'\n');
            out.flush();
            System.out.println("я отправил поставщиков: " + firma);

            out.write(statistic+'\n');
            out.flush();
            System.out.println("я отправил статистику: " + statistic);



            while (!menu2.equals( "back" )) {
                menu2 = in.readLine();
                System.out.println( "я получил: " + menu2 );
                switch (menu2) {
                    case "deleteFirma":{
                        int firmaId = in.read();
                        System.out.println( "я получил: " + firmaId );
                        handler.deleteFirma(firmaId);
                        break;
                    }

                    case "getFirma":{
                        getFirma();
                        break;
                    }

                    case "getSinglSolution":{
                        getSinglSolution();
                        break;
                    }

                    case "getStatistics":{
                        getStatistic();
                        break;
                    }

                    case "back": {
                        menu2 = "back";
                        break;
                    }
                    default:break;
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "exit";
    }

    protected void getFirma(){
        try {
            String firma;
            firma = in.readLine();
            if(!firma.equals( "back" )) {
                JSONObject projectJson = new JSONObject( firma );
                int idfirma = IdGenerator.getInstance( "firma" ).getNextId();

                String provider = projectJson.getString( "provider" );
                String unp = projectJson.getString( "unp" );
                String material = projectJson.getString( "material" );
                int cost = projectJson.getInt( "cost" );
                int time = projectJson.getInt( "time" );


                DatabaseHandler handler = new DatabaseHandler();
                String input = handler.firmaInput( idfirma, provider, unp, material, cost, time );
                out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил: " + input );
                out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил нового поставщика: " + input );
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    protected void getStatistic(){
        try {
            String statistic;
            statistic = in.readLine();
            if(!statistic.equals( "back" )) {
                JSONObject statisticJson = new JSONObject( statistic );

                int year =  statisticJson.getInt( "year" );
                int revenue =  statisticJson.getInt( "revenue" );
                int expenses = statisticJson.getInt( "expenses" );
                int profit = statisticJson.getInt( "profit" );


                DatabaseHandler handler = new DatabaseHandler();
                String input = handler.statisticInput(year, revenue, expenses, profit );
               out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил: " + input );
                out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил статистику: " + input );
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    protected void getSinglSolution(){
        try {
            String task;
            task = in.readLine();
            if(!task.equals( "back" )) {
                JSONObject taskJson = new JSONObject( task );

                int po1 = taskJson.getInt( "po1" );
                int po2 = taskJson.getInt( "po2" );
                int po3 = taskJson.getInt( "po3" );
                int oo1 = taskJson.getInt( "oo1" );
                int oo2 = taskJson.getInt( "oo2" );
                int oo3 = taskJson.getInt( "oo3" );


                int[][] array = {{po1, po2, po3}, {oo1, oo2, oo3}};

                for(int i=0; i<2; i++){
                    for(int j=0; j<3; j++){
                        System.out.print(array[i][j]+ " ");
                    }
                    System.out.print("\n");
                }

                float[] array_yd = new float[3];
                for(int i=0; i<3; i++){
                    array_yd[i]=((float)(3*array[0][i]+2*array[1][i]))/5;
                }

                for(int i=0; i<3; i++){
                    System.out.print(array_yd[i]+ " ");
                }
                System.out.print("\n");


                float[] array_av = new float[3];
                for(int i=0; i<3; i++){
                    array_av[i]=((float)(array[1][i]-array[0][i]))/5;
                }


                for(int i=0; i<3; i++){
                    System.out.print(array_av[i] + " ");
                }
                System.out.print("\n");


                float[] array_ans = new float[3];
                for(int i=0; i<3; i++){
                    array_ans[i] = (array_av[i]/array_yd[i]);
                }

                for(int i=0; i<3; i++){
                    System.out.print(array_ans[i]+ " ");
                }
                System.out.print("\n");


                float x = array_ans[0];
                float y = array_ans[0];
                int a = -1;
                int b = -1;


                for(int i=0; i<3;i++){
                  if (array_ans[i] <= x){
                      x = array_ans[i];
                      a = i;
                  }
                  if (array_ans[i] >= y){
                      y = array_ans[i];
                      b = i;
                  }
                }
                b=b+1;
                a=a+1;
                System.out.println("Минимальная оценка риска: " +x +"\nМаксимальная оценка риска: " +y);

                String ans_pr1 = Float.toString(array_ans[0]);
                String ans_pr2 = Float.toString(array_ans[1]);
                String ans_pr3 = Float.toString(array_ans[2]);
                String x_ans = Float.toString(x);
                String y_ans = Float.toString(y);
                String a_ans = Integer.toString(a);
                String b_ans = Integer.toString(b);


//                out.write( ans_pr1 + '\n' );
//                out.flush();
//                System.out.println( "я отправил риски Пр1: " + ans_pr1 );

              /*  out.write( ans_pr2 + '\n' );
                out.flush();
                System.out.println( "я отправил риски Пр2: " + ans_pr2 );

                out.write( ans_pr3 + '\n' );
                out.flush();
                System.out.println( "я отправил риски Пр3: " + ans_pr3 );

                out.write( x_ans + '\n' );
                out.flush();
                System.out.println( "я отправил оптимальный выбор: " + x_ans );*/

                out.write( a_ans + '\n' );
                out.flush();
                System.out.println( "Оптимально выбрать материал под № " + a_ans );

              /*  out.write( y_ans + '\n' );
                out.flush();
                System.out.println( "я отправил неоптимальный выбор: " + y_ans );*/

                out.write( b_ans + '\n' );
                out.flush();
                System.out.println( "Нерационально выбирать материал под № " + b_ans );
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}



