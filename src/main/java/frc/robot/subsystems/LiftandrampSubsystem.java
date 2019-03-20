package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.LiftAndRampCommand;

public class LiftAndRampSubsystem extends Subsystem {
    static private LiftAndRampSubsystem subsystem;

    private LiftAndRampSubsystem() {
        createramp();
        createLift();
	}

    public static LiftAndRampSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new LiftAndRampSubsystem();
		}
		return subsystem;
    }

	private void createLift() {
		try {
			Robot.oi.lift = new WPI_TalonSRX(RobotMap.LIFT_TAL_ID);
			Robot.oi.lift.configPeakOutputForward(1); 
            Robot.oi.lift.configPeakOutputReverse(-1);
		} catch (Exception ex) {
			System.out.println("createlift FAILED");
		}
    }

    private void createramp() {
		try {
			Robot.oi.ramp = new WPI_TalonSRX(RobotMap.RAMP_TAL_ID);
			Robot.oi.ramp.configPeakOutputForward(1); 
			Robot.oi.ramp.configPeakOutputReverse(-1);
		} catch (Exception ex) {
			System.out.println("createramp FAILED");
		}
    }
    
    public void liftup(){
        Robot.oi.lift.set(.75);
    }

    public void liftdown(){
        Robot.oi.lift.set(-.75);
    }
    
    public void liftstop(){
        Robot.oi.lift.set(0);
    }

    public void rampDown(){
        Robot.oi.ramp.set(-1);
    }

    public void rampstop(){
        Robot.oi.ramp.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftAndRampCommand());
    }
}