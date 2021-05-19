import utils.generators.PublicationGenerator;

public class App {
    public static void main(String[] args) {
        while(true){
            System.out.println(PublicationGenerator.createNewPublication().toString());
        }
    }
}