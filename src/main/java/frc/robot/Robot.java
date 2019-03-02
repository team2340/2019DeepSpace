/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Commands.ArmHomingCommand;
import frc.robot.Commands.DrivingCameraCommand;
import frc.robot.Commands.HomingElevatorCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LiftandrampSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final OI oi = new OI();
  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;
  // public static final DebugLogger myLogger = new DebugLogger();
   private final SendableChooser<String> m_chooser = new SendableChooser<>();
   public static ElevatorSubsystem elevator = null;
   public static DriveSubsystem drive = null;
   public static ArmSubsystem arm = null;
   public static LiftandrampSubsystem liftandramp = null;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

    drive = DriveSubsystem.getInstance();         
		//liftandramp = LiftandrampSubsystem.getInstance();
		arm = ArmSubsystem.getInstance();               
		elevator = ElevatorSubsystem.getInstance();

    // oi.gyro = new ADXRS450_Gyro();
  }

  public void teleopInit() {
		// oi.gyro.reset();
		// SmartDashboard.putNumber("Gyro angle", oi.gyro.getAngle());
	}

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    // m_autoSelected = m_chooser.getSelected();
    // System.out.println("Auto selected: " + m_autoSelected);
    //  new HomingElevatorCommand().start();
    // new DrivingCameraCommand().start();
    // new ArmHomingCommand().start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
