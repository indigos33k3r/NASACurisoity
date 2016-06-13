import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
import com.neuronrobotics.bowlerstudio.vitamins.*;
import java.nio.file.Paths;
import eu.mihosoft.vrl.v3d.FileUtil;
import com.neuronrobotics.bowlerstudio.vitamins.*;
println "Loading STL file"
// Load an STL file from a git repo
// Loading a local file also works here

return new ICadGenerator(){
	@Override 
	public ArrayList<CSG> generateCad(DHParameterKinematics d, int linkIndex) {
		ArrayList<DHLink> dhLinks = d.getChain().getLinks()
		ArrayList<CSG> allCad=new ArrayList<>()
		int i=linkIndex;
		DHLink dh = dhLinks.get(linkIndex)
		// Hardware to engineering units configuration
		LinkConfiguration conf = d.getLinkConfiguration(i);
		// Engineering units to kinematics link (limits and hardware type abstraction)
		AbstractLink abstractLink = d.getAbstractLink(i);// Transform used by the UI to render the location of the object
		// Transform used by the UI to render the location of the object
		Affine manipulator = dh.getListener();
		// loading the vitamins referenced in the configuration
		CSG servo=   Vitamins.get(conf.getElectroMechanicalType(),conf.getElectroMechanicalSize())
		
		CSG tmpSrv = servo
					.rotx(-Math.toDegrees(dh.getAlpha()))
					.rotz(-Math.toDegrees(dh.getTheta()))
					.movex(-dh.getR())
					.movez(-dh.getD())
		tmpSrv.setManipulator(manipulator)
		allCad.add(tmpSrv)
		println "Generating link: "+linkIndex

		if(i==0){
			// more at https://github.com/NeuronRobotics/java-bowler/blob/development/src/main/java/com/neuronrobotics/sdk/addons/kinematics/DHLink.java
			println dh
			println "D = "+dh.getD()// this is the height of the link
			println "R = "+dh.getR()// this is the radius of rotation of the link
			println "Alpha = "+Math.toDegrees(dh.getAlpha())// this is the alpha rotation
			println "Theta = "+Math.toDegrees(dh.getTheta())// this is the rotation about hte final like orentation
			println conf // gets the link hardware map from https://github.com/NeuronRobotics/java-bowler/blob/development/src/main/java/com/neuronrobotics/sdk/addons/kinematics/LinkConfiguration.java
			println conf.getHardwareIndex() // gets the link hardware index
			println conf.getScale() // gets the link hardware scale to degrees from link units
			// more from https://github.com/NeuronRobotics/java-bowler/blob/development/src/main/java/com/neuronrobotics/sdk/addons/kinematics/AbstractLink.java
			println  "Max engineering units for link = " + abstractLink.getMaxEngineeringUnits() 
			println  "Min engineering units for link = " + abstractLink.getMinEngineeringUnits() 
			println "Position "+abstractLink.getCurrentEngineeringUnits()
			println manipulator
		}
		return allCad;
	}
	@Override 
	public ArrayList<CSG> generateBody(MobileBase b ) {
		ArrayList<CSG> allCad=new ArrayList<>();
		double size =40;

		File servoFile = ScriptingEngine.fileFromGit(
			"https://github.com/NeuronRobotics/NASACurisoity.git",
			"STL/body.STL");
		// Load the .CSG from the disk and cache it in memory
		CSG body  = Vitamins.get(servoFile)

		body.setManipulator(b.getRootListener());
		

		return [body];
	}
};