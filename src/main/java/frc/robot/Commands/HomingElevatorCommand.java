package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HomingElevatorCommand extends Command {
	public HomingElevatorCommand() {
		requires(Robot.elevator);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		System.out.println("Elevator Homing");
		if(Robot.elevator.limitSwitchAtBottomOfElevatorTwo.read()==false){
		Robot.elevator.move2(-.5);
		}
		if(Robot.elevator.limitSwitchAtBottomOfElevatorOne.read()==false){
			Robot.elevator.move1(-.5);
		}
	}

	@Override
	protected boolean isFinished() {
		if(Robot.elevator.limitSwitchAtBottomOfElevatorTwo.read()==true){
			System.out.println("Limit switch at E2 done");
			Robot.elevator.move2(0);
		}
		if(Robot.elevator.limitSwitchAtBottomOfElevatorOne.read()==true){
			System.out.println("Limit switch at E1 done");
			Robot.elevator.move1(0);
		}
		if (Robot.elevator.limitSwitchAtBottomOfElevatorOne.read() ==true 
			&& Robot.elevator.limitSwitchAtBottomOfElevatorTwo.read() ==true)  {
            Robot.elevator.encoderOffsetOfElevatorOne = Robot.elevator.encoderOne.getPosition();
			Robot.elevator.encoderOffsetOfElevatorTwo = Robot.elevator.encoderTwo.getPosition();
			System.out.println("E1 offset: " + Robot.elevator.encoderOffsetOfElevatorOne);
			System.out.println("E2 offset: " + Robot.elevator.encoderOffsetOfElevatorTwo);
			return true;
		}
		return false;
	}
}
