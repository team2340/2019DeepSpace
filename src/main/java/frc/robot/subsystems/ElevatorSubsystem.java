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
	public LimitSwitch limitSwitchAtBottomOfElevatorOne = new LimitSwitch(RobotMap.ELEVATOR1_BOTTOM_ID);
	public LimitSwitch limitSwitchAtBottomOfElevatorTwo = new LimitSwitch(RobotMap.ELEVATOR2_BOTTOM_ID);
	static private ElevatorSubsystem subsystem;
	public double positionP = 0.08525; //25% power at 3000 error

	public CANEncoder encoderOne;
	public CANEncoder encoderTwo;
	public CANPIDController pidControllerElevatorOne;
	public CANPIDController pidControllerElevatorTwo;
	public double maxHeightOfElevator1 = 33;
	public double maxHeightOfElevator2 = 34.5;
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
			Robot.oi.elevator1 = new CANSparkMax(RobotMap.ELEVATOR_NEO_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
			Robot.oi.elevator1.setIdleMode(IdleMode.kBrake); //if motor not moving
			Robot.oi.elevator1.setSmartCurrentLimit(40);
			encoderOne = Robot.oi.elevator1.getEncoder();
			encoderOne.setPositionConversionFactor(1.0/1.57); //Small gear on motor, big gear on elevator
			pidControllerElevatorOne=Robot.oi.elevator1.getPIDController();
		} catch (Exception ex) {
			System.out.println("createElevator FAILED");
		}
	}

	private void createElevator2() {
		try {
			Robot.oi.elevator2 = new CANSparkMax(RobotMap.ELEVATOR2_NEO_ID,CANSparkMaxLowLevel.MotorType.kBrushless);
			Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
			Robot.oi.elevator2.setInverted(true);
			Robot.oi.elevator2.setSmartCurrentLimit(40);
			encoderTwo = Robot.oi.elevator2.getEncoder();
			encoderTwo.setPositionConversionFactor(1.0/2.0); //Small gear on motor, big gear on elevator
			pidControllerElevatorTwo=Robot.oi.elevator2.getPIDController();
		} catch (Exception ex) {
			System.out.println("createElevator2 FAILED");
		}
	}

	public void moveUpOrDown(double num){
		double range = 0.1;
		// System.out.println("num " + num);
		if(num>range){//we want to move up
			// System.out.println("num is greater then range" );
			if(checkMaxOfElevatorTwo() == false){//if elevator one is not all the way at the bottom
				// System.out.println("Elevator two is not all the way up" );

				if(checkMaxOfElevatorOne()==true){//if elevator two is all the way at the bottom
					// System.out.println("elevator one is all the way up" );

					Robot.elevator.move1(0);
					Robot.elevator.move2(num);//move elevator two up
				}
				else
				{ //if elevator One is all the way down
					// System.out.println("elevator one is not all the way up" );

					Robot.elevator.move1(num);//move elevator two up
					Robot.elevator.move2(0);
				}
			}
			else
			{
				// System.out.println("everyone is all the way up" );

				Robot.elevator.move1(0);
				Robot.elevator.move2(0);
			}
		}
		else if(num<-range){ //going down// + is down
			// System.out.println("num is less then range");
			if (limitSwitchAtBottomOfElevatorOne.read() == false){
				// System.out.println("elevator one is not all the way down" );

				if (limitSwitchAtBottomOfElevatorTwo.read() == true) {
					// System.out.println("elevator two is all the way down" );

					Robot.elevator.move1(num);
					Robot.elevator.move2(0);
				}
		 		else{
					// System.out.println("elevator two is not all the way down" );

					Robot.elevator.move1(0);
					Robot.elevator.move2(num);
		 		}
			 }
			else
			{
				// System.out.println("everyone is all the way down" );

				Robot.elevator.move1(0);
				Robot.elevator.move2(0);
			}
		}
		else{
			// System.out.println("num is set to zero");
			Robot.elevator.move1(0);
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
		desiredHeight = Math.max(RobotUtils.getHeightOfRobotFromTheGround(), desiredHeight);
		System.out.println("desiredHeight-move position"+desiredHeight);
		double elevatorDesiredHeight = desiredHeight-RobotUtils.getHeightOfRobotFromTheGround();
		System.out.println("elevatordesiredHeight-move position"+elevatorDesiredHeight);

		// double PostionOfElevatorTwo = (desiredHeight-(RobotUtils.getHeightOfRobotFromTheGround() ));	
		double currentHeight = (currentHeightElevatorOne())+(currentHeightElevatorTwo());
		System.out.println("currentHeight"+currentHeight);

		double rangeForCurrentHeight = 1;
		System.out.print("cuurrentHeight+range"+(rangeForCurrentHeight+elevatorDesiredHeight));
		if((currentHeight<=(rangeForCurrentHeight+elevatorDesiredHeight))
		   &&(currentHeight >= (rangeForCurrentHeight- elevatorDesiredHeight))){
			System.out.println("done moveing");

			Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
			Robot.oi.elevator1.stopMotor();
			Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
			Robot.oi.elevator2.stopMotor();
		}
		else
		{
			if (currentHeight>elevatorDesiredHeight){
				if (limitSwitchAtBottomOfElevatorTwo.read()==true){
					
					Robot.oi.elevator1.setIdleMode(IdleMode.kCoast);
					Robot.oi.elevator2.setIdleMode(IdleMode.kBrake);
					pidControllerElevatorOne.setReference(elevatorDesiredHeight + encoderOffsetOfElevatorOne, ControlType.kPosition);
				}
				else{
				 	Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
				 	Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);
			
				 	double desiredHeightOfElevatorTwo = (elevatorDesiredHeight-(currentHeightElevatorOne()));
				 	if (desiredHeightOfElevatorTwo > 0){
						
				 		pidControllerElevatorTwo.setReference(-1*desiredHeightOfElevatorTwo + Robot.elevator.encoderOffsetOfElevatorTwo, ControlType.kPosition);// must be * -1
				 	}
				 	else{
				 		pidControllerElevatorTwo.setReference(Robot.elevator.encoderOffsetOfElevatorTwo, ControlType.kPosition);//to 0
				 	}
				}
			}
			// else if (currentHeight<desiredHeight) {
			// 	if (checkMaxOfElevatorOne()==true) {//when ele 1 is all the way up
			// 		Robot.oi.elevator1.setIdleMode(IdleMode.kBrake);
			// 		Robot.oi.elevator2.setIdleMode(IdleMode.kCoast);

			// 		pidControllerElevatorTwo.setReference(desiredEncoderPostionOfElevatorTwo-RobotUtils.getEncPositionFromINElevatorTwo(currentHeight) + Robot.elevator.encoderOffsetOfElevatorTwo, ControlType.kPosition);
			// 	}
				else {
					double desiredHeightOfElevatorOne = (elevatorDesiredHeight-(encoderTwo.getPosition()-encoderOffsetOfElevatorTwo));
					double maxEncoderElevator1 = (maxHeightOfElevator1)-encoderOffsetOfElevatorOne;//add max encoder hieght of elevator one
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
//	}

	public boolean checkMaxOfElevatorOne()
	{
		double currentHeightOfOne = currentHeightElevatorOne();
		double maxHeightOfElevator1 = maxHeightElevatorOne();
		double wiggleRoomForTheHeightOfOne = 0.5;
		System.out.println("current Height Of One " + currentHeightOfOne);
		System.out.println("maxHeightOfElevator1 " + maxHeightOfElevator1);
		System.out.println("wiggleRoomForTheHeightOfOne " + wiggleRoomForTheHeightOfOne);
		if(currentHeightOfOne >= (maxHeightOfElevator1 - wiggleRoomForTheHeightOfOne)){
			System.out.println("currentHeightOfOne is " + true);
			return true;
		}
		else{
			System.out.println("currentHeightOfOne is " + false);
			return false;
		}

	}
	public boolean checkMaxOfElevatorTwo()
	{
		double currentHeightOfTwo = currentHeightElevatorTwo();
		double maxHeightOfElevator2 = maxHeightElevatorTwo();
		double wiggleRoomForTheHeightOfTwo = 0.5;
		System.out.println("current Height Of Two " + currentHeightOfTwo);
		System.out.println("maxEncoderElevator2 " + maxHeightOfElevator2);
		System.out.println("wiggleRoomForTheHeightOfTwo " + wiggleRoomForTheHeightOfTwo);
		if(currentHeightOfTwo >= (maxHeightOfElevator2 - wiggleRoomForTheHeightOfTwo)){
			System.out.println("currentHeightOfTwo is " + true);
			return true;
		}
		else{
			System.out.println("currentHeightOfTwo is " + false);
			return false;
		}
	}

	public double currentHeightElevatorOne()
	{
		return encoderOne.getPosition() - encoderOffsetOfElevatorOne;
	}

	public double currentHeightElevatorTwo()
	{
		return encoderTwo.getPosition() - encoderOffsetOfElevatorTwo;
	}

	public double maxHeightElevatorOne()
	{
		return maxHeightOfElevator1/*  + encoderOffsetOfElevatorOne */;
	}

	public double maxHeightElevatorTwo()
	{
		return maxHeightOfElevator2/*  + encoderOffsetOfElevatorTwo */;
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ElevatorCommand());
	}
}
