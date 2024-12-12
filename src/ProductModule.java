import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductModule extends Module {
    ProductModule(User currentUser) {
        this.currentUser = currentUser;
    }
    public void startModule() throws IOException {
            int choice = -1;
            do {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    choice = App.inputInt(App.printInABoxMain("Product Module","Hi "+currentUser.getUsername(),"🯇 Please choose one of the following options:🯈",
                                                    "1)│Manage Products\n" +
                                                    "2)│Search Products\n" +
                                                    "3)│View Expirations\n" +
                                                    "0)│Logout",3,85,true,10,20)+
                                                    "║choose🮶 ");
                    switch (choice) {
                            case 0:// exit module
                                    break;
                            case 1:
                                    manageProducts();
                                    break;
                            case 2:
                                    searchProducts();
                                    break;
                            case 3:
                                    viewExpirations();
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
        public void manageProducts() throws IOException {
                int choice = -1;
                do {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        choice = App.inputInt(App.printInABoxMain("Product Module","Hi "+currentUser.getUsername(),"🯇 Please choose one of the following options:🯈",
                                                        "1)│List Products\n" +
                                                        "2)│Add Product\n" +
                                                        "3)│Remove Product\n" +
                                                        "4)│Update Product\n" +
                                                        "0)│Back",3,85,true,10,20)+
                                                        "║choose🮶 ");
                        switch (choice) {
                                case 0:// exit module
                                        break;
                                case 1:
                                        HashMap<Integer,String> plist=new HashMap<>();
                                        for(int i = 0; i<App.productDataHandler.getLength();i++){
                                                plist.put(i, "Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                                        }
                                        System.out.println(App.printInABox("Products", plist, 3, 85, false));
                                        App.input.nextLine();
                                        break;
                                case 2:
                                        App.clearScrean();
                                        String name=App.inputString(App.printInABoxInput("add product", "Name: \nQuantity: \nProduction Date: \nExpiry Date: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                        App.clearScrean();
                                        int quantity=App.inputInt(App.printInABoxInput("add product", "Name: "+name+"\nQuantity: \nProduction Date: \nExpiry Date: ", 1, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must be an integer!",85));
                                        App.clearScrean();
                                        LocalDate prod=App.inputDate(App.printInABoxInput("add product", "Name: "+name+"\nQuantity: "+quantity+"\nProduction Date: \nExpiry Date: ", 2, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must be an date!",85));
                                        App.clearScrean();
                                        LocalDate expiry=App.inputDate(App.printInABoxInput("add product", "Name: "+name+"\nQuantity: "+quantity+"\nProduction Date: "+prod+"\nExpiry Date: ", 3, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must be an date!",85));
                                        App.clearScrean();
                                        HashMap<Integer,String> list=new HashMap<>();
                                        for(int i = 0; i<App.productCategoryDataHandler.getLength();i++){
                                                list.put(i, App.productCategoryDataHandler.get(i).getName());
                                        }
                                        Integer[] indices=App.inputInts(App.productCategoryDataHandler.getLength(),App.printInABox("add product",list,3, 85, true)+"║choose🮶 ");
                                        ProductCategory[] cats=new ProductCategory[indices.length];
                                        for (int i=0;i<indices.length;i++) {
                                                cats[i]=(App.productCategoryDataHandler.get(indices[i]));
                                        }
                                        App.productDataHandler.add(new Product(cats, name, prod, expiry, quantity));
                                        System.out.println(App.printInABoxGreen("Added!", 85));
                                        App.input.nextLine();
                                        break;
                                case 3:
                                        HashMap<Integer,String> rlist=new HashMap<>();
                                        rlist.put(0,"Back");
                                        for(int ir = 0; ir<App.productDataHandler.getLength();ir++){
                                                rlist.put(ir+1, "Name: "+App.productDataHandler.get(ir).getName());
                                        }
                                        int to_del=App.inputInt(App.printInABox("Products", rlist, 3, 85, false)+"║choose🮶 ");
                                        if(to_del==0)break;
                                        if(to_del-1>=App.productDataHandler.getLength()){
                                                System.err.println(App.printInABoxError("Unknown choice!", 85));
                                                break;
                                        }
                                        App.productDataHandler.delete(to_del-1);
                                        System.out.println(App.printInABoxGreen("Removed!", 85));
                                        App.input.nextLine();
                                        break;
                                case 4:
                                        HashMap<Integer,String> ulist=new HashMap<>();
                                        ulist.put(0,"Back");
                                        for(int iu = 0; iu<App.productDataHandler.getLength();iu++){
                                                ulist.put(iu+1, "Name: "+App.productDataHandler.get(iu).getName());
                                        }
                                        int to_upd=App.inputInt(App.printInABox("Products", ulist, 3, 85, false)+"║choose🮶 ");
                                        if(to_upd==0)break;
                                        if(to_upd-1>=App.productDataHandler.getLength()){
                                                System.err.println(App.printInABoxError("Unknown choice!", 85));
                                                break;
                                        }
                                        int to_upd_item=App.inputInt(App.printInABox("Products", "0)│Back\n"+
                                                                                                "1)│Name\n" + //
                                                                                                "2)│Quantity\n" + //
                                                                                                "3)│Production Date\n" + //
                                                                                                "4)│Expiry Date\n"+
                                                                                                "5)│Category", 3, 85, false)+"║choose🮶 ");
                                        if(to_upd_item==0)break;
                                        if(to_upd_item>5){
                                                System.err.println(App.printInABoxError("Unknown choice!", 85));
                                                break;
                                        }
                                        switch(to_upd_item){
                                                case 1:{
                                                        App.clearScrean();
                                                        String nameu=App.inputString(App.printInABoxInput("update product", "Name: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                                        App.productDataHandler.get(to_upd-1).setName(nameu);
                                                        App.productDataHandler.update(to_upd-1,App.productDataHandler.get(to_upd-1));
                                                }break;
                                                case 2:{
                                                        App.clearScrean();
                                                        int quantityu=App.inputInt(App.printInABoxInput("update product", "Quantity: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                                        App.productDataHandler.get(to_upd-1).setQuantity(quantityu);
                                                        App.productDataHandler.update(to_upd-1,App.productDataHandler.get(to_upd-1));
                                                }break;
                                                case 3:{
                                                        App.clearScrean();
                                                        LocalDate produ=App.inputDate(App.printInABoxInput("update product", "Production Date: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                                        App.productDataHandler.get(to_upd-1).setProductionDate(produ);
                                                        App.productDataHandler.update(to_upd-1,App.productDataHandler.get(to_upd-1));
                                                }break;
                                                case 4:{
                                                        App.clearScrean();
                                                        LocalDate expu=App.inputDate(App.printInABoxInput("update product", "Expiry Date: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                                        App.productDataHandler.get(to_upd-1).setExpirationDate(expu);
                                                        App.productDataHandler.update(to_upd-1,App.productDataHandler.get(to_upd-1));
                                                }break;
                                                case 5:{
                                                        App.clearScrean();
                                                        HashMap<Integer,String> clist=new HashMap<>();
                                                        for(int i = 0; i<App.productCategoryDataHandler.getLength();i++){
                                                                clist.put(i, App.productCategoryDataHandler.get(i).getName());
                                                        }
                                                        Integer[] indicesu=App.inputInts(App.productCategoryDataHandler.getLength(),App.printInABox("update product",clist,3, 85, true)+"║choose🮶 ");
                                                        ProductCategory[] catsu=new ProductCategory[indicesu.length];
                                                        for (int i=0;i<indicesu.length;i++) {
                                                                catsu[i]=(App.productCategoryDataHandler.get(indicesu[i]));
                                                        }
                                                        App.productDataHandler.get(to_upd-1).setCategories(catsu);
                                                        App.productDataHandler.update(to_upd-1,App.productDataHandler.get(to_upd-1));
                                                }break;
                                        }
                                        System.out.println(App.printInABoxGreen("Updated!", 85));
                                        App.input.nextLine();
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
        public void searchProducts() throws IOException {
                int to_search_item=App.inputInt(App.printInABox("Products", "0)│Back\n"+
                                                                        "1)│Name\n" + //
                                                                        "2)│Production Date\n" + //
                                                                        "3)│Expiry Date\n"+
                                                                        "4)│Category", 3, 85, false)+"║choose🮶 ");
                switch (to_search_item) {
                        case 0:
                                return;
                        case 1: {
                                App.clearScrean();
                                String nameu=App.inputString(App.printInABoxInput("search product", "Name: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                HashMap<Integer,String> plist=new HashMap<>();
                                for(int i = 0; i<App.productDataHandler.getLength();i++){
                                        Product item=App.productDataHandler.get(i);
                                        if(item.getName().contains(nameu))
                                                plist.put(i, "Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                                }
                                System.out.println(App.printInABox("Products", plist, 3, 85, false));
                                App.input.nextLine();
                        }break;
                        case 2: {
                                App.clearScrean();
                                LocalDate produ=App.inputDate(App.printInABoxInput("search product", "Production Date: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                HashMap<Integer,String> plist=new HashMap<>();
                                for(int i = 0; i<App.productDataHandler.getLength();i++){
                                        Product item=App.productDataHandler.get(i);
                                        if(item.getProductionDate().equals(produ))
                                                plist.put(i, "Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                                }
                                System.out.println(App.printInABox("Products", plist, 3, 85, false));
                                App.input.nextLine();
                        }break;
                        case 3: {
                                App.clearScrean();
                                LocalDate exp=App.inputDate(App.printInABoxInput("search product", "Expiry Date: ", 0, 0, 85, true), "\033[H\033[2J"+App.printInABoxError("Input must not be empty!",85));
                                HashMap<Integer,String> plist=new HashMap<>();
                                for(int i = 0; i<App.productDataHandler.getLength();i++){
                                        Product item=App.productDataHandler.get(i);
                                        if(item.getExpirationDate().equals(exp))
                                                plist.put(i, "Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                                }
                                System.out.println(App.printInABox("Products", plist, 3, 85, false));
                                App.input.nextLine();
                        }break;
                        case 4: {
                                App.clearScrean();
                                HashMap<Integer,String> ulist=new HashMap<>();
                                ulist.put(0,"Back");
                                for(int iu = 0; iu<App.productCategoryDataHandler.getLength();iu++){
                                        ulist.put(iu+1, "Name: "+App.productCategoryDataHandler.get(iu).getName());
                                }
                                int to_upd=App.inputInt(App.printInABox("Product Categories", ulist, 3, 85, false)+"║choose🮶 ");
                                if(to_upd==0)break;
                                if(to_upd-1>=App.productCategoryDataHandler.getLength()){
                                        System.err.println(App.printInABoxError("Unknown choice!", 85));
                                        break;
                                }
                                System.out.println(App.productCategoryDataHandler.get(to_upd-1).getName());
                                HashMap<Integer,String> plist=new HashMap<>();
                                for(int i = 0; i<App.productDataHandler.getLength();i++){
                                        Product item=App.productDataHandler.get(i);
                                        boolean f=false;
                                        for (ProductCategory productCategory : item.getCategories()) {
                                                System.out.println(productCategory.getName());
                                                if(productCategory.equals(App.productCategoryDataHandler.get(to_upd-1))){
                                                        f=true;
                                                        break;
                                                }
                                        }
                                        if(f)
                                                plist.put(i, "Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                                }
                                System.out.println(App.printInABox("Products", plist, 3, 85, false));
                                App.input.nextLine();
                        }break;
                        default:
                                break;
                }
                
        }
        public void viewExpirations() throws IOException {
                HashMap<Integer,String> plist=new HashMap<>();
                for(int i = 0; i<App.productDataHandler.getLength();i++){
                        Product item=App.productDataHandler.get(i);
                        if(item.getExpirationDate().minusMonths(1).isBefore(LocalDate.now()))
                                plist.put(i, "Near exepiry: Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                        if(item.getQuantity()<5)
                                plist.put(i, "Less than 5 items: Name: "+App.productDataHandler.get(i).getName()+"  Quantity: "+App.productDataHandler.get(i).getQuantity()+"  Production Date: "+App.productDataHandler.get(i).getProductionDate()+"  Expiry Date: "+App.productDataHandler.get(i).getExpirationDate()+"  Categories: "+App.productDataHandler.get(i).getCategoryNames());
                }
                System.out.println(App.printInABox("Products", plist, 3, 85, false));
                App.input.nextLine();
        }
}
