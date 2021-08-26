// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Command to drive at a certain angle relative to the field.
 * Use of .setTimeout() strongly suggested
 */
public class DrivePolar extends CommandBase {
  
  //Local versions of the paremeters
  private final Drivetrain m_drivetrain;
  private final double magnitude;
  private final double angle;

  public DrivePolar(Drivetrain m_drivetrain, double magnitude, double angle) {
    
    //Copying the constructor parameters to local values
    this.m_drivetrain = m_drivetrain;
    this.magnitude = magnitude;
    this.angle = angle;

    //Ensures the command has drivetrain access
    addRequirements(m_drivetrain);
  }

  @Override
  public void initialize() {
    //Simple print to signal command starting
    System.out.println("Polar Drive Initialized");
  }

  @Override
  public void execute() {

    //Creates x and y values from the angle input
    m_drivetrain.drive(
      magnitude * Math.sin(angle * (Math.PI/180)),
      magnitude * Math.cos(angle * (Math.PI/180)),
      0,
      false);
  }

  //Stops the robot when the command does.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(0,0,0,true);
  }


  //Stops the robot from stoping on its own before the .setTimeout()
  @Override
  public boolean isFinished() {
    return false;
  }
}
