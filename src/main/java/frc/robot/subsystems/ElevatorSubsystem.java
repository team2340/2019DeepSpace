package frc.robot.subsystems;

import frc.robot.Commands.ElevatorCommand;
import frc.robot.LimitSwitches.LimitSwitch;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotUtils;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;


public class ElevatorSubsystem extends Subsystem {
	public LimitSwitch limitSwitchesAtBottomOfElevatorOne = new LimitSwitch(RobotMap.ELEVATOR1_BOTTOM_ID);
	//limitSwitchesAtZero is the bottom of ele 1
	public LimitSwitch limitSwitchAtBottomOfElevatorTwo = new LimitSwitch(RobotMap.ELEVATOR2_BOTTOM_ID);
	//limitSwitchesAtOne is the bottom of ele 2
	// LimitSwitches limitSwitchesAtTwo = new LimitSwitches(2);
	// //limitSwitchesAtTwo is the top of ele 1
	// LimitSwitches limitSwitchesAtThree = new LimitSwitches(3);
	// //limitSwitchesAtThree is the top of ele 2
	static private ElevatorSubsystem subsystem;
	public double positionP = 0.08525; //25% power at 3000 error

	public CANEncoder encoderOne;
	public CANEncoder encoderTwo;
	public CANPIDController pidControllerElevatorOne;
	public CANPIDController pidControllerElevatorTwo;
	public double maxHeightOfElevator1 = 40.25;
	public double maxHeightOfElevator2 = 42.75;
	public double encoderOffsetOfElevatorOne;
	public double encoderOffsetOfElevatorTwo;
	

	private ElevatorSubsystem() {
		createElevator();
		createElevator2();
	}

