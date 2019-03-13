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
		System.out.println("arm limit switch " + Robot.arm.limitSwitchForArmOpen.read());
		System.out.println("arm encoder position " + Robot.arm.encoderArm.getPosition());
		System.out.println("arm encoder offset " + Robot.arm.encoderOffsetOfArm);
		System.out.println("arm max height " + Robot.arm.maxValueInches);
		// System.out.println("arm max height w/ offset " + Robot.arm.maxHeightElevatorTwo());
		// System.out.println("limit of arms");
		if (Robot.arm.limitSwitchForArmOpen.read() == true)
		{
			Robot.arm.armsStop();
			Robot.arm.encoderOffsetOfArm = Robot.arm.encoderArm.getPosition();
			System.out.println("Arm offset: " + Robot.arm.encoderOffsetOfArm);
		}

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
        if (controller.getRawButton(RobotMap.BUTTON_5) && !controller.getRawButton(RobotMap.BUTTON_7)){
			Robot.arm.armWheelsin();
		}

        else if(controller.getRawButton(RobotMap.BUTTON_1)&& !controller.getRawButton(RobotMap.BUTTON_7)){
            Robot.arm.armWheelsout();
		}
		else{
			Robot.arm.armWheelStop();
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
