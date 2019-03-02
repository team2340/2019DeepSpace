package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotUtils;
import frc.robot.Commands.ArmCommand;
import frc.robot.LimitSwitches.LimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel;


public class ArmSubsystem extends Subsystem {
    static private ArmSubsystem subsystem;

    private ArmSubsystem() {
        System.out.println("CREATED ARM SUBSYSTEM");
        //createArmwheel();
        createArmopenclose();
	}
    // public LimitSwitch limitSwitchesForArms = new LimitSwitch(RobotMap.ARM_OPEN_ID);
    public CANEncoder encoderArm;
    public double maxValueInches = 18;
    public double encoderOffsetOfArm;
    public CANPIDController pidControllerArmOpenClose;

	public static ArmSubsystem getInstance() {
		if (subsystem == null) {
            subsystem = new ArmSubsystem();
		}
		return subsystem;
    }

	private void createArmwheel() {
		try {
			Robot.oi.armwheel = new WPI_TalonSRX(RobotMap.ARM_WHEEL_TAL_ID);
			Robot.oi.armwheel.configPeakOutputForward(1,0); 
			Robot.oi.armwheel.configPeakOutputReverse(-1,0);
		} catch (Exception ex) {
			System.out.println("createArmWheel FAILED");
		}
    }

    private void createArmopenclose() {
		try {
            Robot.oi.armopenclose= new CANSparkMax(RobotMap.ARM_OPEN_CLOSE_TAL_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
            Robot.oi.armopenclose.setIdleMode(IdleMode.kBrake);
            Robot.oi.armopenclose.setSmartCurrentLimit(8);
            pidControllerArmOpenClose=Robot.oi.armopenclose.getPIDController();
            
            // Robot.oi.armopenclose = new WPI_TalonSRX(RobotMap.ARM_OPEN_CLOSE_TAL_ID);
			//Robot.oi.armopenclose.configPeakOutputForward(1,0); 
			//Robot.oi.armopenclose.configPeakOutputReverse(-1,0);
		} catch (Exception ex) {
			System.out.println("createArmopenclose FAILED");
		}
    }

    public  void armMovement(boolean closingArm){
        if (closingArm==true){
            System.out.println("armMovement "+true);
                armsClose();
        }
        else if(closingArm==false){
            System.out.println("armMovment"+false);
            armsOpen();
        }
    }

    public void armsOpen(){
        // System.out.println("amrsOpen is"+Robot.arm.limitSwitchesForArms.read());
        //  if (Robot.arm.limitSwitchesForArms.read() ==false) {
            Robot.oi.armopenclose.setIdleMode(IdleMode.kCoast); 
            Robot.oi.armopenclose.set(-1);
         }
    // }

    public void armsClose(){
        System.out.println("encoderOffsetOfArm"+encoderOffsetOfArm);
        double maxEncoderValue = RobotUtils.getEncPositionFromINArms(maxValueInches);
        System.out.println("maxEncoderValueArms"+maxEncoderValue);
        // if ((Robot.arm.encoderArm.getPosition()+ encoderOffsetOfArm)< maxEncoderValue ){
            Robot.oi.armopenclose.setIdleMode(IdleMode.kCoast); 
            Robot.oi.armopenclose.set(1);
        //  }
    }
    public void armsStop(){
        Robot.oi.armopenclose.setIdleMode(IdleMode.kBrake); 
        Robot.oi.armopenclose.set(0);
    }
    public void armWheelsin(){
        Robot.oi.armwheel.set(-.5);
    }

    public void armWheelsout(){
        Robot.oi.armwheel.set(.5);
    }
    public void armWheelStop(){
        Robot.oi.armwheel.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArmCommand());
    }
}