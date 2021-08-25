// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

  //Motor controller declarations
  private static WPI_TalonSRX m_frontLeft;
  private static WPI_TalonSRX m_frontRight;
  private static WPI_TalonSRX m_backLeft;
  private static WPI_TalonSRX m_backRight; 

  //Gyro declaration
  private static PigeonIMU m_gyro;

  public Drivetrain() {
    //Motor controller CAN assignment
    m_frontLeft = new WPI_TalonSRX(1);
    m_frontRight = new WPI_TalonSRX(2);
    m_backLeft = new WPI_TalonSRX(3);
    m_backRight = new WPI_TalonSRX(4);

    //Gyro CAN assignment
    m_gyro = new PigeonIMU(5);

  }

  /**
   * @param xSpeed Speed along the X Axis
   * @param ySpeed Speed along the Y Axis
   * @param zRotation Rotational Speed
   * @param fieldRelativeOverride Whether or not Field Relativity should be disabled
   * 
   * The function that litterally drives the robot
   * 
   */
  public void drive(double xSpeed, double ySpeed, double zRotation, boolean fieldRelativeOverride){

    /**
     * Limits the inputs to the acceptable range
     * Values will probably be in this range already
     * just accounting for an edge case.
    */
    xSpeed = MathUtil.clamp(xSpeed, -1, 1);
    ySpeed = MathUtil.clamp(xSpeed, -1, 1);
    zRotation = MathUtil.clamp(zRotation, -1, 1);


    //Puts a small deadband on all of the analog inputs
    xSpeed = applyDeadband(xSpeed, 0.05);
    ySpeed = applyDeadband(ySpeed, 0.05);
    zRotation = applyDeadband(zRotation, 0.05);

    //Plug the x and y inputs into a Vector2D to make Field Relativity a cake walk
    Vector2d input = new Vector2d(xSpeed,ySpeed);

    /**
     * If field relative is not being overriden offset
     * the desired movement by the gyros angle to align
     * movement to the driver's perspective
     */
    if(!fieldRelativeOverride){

      input.rotate(-m_gyro.getFusedHeading());

    }

    /**
     * Creates and array for the wheel speeds with extra
     * space needed for the normalization that happens
     * on line #91
    */

    double[] wheelSpeeds = new double[4];

    /**
     * Calculates the desired wheel values, and puts them
     * into the array ready to be normalized.
    **/
    wheelSpeeds[0] = input.x + input.y + zRotation;
    wheelSpeeds[1] = -input.x + input.y - zRotation;
    wheelSpeeds[2] = -input.x + input.y + zRotation;
    wheelSpeeds[3] = input.x + input.y - zRotation;

    /**
     * When going in paricular directions these equations
     * can result in an output of 2 at the worst. Since
     * motor powers are on a scale from -1 to 1 these have
     * to be normalized.
     */
    normalize(wheelSpeeds);

    m_frontLeft.set(wheelSpeeds[0] * Constants.MAX_DRIVE_SPEED);
    m_frontRight.set((wheelSpeeds[1] * Constants.MAX_DRIVE_SPEED) * -1);
    m_backLeft.set(wheelSpeeds[2] * Constants.MAX_DRIVE_SPEED);
    m_backRight.set((wheelSpeeds[3] * Constants.MAX_DRIVE_SPEED) * -1);

  }

  /**
   * @param input Input in need of a deadband
   * @param deadband Deadband threshold
   */
  private double applyDeadband(double input, double deadband) {
    if (Math.abs(input) > deadband) {
      if (input > 0.0) {
        return (input - deadband) / (1.0 - deadband);
      } else {
        return (input + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  /**
   * @param wheelSpeeds Array of wheel speeds to normalize
   * 
   * This function takes an array of values and normalize them.
   * Keeping all of the values in line with what should be passed
   * to the motors while also preserving the magnitude difference
   * between each values.
   */
  private void normalize(double[] wheelSpeeds) {
    double maxMagnitude = Math.abs(wheelSpeeds[0]);
    for (int i = 1; i < wheelSpeeds.length; i++) {
      double temp = Math.abs(wheelSpeeds[i]);
      if (maxMagnitude < temp) {
        maxMagnitude = temp;
      }
    }
    if (maxMagnitude > 1.0) {
      for (int i = 0; i < wheelSpeeds.length; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
      }
    }
  }
}
