// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain();

  private final XboxController m_controller = new XboxController(0);

  public RobotContainer() {
    configureButtonBindings();

    m_drivetrain.setDefaultCommand(new TeleopDrive(
      m_drivetrain,
      () -> m_controller.getRawAxis(XboxController.Axis.kRightX.value),
      () -> m_controller.getRawAxis(XboxController.Axis.kRightY.value),
      () -> m_controller.getRawAxis(XboxController.Axis.kLeftX.value),
      () -> m_controller.getRawButton(XboxController.Button.kBumperRight.value)
    ));

  }

  private void configureButtonBindings() {}

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
