// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class RobotContainer {

  //Subsystem Inits
  private final Drivetrain m_drivetrain = new Drivetrain();
  //HID Inits
  private final XboxController m_controller = new XboxController(0);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    CommandScheduler.getInstance().setDefaultCommand(m_drivetrain, 
      new TeleOp(
        m_drivetrain,
        () -> m_controller.getRawAxis(XboxController.Axis.kLeftX.value), 
        () -> -m_controller.getRawAxis(XboxController.Axis.kLeftY.value), 
        () -> m_controller.getRawAxis(XboxController.Axis.kRightX.value)));

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
