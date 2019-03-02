package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.LiftandrampCommand;

public class LiftandrampSubsystem extends Subsystem {
    static private LiftandrampSubsystem subsystem;

    private LiftandrampSubsystem() {
        createramp();
        createLift();
	}

    public static LiftandrampSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new LiftandrampSubsystem();
		}
		return subsystem;
    }

	private void createLift() {
		try {
			Robot.oi.lift = new WPI_TalonSRX(RobotMap.LIFT_TAL_ID);
			Robot.oi.lift.configPeakOutputForward(1,0); 
			Robot.oi.lift.configPeakOutputReverse(-1,0);
		} catch (Exception ex) {
			System.out.println("createlift FAILED");
		}
    }

    private void createramp() {
		try {
			Robot.oi.ramp = new WPI_TalonSRX(RobotMap.RAMP_TAL_ID);
			Robot.oi.ramp.configPeakOutputForward(1,0); 
			Robot.oi.ramp.configPeakOutputReverse(-1,0);
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
    public void rampup(){
        Robot.oi.ramp.set(1);
    }

    public void rampdown(){
        Robot.oi.ramp.set(-1);
    }
    public void rampstop(){
        Robot.oi.ramp.set(0);
    }

    public double getRampAngle(){
        double angle = 0;//Math.abs(Robot.oi.gyro.getAngle());
        SmartDashboard.putNumber("Gyro angle", angle);
		// Robot.myLogger.log("Rotation","angle", angle);
        return angle;
    } 

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftandrampCommand());
    }

}