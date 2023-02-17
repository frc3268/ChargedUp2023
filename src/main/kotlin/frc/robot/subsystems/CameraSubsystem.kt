package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.util.Units
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult
import frc.robot.Constants


class CameraSubsystem : SubsystemBase() {
    val cam:PhotonCamera = PhotonCamera("CCP SPY BALOON")
    var frame:PhotonPipelineResult = PhotonPipelineResult()
    


    override fun periodic() {
        // This method will be called once per scheduler run
        frame = cam.getLatestResult()
    }

    fun takePicture(proscessed:Boolean) {
        (if (proscessed) (cam.takeOutputSnapshot()) else (cam.takeInputSnapshot()))
    }

    

    fun distanceToBestTarget(targetHeight: Double):Double{
        !frame.hasTargets()?return Constants.errorCodes.frameNotFoundError
        val pitch:Double = Units.degreesToRadians(frame.getBestTarget().getPitch())
        return (targetHeight - Constants.setHeights.camera) / Math.tan(pitch)
    }


    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
