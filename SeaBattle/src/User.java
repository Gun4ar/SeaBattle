import java.util.Scanner;

/**
 * Created by Bogachevy on 25.12.2017.
 */
public class User {
    private String name;

    public User() {
        String name =null;
        System.out.println("Hello, my dear friend!");
        System.out.println("Please, introduce yourself:");
        Scanner scanner = new Scanner(System.in);
        try {
            name = scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.name = name;
        toString();
        System.out.println();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Our new Player is: " + getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void putShips(){
        System.out.println("If you want to set ships by yourself print 1: ");
        Scanner scanner = new Scanner(System.in);
        int number=0;
        try {
            number = scanner.nextInt();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (number==1){
            System.out.println("Ships are put by User");

        }else {
            NavyFactory navyFactory = new NavyFactory();
            Field field = new Field(10, 10);
            field.createBattleFild();
            field.drawField();
            navyFactory.createNavy();
        }
    }
}
