// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TeleopDrive extends CommandBase {
  
  //Local versions of the inputs
  private final Drivetrain m_drivetrain;
  private final double xSpeed;
  private final double ySpeed;
  private final double zRotation;
  private final boolean fieldRelativeOverride;

  boolean toggleOn = false;
  boolean togglePressed = false;

  /**
   * @param m_drivetrain          Drivetrain Subsystem
   * @param xSpeed                Speed along the X Axis
   * @param ySpeed                Speed along the Y Axis
   * @param zRotation             Rotational Speed
   * @param fieldRelativeOverride Whether or not Field Relativity should be
   *                              disabled
   */
  public TeleopDrive(Drivetrain m_drivetrain, DoubleSupplier xSpeed, DoubleSupplier ySpeed, DoubleSupplier zRotation,
      BooleanSupplier fieldRelativeOverride) {

    // Copying the constructor parameters into a local sense
    this.m_drivetrain = m_drivetrain;
    this.xSpeed = xSpeed.getAsDouble();
    this.ySpeed = ySpeed.getAsDouble();
    this.zRotation = zRotation.getAsDouble();
    this.fieldRelativeOverride = fieldRelativeOverride.getAsBoolean();

    // Makes sure that the command needs access to the subsystem to run.
    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {
    // Simple print to verify the command runs.
    System.out.println("Teleop Mecanum Initialized");
  }

  @Override
  public void execute() {

    //Updates the toggle state
    updateToggle();

    //Plug all of the local variables into the drive equation with the correct relativity state.
    if(toggleOn){
      m_drivetrain.drive(xSpeed, ySpeed, zRotation, false);
    }
    else{
      m_drivetrain.drive(xSpeed, ySpeed, zRotation, true);
    }
  }

  //Updates the field relativity toggle
  private void updateToggle() {
    if(fieldRelativeOverride) {
      if(!togglePressed) {
        toggleOn = !toggleOn;
        togglePressed = true;
      }
    }
    else{
      togglePressed = false;
    }
  }

  @Override
  public void end(boolean interrupted) {

    //When the command stops the robot stops.
    m_drivetrain.drive(0, 0, 0, false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //Returning false ensures that the robot will not stop driving unwarented.
  }
}
