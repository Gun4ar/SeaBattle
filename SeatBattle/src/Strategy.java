/**
 * Класс Стратегий
 *
 * @author Илья Богачев
 * @since 05.02.2018
 */
public class Strategy {

   public boolean fight(){
       System.out.println("Default Player Fight method");
       return false;
   }

   public boolean findShip(){
       System.out.println("Default Computer FindShips method");
       return false;
   }

    public Point killWoundedShip(Point point){
        System.out.println("Default kill wounded ship");
        return point;
    }
}
