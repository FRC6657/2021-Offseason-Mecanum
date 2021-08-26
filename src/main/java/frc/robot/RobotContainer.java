// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Teleop.*;
import frc.robot.commands.Auto.*;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RobotContainer {

  //Subsystem Inits
  private final Drivetrain m_drivetrain = new Drivetrain();

  //HID Inits
  private final XboxController m_controller = new XboxController(0);

  public RobotContainer() {
    configureButtonBindings();

    //Sets the default command for the drivetrain subsystem to Teleop.
    m_drivetrain.setDefaultCommand(new Drive(
      m_drivetrain, //Subsystem Input for calling the drive function within it
      () -> m_controller.getRawAxis(XboxController.Axis.kRightX.value), //xSpeed Input
      () -> m_controller.getRawAxis(XboxController.Axis.kRightY.value), //ySpeed Input
      () -> m_controller.getRawAxis(XboxController.Axis.kLeftX.value),  //zRotation Input
      () -> m_controller.getRawButton(XboxController.Button.kBumperRight.value) //Button Input for Field Relativity
    ));
  }

  private void configureButtonBindings() {}

  private final String auto = "Polar";

  //Changes auto based on the String
  public Command getAutonomousCommand() {
    switch(auto){
      case "Polar":
        return new SequentialCommandGroup(
          new DrivePolar(m_drivetrain, 0.5, 30).withTimeout(1)
        );
      default:
        return null;
    }
  }
}
