/**
 * Интерфейс Стратегий
 *
 * @author Илья Богачев
 * @since 28.01.2018
 */
public interface Strategy {

   default boolean fight(){
       System.out.println("Default Player Fight method");
       return false;
   }

   default boolean findShip(){
       System.out.println("Default Computer FindShips method");
       return false;
   }

    default Point killWoundedShip(Point point){
        System.out.println("Default kill wounded ship");
        return point;
    }


}
