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
		// System.out.println("limit switch at bottom of elevator two"+Robot.elevator.limitSwitchAtBottomOfElevatorTwo.read());
		// System.out.println("limit switch at bottom of elevator one"+Robot.elevator.limitSwitchesAtBottomOfElevatorOne.read());
	
		double y = -controller.getY();
		//going up or down	
		Robot.elevator.moveUpOrDown(y);
		//deliver cargo
		//If you don't press Button_7, you are doing the ball;
		//If you press Button_7, you are switched to do the hatch.
		if(controller.getRawButton(RobotMap.BUTTON_2) && !controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(27.5);
		}
		if (controller.getRawButton(RobotMap.BUTTON_3) && !controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(55.5);
		}
		if (controller.getRawButton(RobotMap.BUTTON_5) && !controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(83.5);
		}

		//If you also press Button_7, you are switched to do the hatch.
		if (controller.getRawButton(RobotMap.BUTTON_2) && controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(19);
		}
		if (controller.getRawButton(RobotMap.BUTTON_3) && controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(47);
		}
		if (controller.getRawButton(RobotMap.BUTTON_5) && controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.elevator.movePosition(75);
		}
//For ship
//ball
		if (controller.getRawButton(RobotMap.BUTTON_6)){
			Robot.elevator.movePosition(38.5);
		}
	}
	
	//Key 
	//0 is when Elevator 2 reaches the highest top.
	//1 is when Elevator 1 reaches its top limit.
	//2 is when Elevator 2 reaches its bottom limit.
	//3 is when Elevator 1 reaches the lowest bottom. 

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
