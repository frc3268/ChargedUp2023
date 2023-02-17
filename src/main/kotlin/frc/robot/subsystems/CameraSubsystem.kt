package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult
import frc.robot.Constants


class CameraSubsystem : SubsystemBase() {
    val cam:PhotonCamera = PhotonCamera("CCP SPY BALOON")
    var frame:PhotonPipelineResult? = null
    


    override fun periodic() {
        // This method will be called once per scheduler run
        frame = cam.getLatestResult()
    }

    fun takePicture(proscessed:Boolean) {
        (if (proscessed) (cam.takeOutputSnapshot()) else (cam.takeInputSnapshot()))
    }

    

    fun distanceToBestTarget():Double{
        frame?:return Constants.errorCodes.frameNotFoundError
        frame?.getBestTarget()
       
        return 0.0
        
    }


    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
