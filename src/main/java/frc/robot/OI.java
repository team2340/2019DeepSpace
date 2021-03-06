/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick driveController = new Joystick(RobotMap.DRIVE_PORT);
	public Joystick acquisitionController = new Joystick(RobotMap.ACQUISITION_PORT);
	public ADXRS450_Gyro gyro = null;

	public WPI_TalonSRX left = null;
	public WPI_TalonSRX right = null;
	public CANSparkMax elevator1 = null;
	public CANSparkMax elevator2 = null;
	// public WPI_TalonSRX armwheel = null; 
	public CANSparkMax armopenclose = null;
	//public WPI_TalonSRX armopenclose = null;
	// public WPI_TalonSRX armthree = null;
	public WPI_TalonSRX ramp = null;
	public WPI_TalonSRX lift = null;
	
	public final double CAM_VIEWING_ANGLE = 61.0;
	public final double IMG_WIDTH = 160.0;
	public final double IMG_HEIGHT = 120.0;
}
