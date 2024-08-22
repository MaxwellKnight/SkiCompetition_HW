package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;


/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */

public class Snowbiker extends  WinterSportsman{
    public Snowbiker(String name, double age, Gender gender,
                       double acceleration, double maxSpeed, Discipline discipline){
        super(name, age, gender, acceleration, maxSpeed, discipline);
    }

    public String toString(){
        return "Snowbiker " + this.name;
    }
}
