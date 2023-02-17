package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.util.Units
import edu.wpi.first.math.geometry.Translation2d
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult
import frc.robot.Constants


class CameraSubsystem : SubsystemBase() {
    val cam:PhotonCamera = PhotonCamera("CCP BALOON CAMERA")
    var frame:PhotonPipelineResult = PhotonPipelineResult()
    


    override fun periodic() {
        // This method will be called once per scheduler run
        frame = cam.getLatestResult()
    }

    fun takePicture(proscessed:Boolean) {
        (if (proscessed) (cam.takeOutputSnapshot()) else (cam.takeInputSnapshot()))
    }

    

    fun movementToTarget(targetHeight: Double):Constants.movementTarget{
        if(!frame.hasTargets()) (return Constants.movementTarget(Constants.errorCodes.targetsNotFoundError, Constants.errorCodes.targetsNotFoundError))
        val pitch:Double = Units.degreesToRadians(frame.getBestTarget().getPitch())
        val dist:Double = targetHeight - Constants.setHeights.camera / Math.tan(pitch)
        return Constants.movementTarget(dist, frame.getBestTarget().getYaw())
    }


    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
