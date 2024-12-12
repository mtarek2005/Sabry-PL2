import java.io.IOException;

public class AdminModule extends Module{
    AdminModule(User currentUser) {
        this.currentUser = currentUser;
    }
    public void startModule() throws IOException {
        int choice = -1;
        do {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                choice = App.inputInt(App.printInABoxMain("Admin Module","Hi "+currentUser.getUsername(),"🯇 Please choose one of the following options:🯈",
                                                "1)│Manage Suppliers\n" +
                                                "2)│Manage Clients\n" +
                                                "3)│Manage Categories\n" +
                                                "4)│Manage Offers\n" +
                                                "5)│Generate Reports\n" +
                                                "0)│Logout",3,85,true,10,20)+
                                                "║choose🮶 ");
                switch (choice) {
                        case 0:// exit module
                                break;
                        case 1:
                                manageSuppliers();
                                break;
                        case 2:
                                manageClients();
                                break;
                        case 3:
                                manageCategories();
                                break;
                        case 4:
                                manageOffers();
                                break;
                        case 5:
                                generateReports();
                                break;
                        default:
                                System.err.println(App.printInABoxError("Invalid Operation!",85,true)+"Press Enter to continue...");
                                App.input.nextLine();
                                break;
                }
        } while (choice != 0);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void manageSuppliers() throws IOException {

    }
    public void manageClients() throws IOException {

    }
    public void manageCategories() throws IOException {

    }
    public void manageOffers() throws IOException {

    }
    public void generateReports() throws IOException {

    }
}
