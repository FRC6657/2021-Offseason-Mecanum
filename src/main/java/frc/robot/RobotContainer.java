// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  // Subsystem Inits
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Intake m_intake = new Intake();
  // HID Inits
  private final Joystick m_controller = new Joystick(0);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    CommandScheduler.getInstance().setDefaultCommand(m_drivetrain, new TeleOp(m_drivetrain,
        () -> m_controller.getRawAxis(0), () -> -m_controller.getRawAxis(1), () -> m_controller.getRawAxis(2)));

    final JoystickButton side = new JoystickButton(m_controller, 2);
    side.whenHeld(new IntakePowercells(m_intake, .4));

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
