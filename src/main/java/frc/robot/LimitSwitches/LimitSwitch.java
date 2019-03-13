package frc.robot.LimitSwitches;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class LimitSwitch  {

	private DigitalInput limitSwitch = null;
	public Integer pinnumber ;
    public LimitSwitch(Integer pin) {
		pinnumber=pin;
		System.out.print("pin number"+pin);
    	limitSwitch = new DigitalInput(pin);
    }

    public boolean read() {
		System.out.println("limitSwitch "+pinnumber+" "+limitSwitch.get());
		
    	// while (limitSwitch.get()) {
    	// 	Timer.delay(.01);
		// }
		return limitSwitch.get();
	}
}	

