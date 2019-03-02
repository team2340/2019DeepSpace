package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArmCommand extends Command {
	private Joystick controller;

	public ArmCommand() {
		System.out.println("CREATED ARM COMMAND");
		requires(Robot.arm);
	}

	@Override
	protected void initialize() {
		System.out.println("INITIALIZE");
		controller = Robot.oi.acquisitionController;
	}

	@Override
	protected void execute() {
		System.out.println("limit of arms");
        if(controller.getRawButton(RobotMap.BUTTON_4) && controller.getRawButton(RobotMap.BUTTON_7))
        {
            Robot.arm.armsClose();
        }
        else if(controller.getRawButton(RobotMap.BUTTON_4) && ! controller.getRawButton(RobotMap.BUTTON_7)){
            Robot.arm.armsOpen();
		}
		else{
			Robot.arm.armsStop();
		}
        
        // if(controller.getRawButton(RobotMap.BUTTON_1)&& controller.getRawButton(RobotMap.BUTTON_7)){
        //     Robot.arm.armWheelsin();
        // }
        // else if(controller.getRawButton(RobotMap.BUTTON_1)&& !controller.getRawButton(RobotMap.BUTTON_7)){
        //     Robot.arm.armWheelsout();
		// }
		// else{
		// 	Robot.arm.armWheelStop();
		// }
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
