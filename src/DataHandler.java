import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandler<T extends StrSerializable> {
    private String filePath;
    private File file;
    private ObjectOutputStream fileWriter;
    private ObjectInputStream fileReader;
    private ArrayList<T> list;

    //@SuppressWarnings("unchecked")
    public DataHandler(String path, T oi) throws IOException,ClassNotFoundException{
        filePath=path;
        file=new File(filePath);
        file.createNewFile();
        list = new ArrayList<T>();
        Object line;
        FileInputStream fis=new FileInputStream(file);
        try{
            fileReader = new ObjectInputStream(fis);
            System.out.println("fis:"+fis.available()+",fr:"+fileReader.available());
            while(true)
            {
                try {
                    line = fileReader.readObject();
                    list.add((T)line);
                    System.out.println(line);
                } catch(EOFException ex) {
                    System.out.println("done!");
                    break;
                }
            }  
            fileReader.close();
        }catch(EOFException ex){
            System.out.println("empty!!");
        }
        fis.close();
    }
    private void save() throws IOException{
        System.out.println("saving!");
        FileOutputStream fos = new FileOutputStream(file);
        fileWriter = new ObjectOutputStream(fos);
        for (T item : list) {
            fileWriter.writeObject(item);
            System.out.println("wrote:"+item);
        }
        fileWriter.close();
        fos.close();
    }
    public T get(int index){
        return list.get(index);
    }
    public int getIndex(T object){
        return list.indexOf(object);
    }
    public int getLength(){
        return list.size();
    }
    public void add(T item) throws IOException{
        list.add(item);
        save();
    }
    public void update(int index, T item) throws IOException{
        list.set(index,item);
        save();
    }
    public void delete(int index) throws IOException{
        list.remove(index);
        save();
    }
    public T[] listAll(T[] a){
        return list.toArray(a);
    }
}