	public static ElevatorSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new ElevatorSubsystem();
		}
		return subsystem;
	}
	
	private void createElevator() {
		try {
			Robot.oi.elevator1 = new CANSparkMax(RobotMap.ELEVATOR_TAL_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
			// Robot.oi.elevator1.setIdleMode(IdleMode.kBrake); //if motor not moving
			Robot.oi.elevator1.setSmartCurrentLimit(8);
			encoderOne = Robot.oi.elevator1.getEncoder();
			pidControllerElevatorOne=Robot.oi.elevator1.getPIDController();
		} catch (Exception ex) {
			System.out.println("createElevator FAILED");
		}
	}

	private void createElevator2() {
		try {
			Robot.oi.elevator2 = new CANSparkMax(RobotMap.ELEVATOR2_TAL_ID,CANSparkMaxLowLevel.MotorType.kBrushless);
			// Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
			Robot.oi.elevator2.setInverted(true);
			Robot.oi.elevator2.setSmartCurrentLimit(8);
			encoderTwo = Robot.oi.elevator2.getEncoder();
			pidControllerElevatorTwo=Robot.oi.elevator2.getPIDController();
		} catch (Exception ex) {
			System.out.println("createElevator2 FAILED");
		}
	}

	public void moveUpOrDown(double num){
		double range = 0.3;
		if(num>range){//we want to move up
			if (checkMaxOfElevatorTwo() == false){//if elevator one is not all the way at thr bottom
			
				// SmartDashboard.putNumber("EncoderTwoPosition", Robot.elevator.encoderTwo);
				if(checkMaxOfElevatorOne()==true){//if elevator two is all the way at the bottom
					Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
					Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);
					Robot.elevator.move2(num);//move elevator two up
				}
				// SmartDashboard.putNumber("EncoderTwoPosition", Robot.elevator.encoderTwo);
				else{// if elevator One is all the way down
					Robot.oi.elevator1.setIdleMode(IdleMode.kCoast);
					Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
					Robot.elevator.move1(num);//move elevator two up
				}
				// SmartDashboard.putNumber("EncoderOnePosition", Robot.elevator.encoderOne);
			}
		}
		else if(num<range){ //going down// + is down
			if (limitSwitchesAtBottomOfElevatorOne.read() == false){
				if (limitSwitchAtBottomOfElevatorTwo.read() == true) {
					Robot.oi.elevator1.setIdleMode(IdleMode.kCoast);
					Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
					Robot.elevator.move1(num);
				}
				else{
					Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
					Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);
					Robot.elevator.move2(num);
				}
			}
		}
		else{
			Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
			Robot.elevator.move1(0);
			Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
			Robot.elevator.move2(0);
		}
	}
	
	public void move1(double amt) {
		if(amt==0){

			Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
			//set to beak mode
		}
		else{
			Robot.oi.elevator1.setIdleMode(IdleMode.kCoast);
			//set to coast mode
		}
		Robot.oi.elevator1.set(amt);
	}
	public void move2(double amt) {
		if(amt==0){
			Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
			//set to brack mode
		}
		else{
			Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);
			//set to coast mode
		}
		Robot.oi.elevator2.set(amt);
	}

	public void movePosition(double desiredHeight){
		//desiredHeight is in Inches, while desiredEncoderPosition is in encoder counts, real
		//desiredEncoderPosition should be used when doing the math below, real, where we want to locate
		double desiredEncoderPostionOfElevatorOne = RobotUtils.getEncPositionFromINElevatorOne(desiredHeight-(RobotUtils.getHeightOfRobotFromTheGround() ));	
		double desiredEncoderPostionOfElevatorTwo = RobotUtils.getEncPositionFromINElevatorTwo(desiredHeight-(RobotUtils.getHeightOfRobotFromTheGround() ));	
		double currentHeight = RobotUtils.getDistanceInInchesFromElevatorOne(encoderOne.getPosition()-encoderOffsetOfElevatorOne)+RobotUtils.getDistanceInInchesFromElevatorTwo(encoderTwo.getPosition()-encoderOffsetOfElevatorTwo);
		 double rangeForCurrentHeight = 1;
		if((currentHeight<=(rangeForCurrentHeight+currentHeight))&&(currentHeight >= (rangeForCurrentHeight- currentHeight))){
			Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
			Robot.oi.elevator1.stopMotor();
			// add in a breack
			Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
			Robot.oi.elevator2.stopMotor();
			//add in breack
		}
		else
		{
			if (currentHeight>desiredHeight){
				if (limitSwitchAtBottomOfElevatorTwo.read()==true){
					
					Robot.oi.elevator1.setIdleMode(IdleMode.kCoast);
					Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
				
					pidControllerElevatorOne.setReference(desiredEncoderPostionOfElevatorOne-RobotUtils.getEncPositionFromINElevatorOne(currentHeight)+Robot.elevator.encoderOffsetOfElevatorOne, ControlType.kPosition);
				}
				else{
					Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
					Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);
			
					double desiredHeightOfElevatorTwo = (desiredEncoderPostionOfElevatorTwo-(encoderOne.getPosition()-encoderOffsetOfElevatorOne));
					if (desiredHeightOfElevatorTwo > 0){
						
						pidControllerElevatorTwo.setReference(-1*desiredHeightOfElevatorTwo + Robot.elevator.encoderOffsetOfElevatorTwo, ControlType.kPosition);// must be * -1
					}
					else{
						pidControllerElevatorTwo.setReference(Robot.elevator.encoderOffsetOfElevatorTwo, ControlType.kPosition);//to 0
					}
				}
			}
			else if (currentHeight<desiredHeight) {
				if (checkMaxOfElevatorOne()==true) {//when ele 1 is all the way up
					Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
					Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);

					pidControllerElevatorTwo.setReference(desiredEncoderPostionOfElevatorTwo-RobotUtils.getEncPositionFromINElevatorTwo(currentHeight) + Robot.elevator.encoderOffsetOfElevatorTwo, ControlType.kPosition);
				}
				else {
					double desiredHeightOfElevatorOne = (desiredEncoderPostionOfElevatorOne-(encoderTwo.getPosition()-encoderOffsetOfElevatorTwo));
					double maxEncoderElevator1 = RobotUtils.getEncPositionFromINElevatorOne(maxHeightOfElevator1)-encoderOffsetOfElevatorOne;//add max encoder hieght of elevator one
					Robot.oi.elevator1.setIdleMode(IdleMode.kCoast);
					Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
					if (desiredHeightOfElevatorOne < maxEncoderElevator1){
						pidControllerElevatorOne.setReference(desiredHeightOfElevatorOne+Robot.elevator.encoderOffsetOfElevatorOne, ControlType.kPosition);//move ele 1 with the amount of desieredHeightOfElevatorOne.
					}
					else{//When it requires ele 2
						pidControllerElevatorOne.setReference(maxEncoderElevator1+Robot.elevator.encoderOffsetOfElevatorOne, ControlType.kPosition);//move ele 1 to its max, which is maxElevator_1.
					}
			    }	
			}
			//else it is equal!
		}		
	}

	public boolean checkMaxOfElevatorOne()
	{
		double currentHeightOfOne = encoderOne.getPosition();
		double maxEncoderElevator1 = RobotUtils.getEncPositionFromINElevatorOne(maxHeightOfElevator1);//add max encoder hieght of elevator one
		double wiggelRoomInEncoderValuesForTheHeightOfOne = 10;
		System.out.println("current Height Of One "+currentHeightOfOne);
		System.out.println("maxEncoderElevator1"+maxEncoderElevator1);
		System.out.println("wiggelRoomInEncoderVaulesForTheHeightOfOne" + wiggelRoomInEncoderValuesForTheHeightOfOne);
		if((currentHeightOfOne <= (maxEncoderElevator1 + wiggelRoomInEncoderValuesForTheHeightOfOne ))&&(currentHeightOfOne >= (maxEncoderElevator1 - wiggelRoomInEncoderValuesForTheHeightOfOne))){
			System.out.println("currentHeightOfOne is true"+true);
			return true;
		}
		else{
			System.out.println("currentHeightOfOne is true"+false);
			return false;
		}

	}
	public boolean checkMaxOfElevatorTwo()
	{
		
		double currentHeightOfTwo = encoderTwo.getPosition();
		double maxEncoderElevator2 = RobotUtils.getEncPositionFromINElevatorTwo(maxHeightOfElevator2);//add max encoder hieght of elevator one
		double wiggelRoomInEncoderValuesForTheHeightOfTwo = 10;
		System.out.println("current Height Of Two "+currentHeightOfTwo);
		System.out.println("maxEncoderElevator2"+maxEncoderElevator2);
		System.out.println("wiggelRoomInEncoderVaulesForTheHeightOfTwo" + wiggelRoomInEncoderValuesForTheHeightOfTwo);
		if((currentHeightOfTwo<= (maxEncoderElevator2 + wiggelRoomInEncoderValuesForTheHeightOfTwo ))&&(currentHeightOfTwo >= (maxEncoderElevator2 - wiggelRoomInEncoderValuesForTheHeightOfTwo))){
			System.out.println("currentHeightOfTwo is true"+true);
			return true;

		}
		else{
			System.out.println("currentHeightOfTwo is true"+false);

			return false;
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ElevatorCommand());
	}
}
