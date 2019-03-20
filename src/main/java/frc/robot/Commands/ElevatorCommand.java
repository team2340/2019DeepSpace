package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorCommand extends Command {
	private Joystick controller;

	public ElevatorCommand() {
		requires(Robot.elevator);
	}
	
	@Override
	protected void initialize() {
		controller = Robot.oi.acquisitionController;
	}

	@Override
	protected void execute() {
		
		if(Robot.elevator.limitSwitchAtBottomOfElevatorTwo.read()==true){
			// System.out.println("Limit switch at E2 done");
			Robot.elevator.encoderOffsetOfElevatorTwo = Robot.elevator.encoderTwo.getPosition();
			// System.out.println("E2 offset: " + Robot.elevator.encoderOffsetOfElevatorTwo);


		}
		if(Robot.elevator.limitSwitchAtBottomOfElevatorOne.read()==true){
			// System.out.println("Limit switch at E1 done");
			Robot.elevator.encoderOffsetOfElevatorOne = Robot.elevator.encoderOne.getPosition();
			// System.out.println("E1 offset: " + Robot.elevator.encoderOffsetOfElevatorOne);
			

		}
		double limit = 0.6;
		// System.out.println("encoder one position " + Robot.elevator.encoderOne.getPosition());
		// System.out.println("encoder one position w/ offset " + Robot.elevator.currentHeightElevatorOne());
		// System.out.println("elevator one max height " + Robot.elevator.maxHeightElevatorOne());

		// System.out.println("encoder two position " + Robot.elevator.encoderTwo.getPosition());
		// System.out.println("encoder two position w/ offset " + Robot.elevator.currentHeightElevatorTwo());
		// System.out.println("elevator two max height " + Robot.elevator.maxHeightElevatorTwo());

		double y = -controller.getY();
		y = Math.min(y, limit);
		y = Math.max(y, -limit);

		//going up or down
		Robot.elevator.moveUpOrDown(y);
		// if(y >= 0.1 || y <= -0.1) Robot.elevator.move1(y);
		// else Robot.elevator.move1(0);
		//deliver cargo
		//If you don't press Button_7, you are doing the ball;
		//If you press Button_7, you are switched to do the hatch.
		// if (y==0)
		{
		if(controller.getRawButton(RobotMap.BUTTON_2) && !controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(27.5);
			System.out.println("move to 27.5");
		}
		if (controller.getRawButton(RobotMap.BUTTON_3) && !controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(55.5);
			System.out.println("move to 55.5");

		}
		
		if(controller.getRawButton(RobotMap.BUTTON_1)&& controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(83.5);
			System.out.println("move to 83.5");

        }
		//If you also press Button_7, you are switched to do the hatch.
		if (controller.getRawButton(RobotMap.BUTTON_2) && controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(19);
			System.out.println("move to 19");

		}
		if (controller.getRawButton(RobotMap.BUTTON_3) && controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(47);
			System.out.println("move to 47");

		}
		if (controller.getRawButton(RobotMap.BUTTON_5) && controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(75);
			System.out.println("move to 75");

		}
//For ship
//ball
		if (controller.getRawButton(RobotMap.BUTTON_6)){
			Robot.elevator.movePosition(38.5);
			System.out.println("move to 38.5");

		}
	}
}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
