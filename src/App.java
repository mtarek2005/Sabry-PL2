import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class App {
        public static DataHandler<User> userDataHandler;
        public static DataHandler<ProductCategory> productCategoryDataHandler;
        public static DataHandler<Product> productDataHandler;
        public static Module currentModule;
        public static User user;
        public static Scanner input;

        public static void initializeData() throws IOException,ClassNotFoundException {
                userDataHandler = new DataHandler<User>("files/User.jbin", new User(null, null, null));
                productCategoryDataHandler = new DataHandler<ProductCategory>("files/Category.jbin", new ProductCategory(null));
                productDataHandler = new DataHandler<Product>("files/Product.jbin", new Product(null,null,null,null,0));
                // userDataHandler.add(new User("sabry","123",User.utype.admin));
                // userDataHandler.add(new User("moh","123",User.utype.client));
                // productCategoryDataHandler.add(new ProductCategory("phones"));
                // productCategoryDataHandler.add(new ProductCategory("laptop"));
        }

        public static void login() throws IOException {
                String username = "";
                String pass = "";
                //
                System.out.print("\033[H\033[2J");
                System.out.flush();
                //
                // credentials input
                username = inputString(
                        // prompt
                        printUsernameBox(),
                        // err message
                        printUsernameEmptyBox()
                );
                if (username.equals("exit"))
                        return;

                pass = inputString(
                        // prompt
                        printPassBox(),
                        // err message
                        printPassEmptyBox()
                );
                System.out.print("\n");
                if (pass.equals("exit"))
                        return;
                // check user's credentials
                boolean foundUser = false;
                for (int i = 0; i < userDataHandler.getLength(); i++) {
                        if (userDataHandler.get(i).canlogin(username, pass)) {
                                user = userDataHandler.get(i);
                                startModule();
                                foundUser = true;
                                break;
                        }
                }
                if (!foundUser)
                {
                        System.out.println(printInABoxError("Username or password are wrong!",85));
                        input.nextLine();
                }
        }

        public static void startModule() throws IOException {
                // admin module path
                
                if (user.getUserType() == User.utype.admin) {
                        int choice;
                        boolean exit = false;
                        while (!exit) {
                                System.out.print("\033[H\033[2J"); System.out.flush();
                                choice = inputInt(printInABox("🯇 Please choose one of the following options:🯈","1)│Admin Module.\n2)│Product Module.\n0)│Sign Out.",3,85,true)+
                                                                "║ Please enter your choice: ");
                                for(int i = 0; i < 58 - String.valueOf(choice).length(); i++){
                                        System.out.print(" ");
                                }
                                System.out.print("║\n");
                                switch (choice) {
                                        case 1:
                                                currentModule = new AdminModule(user);
                                                currentModule.startModule();
                                                break;
                                        case 2:
                                                currentModule = new ProductModule(user);
                                                currentModule.startModule();
                                                break;
                                        case 0:
                                                exit = true;
                                                break;
                                        default:
                                                System.out.println(printInABoxError("Invalid Choice, please try again", 85));
                                                break;

                                }
                        }
                } else {
                        System.out.println("client");

                }
                
        }

        public static void main(String[] args) {
                try {
                        initializeData();

                        // menu
                        input = new Scanner(System.in);

                        int choice;
                        boolean exit = false;

                        System.out.print("\033[H\033[2J");System.out.flush();

                        while (!exit) {
                                System.out.println(printInABoxMain("Task Management System","A task management system made in Java for the PL2 course at Helwan University.\nThis project made by: 3ab3z, Omar Atya, Omar Wagih, Ali, Mohammed, Tarek.","🯇 Please choose one of the following options:🯈","1)│Sign in.\n0)│Exit.",3,85,false,10,20));

                                choice = inputInt("Input:🮶  ");
                                switch (choice) {
                                        case 1:
                                                login();
                                                break;
                                        case 0:
                                                System.out.print("\033[H\033[2J");System.out.flush();
                                                System.out.print(printInABoxGreen("Thank you for using this program!",85));
                                                exit = true;
                                                break;
                                        default:
                                                System.out.println(printInABoxError("Invalid Choice, please try again",85));
                                                break;
                                }
                        }
                } catch (IOException ex) {
                        System.err.println("Fatal Error:" + ex +":"+ex.getMessage());
                        ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                        System.err.println("wut??:" + ex.getMessage());
                }
        }

        public static int inputInt(String print) {
                return inputInt(print, printInABoxError("Input must be an integer!",85));
        }

        public static int inputIntln(String print) {
                return inputInt(print + "\n");
        }

        public static int inputIntln(String print, String error) {
                return inputInt(print + "\n", error + "\n");
        }

        public static int inputInt(String print, String error) {
                int value;
                while (true) {
                        try {
                                System.out.print(print);
                                value = Integer.parseInt(input.nextLine().trim());
                                break;
                        } catch (NumberFormatException ex) {
                                System.err.print(error);
                                continue;
                        }
                }
                return value;
        }

        public static Integer[] inputInts(int len, String print) {
                return inputInts(len, print, printInABoxError("Input must be an integer!",85), printInABoxError("Input must be in range!",85), printInABoxError("Input must not be empty!",85));
        }

        public static Integer[] inputIntsln(int len, String print) {
                return inputInts(len, print + "\n");
        }

        public static Integer[] inputIntsln(int len, String print, String num_error, String range_error, String blank_error) {
                return inputInts(len, print + "\n", num_error + "\n", range_error + "\n", blank_error + "\n");
        }

        public static Integer[] inputInts(int len, String print, String num_error, String range_error, String blank_error) {
                ArrayList<Integer> value;
                while (true) {
                        try {
                                String line=inputString(print,blank_error);
                                value=new ArrayList<>();
                                for(String num:line.split(",")){
                                        int n=Integer.parseInt(num);
                                        if(n<len){
                                                value.add(n);
                                        }
                                        else{
                                                System.err.print(blank_error);
                                                continue;
                                        }
                                }
                                break;
                        } catch (NumberFormatException ex) {
                                System.err.print(num_error);
                                continue;
                        }
                }
                return Arrays.copyOf(value.toArray(), value.size(), Integer[].class);
        }

        public static String inputString(String prompt) {
                return inputString(prompt, printInABoxError("Input must not be empty!",85));
        }

        // Waits for the user to acknowledge message
        public static String inputString(String prompt, String isBlank_message) {
                String value;
                while (true) {
                        System.out.print(prompt);
                        value = input.nextLine().trim();
                        if (value.isBlank()) {
                                System.err.print(isBlank_message);
                                continue;
                        }
                        break;
                }
                return value;
        }

        public static String inputStringln(String prompt) {
                return inputString(prompt + "\n");
        }

        public static String inputStringln(String prompt, String isBlank_message) {
                return inputString(prompt + "\n", isBlank_message + "\n");
        }

        public static String inputStringOneWord(String prompt) {
                return inputString(prompt).split(" ")[0];
        }

        public static String inputStringOneWord(String prompt, String isBlank_message) {
                return inputString(prompt, isBlank_message).split(" ")[0];
        }

        public static String inputStringOneWordln(String prompt) {
                return inputStringln(prompt).split(" ")[0];
        }

        public static String inputStringOneWordln(String prompt, String isBlank_message){
                return inputStringln(prompt, isBlank_message).split(" ")[0];
        }

        public static double inputDouble(String print, String error) {
                double value;
                while (true) {
                        try {
                                System.out.print(print);
                                value = Double.parseDouble(input.nextLine().trim());
                                break;
                        } catch (NumberFormatException ex) {
                                System.err.print(error);
                                continue;
                        }
                }
                return value;
        }

        public static double inputDouble(String print){
                return inputDouble(print, printInABoxError("Input must be an number!",85));
        }

        public static double inputDoubleln(String print) {
                return inputDouble(print + "\n");
        }

        public static double inputDoubleln(String print, String error) {
                return inputDouble(print + "\n", error + "\n");
        }

        public static LocalDate inputDate(String print, String error) {
                LocalDate value;
                while (true) {
                        try {
                                System.out.print(print);
                                value = LocalDate.parse(input.nextLine().trim());
                                break;
                        } catch (DateTimeParseException ex) {
                                System.err.print(error);
                                continue;
                        }
                }
                return value;
        }

        public static LocalDate inputDate(String print){
                return inputDate(print, printInABoxError("Input must be an date!",85));
        }

        public static LocalDate inputDateln(String print) {
                return inputDate(print + "\n");
        }

        public static LocalDate inputDateln(String print, String error) {
                return inputDate(print + "\n", error + "\n");
        }

        public static String printInABox(String title,String content,int gutterOffset,int width,boolean tailed){
                StringBuilder value=new StringBuilder();
                value.append("╔");
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append("╗\n");
                value.append("║");
                int space = width-title.length();
                for(int i = 0;i<space/2;i++){
                        value.append(" ");
                }
                value.append(title);
                for(int i = 0;i<(space/2)+(space%2);i++){
                        value.append(" ");
                }
                value.append("║\n");
                value.append("║╒");
                for(int i = 1;i<width-1;i++){
                        value.append(i==gutterOffset?"╤":"═");
                }
                value.append("╕║\n");
                for(String ln : content.split("\n")){
                        value.append("║│");
                        int spaceIn = width-ln.length()-2;
                        value.append(ln);
                        for(int i = 0;i<spaceIn;i++){
                                value.append(" ");
                        }
                        value.append("│║\n");
                }
                value.append(tailed?"╠╧":"╚╧");
                for(int i = 1;i<width-1;i++){
                        value.append(i==gutterOffset?"╧":"═");
                }
                value.append(tailed?"╧╣\n":"╧╝\n");
                return value.toString();
        }
        public static String printInABox(String title,ArrayList<String> content,int gutterOffset,int width,boolean tailed){
                StringBuilder value=new StringBuilder();
                for(int i=0;i<content.size();i++){
                        value.append(content.get(i)+"\n");
                }
                return printInABox(title, value.toString(), gutterOffset, width, tailed);
        }
        public static String printInABox(String title,HashMap<Integer,String> content,int gutterOffset,int width,boolean tailed){
                StringBuilder value=new StringBuilder();
                for(Integer key:content.keySet()){
                        if(key==0) continue;
                        String keystr=key.toString();
                        for(int i=keystr.length();i<gutterOffset-2;i++){
                                keystr=" "+keystr;
                        }
                        value.append(keystr+")│"+content.get(key)+"\n");
                }
                if(content.containsKey(0)){
                        Integer key=0;
                        String keystr=key.toString();
                        for(int i=keystr.length();i<gutterOffset-2;i++){
                                keystr=" "+keystr;
                        }
                        value.append(keystr+")│"+content.get(key)+"\n");
                }
                return printInABox(title, value.toString(), gutterOffset, width, tailed);
        }
        public static String printInABoxError(String title,int width,boolean tailed){
                StringBuilder value=new StringBuilder();
                value.append("\23341m"+(tailed?"╠":"╔"));
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append((tailed?"╣":"╗")+"\2330m\n");
                value.append("\23341m║");
                int space = width-title.length()-2;
                value.append(space%2==1?" ":"");
                for(int i = 0;i<space/4;i++){
                        value.append("🯀 ");
                }
                value.append(" "+title+" ");
                for(int i = 0;i<(space/4)+(space%4)/2;i++){
                        value.append("🯀 ");
                }
                value.append("║\2330m\n");
                value.append("\23341m"+(tailed?"╠":"╚"));
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append((tailed?"╣":"╝")+"\2330m\n");
                return value.toString();
        }
        public static String printInABoxError(String title,int width){
                return printInABoxError(title,width,false);
        }
        public static String printInABoxGreen(String title,int width){
                StringBuilder value=new StringBuilder();
                value.append("\23342m╔");
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append("╗\2330m\n");
                value.append("\23342m║\23330m");
                int space = width-title.length();
                for(int i = 0;i<space/2;i++){
                        value.append(" ");
                }
                value.append(title);
                for(int i = 0;i<(space/2)+(space%2);i++){
                        value.append(" ");
                }
                value.append("\2330m\23342m║\2330m\n");
                value.append("\23342m╚");
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append("╝\2330m\n");
                return value.toString();
        }
        public static String printInABoxMain(String title,String description,String subtitle,String content,int gutterOffset,int width,boolean tailed,int firstMargin,int secondMargin){
                StringBuilder value=new StringBuilder();
                value.append("╔");
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append("╗\n");
                value.append("║");
                for(int i = 0;i<width;i++){
                        value.append("▓");
                }
                value.append("║ █\n");

                value.append("║");
                int space_title = width-title.length();
                for(int i = 0;i<space_title/2;i++){
                        if(i<firstMargin)
                                value.append("▓");
                        else if(i<secondMargin)
                                value.append("▒");
                        else
                                value.append("░");
                }
                value.append(title);
                for(int i = 0;i<(space_title/2)+(space_title%2);i++){
                        if(((space_title/2)+(space_title%2))-i<firstMargin)
                                value.append("▓");
                        else if(((space_title/2)+(space_title%2))-i<secondMargin)
                                value.append("▒");
                        else
                                value.append("░");
                }
                value.append("║ █\n");

                value.append("║");
                for(int i = 0;i<width;i++){
                        if(i<firstMargin-1)
                                value.append("▓");
                        else if(i==(firstMargin-1))
                                value.append("🮜");
                        else if(i==firstMargin)
                                value.append("╔");
                        else if(width-i<firstMargin-1)
                                value.append("▓");
                        else if(width-i>firstMargin&&i>firstMargin)
                                value.append("═");
                        else if(width-i==firstMargin-1)
                                value.append("🮝");
                        else if(width-i==firstMargin)
                                value.append("╗");
                        else
                                value.append(" ");
                }
                value.append("║ █\n");

                value.append("║╒");
                for(int i = 1;i<width-1;i++){
                        if(i<firstMargin)
                                value.append("═");
                        else if(i==firstMargin)
                                value.append("╝");
                        else if(width-i<firstMargin)
                                value.append("═");
                        else if(width-i==firstMargin)
                                value.append("╚");
                        else
                                value.append(" ");
                }
                value.append("╕║ █\n");
                for(String ln : description.split("\n")){
                        value.append("║│ ");
                        int spaceIn = width-ln.length()-3;
                        value.append(ln);
                        for(int i = 0;i<spaceIn;i++){
                                value.append(" ");
                        }
                        value.append("│║ █\n");
                }
                value.append("║╘");
                for(int i = 1;i<width-1;i++){
                        value.append("═");
                }
                value.append("╛║ █\n");

                value.append("║  ");
                int space_sub = width-subtitle.length();
                for(int i = 0;i<space_sub/2;i++){
                        value.append(" ");
                }
                value.append(subtitle);
                for(int i = 0;i<(space_sub/2)+(space_sub%2);i++){
                        value.append(" ");
                }
                value.append("║ █\n");
                value.append("║╒");
                for(int i = 1;i<width-1;i++){
                        value.append(i==gutterOffset?"╤":"═");
                }
                value.append("╕║ █\n");
                for(String ln : content.split("\n")){
                        value.append("║│");
                        int spaceIn = width-ln.length()-2;
                        value.append(ln);
                        for(int i = 0;i<spaceIn;i++){
                                value.append(" ");
                        }
                        value.append("│║ █\n");
                }
                value.append(tailed?"╠╧":"╚╧");
                for(int i = 1;i<width-1;i++){
                        value.append(i==gutterOffset?"╧":"═");
                }
                value.append(tailed?"╧╣ █\n":"╧╝ █\n");
                value.append(" █");
                for(int i = 0;i<width;i++){
                        value.append("█");
                }
                value.append("██\n");
                return value.toString();
        }
        public static String printUsernameBox(){
                return  "╔═════════════════════════════════════════════════════════════════════════════════════╗\n" +
                        "║                     🯇 Please Enter the Username and password🯈                       ║\n" +
                        "║╒═══════════════════════════════════════════════════════════════════════════════════╕║\n" +
                        "║│🯅 🯆 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🮲🮳 🯇 🯉 🯉 🯉 🯉 🯉 🯉 🯈 🯇 🯈 🯇 🯈 🯇 🯈 🯇 │║\n"+
                        "╠╧═══════════════════════════════════════════════════════════════════════════════════╧╣\n" +
                        "║ Username:                                                                           ║\n"+
                        "║ Password:                                                                           ║\n"+
                        "╚═════════════════════════════════════════════════════════════════════════════════════╝\u001B[2A\u001B[74D";
        }
        public static String printUsernameEmptyBox(){
                return  "\u001B[H\u001B[41m╔═════════════════════════════════════════════════════════════════════════════════════╗\u001B[0m\n"+
                        "\u001B[41m║🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 Username cannot be empty 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀         ║\u001B[0m\n"+
                        "\u001B[41m╚═════════════════════════════════════════════════════════════════════════════════════╝\u001B[0m\n";
        }
        public static String printPassBox(){
                return  "║ Password:                                                                           ║\n"+
                        "╚═════════════════════════════════════════════════════════════════════════════════════╝\u001B[1A\u001B[74D";
        }
        public static String printPassEmptyBox(){
                return  "\u001B[41m╠═════════════════════════════════════════════════════════════════════════════════════╣\u001B[0m\n"+
                        "\u001B[41m║🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 Passwords cannot be empty 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀 🯀        ║\u001B[0m\n"+
                        "\u001B[41m╠═════════════════════════════════════════════════════════════════════════════════════╣\u001B[0m\n";
        }
        public static String printInABoxInput(String title,String content,int lineIndex,int gutterOffset,int width,boolean tailed){
                StringBuilder value=new StringBuilder();
                value.append("╔");
                for(int i = 0;i<width;i++){
                        value.append("═");
                }
                value.append("╗\n");
                value.append("║");
                int space = width-title.length();
                for(int i = 0;i<space/2;i++){
                        value.append(" ");
                }
                value.append(title);
                for(int i = 0;i<(space/2)+(space%2);i++){
                        value.append(" ");
                }
                value.append("║\n");
                value.append("║╒");
                for(int i = 1;i<width-1;i++){
                        value.append(i==gutterOffset?"╤":"═");
                }
                value.append("╕║\n");
                String[] lines=content.split("\n");
                for(String ln : lines){
                        value.append("║│");
                        int spaceIn = width-ln.length()-2;
                        value.append(ln);
                        for(int i = 0;i<spaceIn;i++){
                                value.append(" ");
                        }
                        value.append("│║\n");
                }
                value.append(tailed?"╠╧":"╚╧");
                for(int i = 1;i<width-1;i++){
                        value.append(i==gutterOffset?"╧":"═");
                }
                value.append(tailed?"╧╣\n":"╧╝\n");
                value.append("\u001B["+(1+content.split("\n").length-lineIndex)+"A\u001B["+(lines[lineIndex].length()+2)+"C");
                return value.toString();
        }
        public static String printInABoxInput(String title,ArrayList<String> content,int lineIndex,int gutterOffset,int width,boolean tailed){
                StringBuilder value=new StringBuilder();
                for(int i=0;i<content.size();i++){
                        value.append(content.get(i)+"\n");
                }
                return printInABoxInput(title, value.toString(), lineIndex, gutterOffset, width, tailed);
        }
        public static void clearScrean(){
                System.out.print("\033[H\033[2J");
                System.out.flush();
        }
}